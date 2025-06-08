package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/authors")
    public List<Author> recommendAuthors(@RequestParam String username) {
        return recommendationService.recommendAuthor(username);
    }

    @GetMapping("/books")
    public List<Book> recommendBooks(@RequestParam String username) {
        return recommendationService.recommendBook(username);
    }

    @GetMapping("/quotes")
    public List<Quote> recommendQuotesByBook(@RequestParam Integer bookId) {
        return recommendationService.recommendQuoteByBook(bookId);
    }
}

