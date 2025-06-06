package ttv.poltoraha.pivka.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dao.request.AuthorDTO;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.mapping.AuthorMapping;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.service.AuthorService;

import java.util.List;

// Имплементации интерфейсов с бизнес-логикой
@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;

    // todo как будто надо насрать всякими мапперами
    @Override
    public void create(AuthorDTO authorDTO) {
        logger.info("Mapping AuthorDTO to Author entity: {}", authorDTO);
        var author = AuthorMapping.fromDto(authorDTO);

        logger.info("Saving a new Author to DataBase: {}", author);
        authorRepository.save(author);
        logger.info("Author saved successfully: {}", author.getFullName());
    }

    @Override
    public void delete(Integer id) {
        logger.info("Deleting Author with id from DataBase: {}", id);
        authorRepository.deleteById(id);
        logger.info("Author deleted successfully: {}", id);
    }

    @Override
    public void addBooks(Integer id, List<Book> books) {
        logger.info("Fetching author in DataBase with id {} to add books", id);
        val author =  getOrThrow(id);

        logger.info("Author found in DataBase: {}, adding books: {}", author.getFullName(), books);
        author.getBooks().addAll(books);
        logger.info("Selected Books added successfully to author: {}", author.getFullName());
    }

    @Override
    public void addBook(Integer id, Book book) {
        logger.info("Fetching author in DataBase with id {} to add book", id);
        val author = getOrThrow(id);

        logger.info("Author in DataBase found: {}, adding book: {}",  author.getFullName(), book);
        author.getBooks().add(book);
        logger.info("Selected Book added successfully to author: {}", author.getFullName());
    }

    @Override
    public List<Author> getTopAuthorsByTag(String tag, int count) {
        logger.info("Querying top authors in DataBase by tag: {}, limit: {}", tag, count);
        Pageable pageable = PageRequest.of(0, count);

        val authors = authorRepository.findTopAuthorsByTag(tag);
        logger.info("Fetched authors in DataBase: {}", authors);

        logger.info("Returning authors from DataBase with tag {} and page {}", tag, pageable);
        return authorRepository.findTopAuthorsByTag(tag, pageable);
    }

    private Author getOrThrow(Integer id) {
        logger.info("Searching for author in DataBase with id: {}", id);
        val optionalAuthor = authorRepository.findById(id);
        val author = optionalAuthor.orElse(null);

        if (author == null) {
            logger.warn("Author with id = {} not found in DataBase", id);
            throw new RuntimeException("Author with id = " + id + " not found");
        }

        logger.info("Author found in DataBase: {}", author.getFullName());
        return author;
    }
}
