package ttv.poltoraha.pivka.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ttv.poltoraha.pivka.dao.request.BookDTO;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Genre;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.repository.GenreRepository;
import ttv.poltoraha.pivka.service.BookDtoToBookService;

@Service
@RequiredArgsConstructor
public class BookDtoToBookServiceImpl implements BookDtoToBookService {

     private final BookRepository bookRepository;
     private final AuthorRepository authorRepository;
     private final GenreRepository genreRepository;

     @Override
     public void saveBook(BookDTO dto) {
          Book book = new Book();
          book.setArticle(dto.getArticle());
          book.setRating(dto.getRating());
          book.setTags(dto.getTags());


          Author author = authorRepository.findByFullName(dto.getAuthorFullName())
                  .orElseGet(() -> {
                       Author newAuthor = new Author();
                       newAuthor.setFullName(dto.getAuthorFullName());
                       return authorRepository.save(newAuthor);
                  });

          Genre genre = genreRepository.findByGenreName(dto.getGenreName())
                  .orElseGet(() -> {
                       Genre newGenre = new Genre();
                       newGenre.setGenreName(dto.getGenreName());
                       return genreRepository.save(newGenre);
                  });

          book.setAuthor(author);
          book.setGenre(genre);

          bookRepository.save(book);
     }
}
