package ttv.poltoraha.pivka.app.serviceImpl;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dao.request.AuthorDTO;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.serviceImpl.AuthorServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private AuthorDTO authorDTO;
    private Book book;

    @BeforeEach
    public void setUp() {
        book = bookRepository.findById(1).orElse(null);
        authorDTO = AuthorDTO.builder()
                .fullName("Test Author")
                .avgRating(4.5)
                .build();
    }

    private List<Author> getAllAuthors() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Test
    public void testCreateAuthor_Success() {
        int beforeSize = getAllAuthors().size();

        authorService.create(authorDTO);

        int afterSize = getAllAuthors().size();

        assertNotEquals(beforeSize, afterSize);
    }

    @Test
    public void testDeleteAuthor_Success() {
        authorService.create(authorDTO);

        Author savedAuthor = getAllAuthors().stream()
                .filter(a -> a.getFullName().equals(authorDTO.getFullName()))
                .findFirst()
                .orElseThrow();

        authorService.delete(savedAuthor.getId());

        assertFalse(authorRepository.existsById(savedAuthor.getId()));
    }

    @Test
    public void testAddBook_Success() {
        authorService.create(authorDTO);

        Author savedAuthor = getAllAuthors().stream()
                .filter(a -> a.getFullName().equals(authorDTO.getFullName()))
                .findFirst()
                .orElseThrow();

        authorService.addBook(savedAuthor.getId(), book);

        Author updatedAuthor = authorRepository.findById(savedAuthor.getId()).orElseThrow();

        assertNotNull(updatedAuthor.getBooks());
        assertTrue(updatedAuthor.getBooks().contains(book));
    }

    @Test
    public void testAddBooks_Success() {
        authorService.create(authorDTO);

        Author savedAuthor = getAllAuthors().stream()
                .filter(a -> a.getFullName().equals(authorDTO.getFullName()))
                .findFirst()
                .orElseThrow();

        List<Book> books = new ArrayList<>();
        books.add(book);

        authorService.addBooks(savedAuthor.getId(), books);

        Author updatedAuthor = authorRepository.findById(savedAuthor.getId()).orElseThrow();

        assertNotNull(updatedAuthor.getBooks());
        assertEquals(1, updatedAuthor.getBooks().size());
    }
}
