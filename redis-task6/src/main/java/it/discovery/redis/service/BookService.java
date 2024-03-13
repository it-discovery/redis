package it.discovery.redis.service;

import it.discovery.redis.model.Book;
import it.discovery.redis.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    /**
     * Returns all the books where number of pages is greater than pages parameter
     *
     * @param name
     * @return
     */
    public List<Book> findByPagesGreaterThan(int pages) {
        //TODO implement
        return null;
    }
}
