package ttv.poltoraha.pivka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ttv.poltoraha.pivka.entity.QuoteRating;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.entity.MyUser;

import java.util.List;
import java.util.Optional;

public interface QuoteRatingRepository extends JpaRepository<QuoteRating, Long> {
    Optional<QuoteRating> findByQuoteAndUser(Quote quote, MyUser user);
    List<QuoteRating> findAllByQuote(Quote quote);
}
