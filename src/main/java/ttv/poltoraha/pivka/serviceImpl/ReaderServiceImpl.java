package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.entity.Reader;
import ttv.poltoraha.pivka.entity.Reading;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.repository.QuoteRepository;
import ttv.poltoraha.pivka.repository.ReaderRepository;
import ttv.poltoraha.pivka.service.ReaderService;
import util.MyUtility;

@Service
@RequiredArgsConstructor
@Transactional
public class ReaderServiceImpl implements ReaderService {
    private final QuoteRepository quoteRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    @Override
    public void createQuote(String username, Integer book_id, String text) {
        val newQuote = new Quote();
        val reader = readerRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("Entity reader with id = " + username + " was not found"));
        val book = bookRepository.findById(book_id)
                .orElseThrow(() -> new EntityNotFoundException("Entity book with id = " + book_id + " was not found"));
        newQuote.setBook(book);
        newQuote.setText(text);
        newQuote.setReader(reader);

        //save() через QuoteRepository эффективнее из-за того, что мы сохраняем самостоятельную сущность Quote отдельно.
        //При сохранении newQuote через reader. могут быть обновлены другие элементы у reader.
        //Работа напрямую с Quote
        quoteRepository.save(newQuote);
    }

    @Override
    public void addFinishedBook(String username, Integer bookId) {
        val reader = MyUtility.findEntityById(readerRepository.findByUsername(username), "reader", username);

        val book = MyUtility.findEntityById(bookRepository.findById(bookId), "book", bookId.toString());

        val reading = new Reading();
        reading.setReader(reader);
        reading.setBook(book);

        reader.getReadings().add(reading);

        readerRepository.save(reader);
    }

    @Override
    public void createReader(String username, String password) {
        val reader = new Reader();
        reader.setUsername(username);
        reader.setPassword(password);
        reader.setNeedsPasswordReset(true);

        readerRepository.save(reader);
    }
}
