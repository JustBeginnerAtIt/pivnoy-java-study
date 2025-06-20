package ttv.poltoraha.pivka.service;

import ttv.poltoraha.pivka.dao.request.BookDTO;

public interface BookDtoToBookService {
    void saveBook(BookDTO dto);
}
