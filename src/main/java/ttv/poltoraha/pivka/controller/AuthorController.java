package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ttv.poltoraha.pivka.dao.request.AuthorDTO;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.metrics.CustomMetrics;
import ttv.poltoraha.pivka.service.AuthorService;

import java.util.List;

// Контроллеры - это классы для создания внешних http ручек. Чтобы к нам могли прийти по http, например, через постман
// Или если у приложухи есть веб-морда, каждое действие пользователя - это http запросы
@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;
    private final CustomMetrics customMetrics;

    @PostMapping("/create")
    public void createAuthor(@RequestBody AuthorDTO authorDTO) {
        customMetrics.recordCounter(CustomMetrics.MetricType.CREATE_AUTHOR);

        val timer = new StopWatch();

        logger.info("Received request to create author: {}", authorDTO);
        timer.start();
        authorService.create(authorDTO);
        timer.stop();
        logger.info("Created author {}", authorDTO.getFullName());

        customMetrics.recordTimer(CustomMetrics.MetricType.CREATE_AUTHOR, timer.getTime());
    }

    @PostMapping("/delete")
    public void deleteAuthorById(@RequestParam Integer id) {
        customMetrics.recordCounter(CustomMetrics.MetricType.DELETE_AUTHOR);

        val timer = new StopWatch();

        logger.info("Received request to delete author with id: {}", id);
        timer.start();
        authorService.delete(id);
        timer.stop();
        logger.info("Deleted author {}", id);

        customMetrics.recordTimer(CustomMetrics.MetricType.DELETE_AUTHOR, timer.getTime());
    }

    @PostMapping("/add/books")
    public void addBooksToAuthor(@RequestParam Integer id, @RequestBody List<Book> books) {
        customMetrics.recordCounter(CustomMetrics.MetricType.ADD_BOOKS_TO_AUTHOR);
        val timer = new StopWatch();

        logger.info("Received request to add books to author with id: {}. Books: {}", id, books);
        timer.start();
        authorService.addBooks(id, books);
        timer.stop();
        logger.info("Successfully added books to author with id: {}", id);

        customMetrics.recordTimer(CustomMetrics.MetricType.ADD_BOOKS_TO_AUTHOR ,timer.getTime());
    }
}
