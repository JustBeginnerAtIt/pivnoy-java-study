package ttv.poltoraha.pivka.service;

import ttv.poltoraha.pivka.entity.ReviewRating;

import java.util.Optional;

public interface ReviewRatingService {
    ReviewRating rateReview(String username, Integer reviewId, Integer rating);
    Optional<Integer> getAverageRatingByReviewId(Integer reviewId);
    void deleteReviewRating(String username, Integer reviewId);
}

