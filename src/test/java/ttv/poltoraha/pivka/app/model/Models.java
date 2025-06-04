package ttv.poltoraha.pivka.app.model;

import ttv.poltoraha.pivka.dao.request.ReviewRequestDto;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Genre;
import ttv.poltoraha.pivka.entity.Review;

import static ttv.poltoraha.pivka.app.util.TestConst.REVIEW_TEXT;
import static ttv.poltoraha.pivka.app.util.TestConst.USERNAME;

public class Models {
    public static Book getBook(Genre genre) {
        return Book.builder()
                .id(1)
                .genre(genre)
                .build();
    }

    public static ReviewRequestDto getReviewRequestDto(Integer bookId) {
        return ReviewRequestDto.builder()
                .readerUsername(USERNAME)
                .text(REVIEW_TEXT)
                .rating(5)
                .bookId(bookId)
                .build();
    }

    public static Review getReview(Book book) {
        return Review.builder()
                .readerUsername(USERNAME)
                .rating(5)
                .text(REVIEW_TEXT)
                .book(book)
                .build();
    }
}
