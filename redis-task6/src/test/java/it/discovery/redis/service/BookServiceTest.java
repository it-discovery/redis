package it.discovery.redis.service;

import it.discovery.redis.model.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookServiceTest {

    BookService bookService;

    @Test
    void saveBook_findByName_success() {
        Book book = new Book();
        book.setNameEn("JPA");
        bookService.saveBook(book);

        List<Book> books = bookService.findByName("JPA");
        assertEquals(1, books.size());
        assertEquals("JPA", books.get(0).getNameEn());
    }

}
