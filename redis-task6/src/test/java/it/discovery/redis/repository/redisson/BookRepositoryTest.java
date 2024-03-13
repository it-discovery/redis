package it.discovery.redis.repository.redisson;

import it.discovery.redis.BaseRedisTest;
import it.discovery.redis.model.Book;
import it.discovery.redis.model.Person;
import it.discovery.redis.model.Publisher;
import it.discovery.redis.model.Review;
import it.discovery.redis.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookRepositoryTest extends BaseRedisTest {

    BookRepository bookRepository;

    @BeforeEach
    void setup() {
        bookRepository = new RedissonBookRepository("localhost", redis.getMappedPort(6379));
    }

    @Test
    void save_validBook_success() {
        Book book1 = new Book();
        book1.setId(1);
        book1.setNameEn("JPA");
        book1.setAuthorId("123");
        book1.setPublisherId("1");

        bookRepository.save(book1);

        Book book = bookRepository.getOne(book1.getId());
        assertNotNull(book);
        assertEquals(book1.getNameEn(), book.getNameEn());
        assertEquals(book1.getAuthorId(), book.getAuthorId());
    }

    @Test
    @Disabled
    void saveAll_validBooks_success() {
        Book book1 = new Book();
        book1.setId(1);
        book1.setNameEn("Redis");
        book1.setAuthorId("123");
        book1.setPublisherId("1");

        Book book2 = new Book();
        book2.setId(2);
        book2.setNameEn("NoSQL");
        book2.setAuthorId("123");
        book2.setPublisherId("1");

        bookRepository.saveAll(List.of(book1, book2));

        Book book = bookRepository.getOne(book1.getId());
        assertNotNull(book);
        Book book3 = bookRepository.getOne(book2.getId());
        assertNotNull(book3);

    }

    @Test
    @Disabled
        //TODO implement
    void findWithReviews_returnsSingleBook() {
        Person author = new Person();
        author.setName("Gavin King");
        Publisher publisher = new Publisher();
        publisher.setName("Packt");

        Book book1 = new Book();
        book1.setNameEn("JPA");
        book1.setAuthorId("123");
        book1.setPublisherId("1");

        Book book2 = new Book();
        book2.setNameEn("Hibernate");
        book2.setAuthorId("123");
        book2.setPublisherId("1");

        Review review = new Review();
        review.setComment("good");
        review.setRate(10);
        book2.addReview(review);
        bookRepository.saveAll(List.of(book1, book2));

        List<Book> books = bookRepository.findWithReviews();
        assertEquals(1, books.size());
        assertEquals("Hibernate", books.getFirst().getNameEn());
    }

}
