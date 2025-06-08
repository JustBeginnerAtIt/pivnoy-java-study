package ttv.poltoraha.pivka.serviceImpl;

import org.springframework.stereotype.Service;
import ttv.poltoraha.pivka.service.ReaderService;

@Service
public class ReaderServiceForNewReaders implements ReaderService {

    @Override
    public void createQuote(String username, Integer book_id, String text) {
        throw new IllegalStateException("Password reset required before quote creation.");
    }

    @Override
    public void addFinishedBook(String username, Integer bookId) {
        throw new IllegalStateException("Password reset required before adding book.");
    }

    @Override
    public void createReader(String username, String password) {
        throw new UnsupportedOperationException("Not used here.");
    }

}
