package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.entity.Review;
import ttv.poltoraha.pivka.entity.ReviewRating;
import ttv.poltoraha.pivka.repository.MyUserRepository;
import ttv.poltoraha.pivka.repository.ReviewRatingRepository;
import ttv.poltoraha.pivka.repository.ReviewRepository;
import ttv.poltoraha.pivka.service.ReviewRatingService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewRatingServiceImpl implements ReviewRatingService {

    private final ReviewRatingRepository reviewRatingRepository;
    private final MyUserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewRating rateReview(String username, Integer reviewId, Integer rating) {
        MyUser user = userRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        ReviewRating reviewRating = reviewRatingRepository.findByUserAndReview(user, review)
                .orElseGet(() -> new ReviewRating(user, review, rating));

        reviewRating.setRating(rating);
        return reviewRatingRepository.save(reviewRating);
    }

    @Override
    public Optional<Integer> getAverageRatingByReviewId(Integer reviewId) {
        return reviewRatingRepository.getAverageRatingByReviewId(reviewId);
    }

    @Override
    public void deleteReviewRating(String username, Integer reviewId) {
        MyUser user = userRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        reviewRatingRepository.deleteByUserAndReview(user, review);
    }
}

