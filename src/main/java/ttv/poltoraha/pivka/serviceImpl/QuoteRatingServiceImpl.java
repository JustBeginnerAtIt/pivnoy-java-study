package ttv.poltoraha.pivka.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.entity.QuoteRating;
import ttv.poltoraha.pivka.repository.QuoteRatingRepository;
import ttv.poltoraha.pivka.service.QuoteRatingService;

@Service
@RequiredArgsConstructor
public class QuoteRatingServiceImpl implements QuoteRatingService {

    private final QuoteRatingRepository quoteRatingRepository;

    @Override
    @Transactional
    public void rateQuote(MyUser user, Quote quote, Integer rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        var existing = quoteRatingRepository.findByQuoteAndUser(quote, user);
        if (existing.isPresent()) {
            QuoteRating qr = existing.get();
            qr.setRating(rating);
            quoteRatingRepository.save(qr);
        } else {
            quoteRatingRepository.save(
                    QuoteRating.builder()
                            .user(user)
                            .quote(quote)
                            .rating(rating)
                            .build()
            );
        }
    }
}
