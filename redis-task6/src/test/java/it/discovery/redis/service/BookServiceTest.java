package it.discovery.redis.service;

import it.discovery.redis.BaseSpringDataRedisTest;
import it.discovery.redis.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest extends BaseSpringDataRedisTest {

    @Autowired
    BookService bookService;

    @Test
    void saveBook_validObject_success() {
        Book book = new Book();
        book.setNameEn("JPA");
        book.setId(1);
        bookService.saveBook(book);

        Book book1 = bookService.findById(book.getId());
        assertNotNull(book1);
        assertEquals("JPA", book1.getNameEn());
    }

    @Test
    void findById_identifierInvalid_error() {
        Book book1 = bookService.findById(100);
        assertNull(book1);
    }

}
