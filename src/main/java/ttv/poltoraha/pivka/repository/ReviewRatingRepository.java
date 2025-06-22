package ttv.poltoraha.pivka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.entity.Review;
import ttv.poltoraha.pivka.entity.ReviewRating;

import java.util.Optional;

public interface ReviewRatingRepository extends JpaRepository<ReviewRating, Integer> {
    Optional<ReviewRating> findByUserAndReview(MyUser user, Review review);

    void deleteByUserAndReview(MyUser user, Review review);

    @Query("SELECT AVG(r.rating) FROM ReviewRating r WHERE r.review.id = :review_id")
    Optional<Integer> getAverageRatingByReviewId(Integer reviewId);
}

