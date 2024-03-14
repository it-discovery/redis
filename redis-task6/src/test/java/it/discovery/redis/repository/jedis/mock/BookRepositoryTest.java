package it.discovery.redis.repository.jedis.mock;

import com.github.fppt.jedismock.RedisServer;
import it.discovery.redis.model.Book;
import it.discovery.redis.model.Person;
import it.discovery.redis.model.Publisher;
import it.discovery.redis.model.Review;
import it.discovery.redis.repository.BookRepository;
import it.discovery.redis.repository.jedis.JedisHashBookRepository;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryTest {

    static RedisServer redisServer;

    BookRepository bookRepository;

    @BeforeAll
    static void setupJedisMock() throws IOException {
        redisServer = RedisServer.newRedisServer().start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    @BeforeEach
    void setup() {
        bookRepository = new JedisHashBookRepository(redisServer.getHost(), redisServer.getBindPort());
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
    void findByName_bookExists_success() {
        Book book1 = new Book();
        book1.setId(1);
        book1.setNameEn("Redis7");
        book1.setAuthorId("123");
        book1.setPublisherId("1");

        bookRepository.save(book1);

        List<Book> books = bookRepository.findByName(book1.getNameEn());
        assertEquals(1, books.size());
        assertEquals(book1.getId(), books.getFirst().getId());
    }

    @Test
    void findByName_bookNotExist_success() {
        Book book1 = new Book();
        book1.setId(100);
        book1.setNameEn("NoSQL");
        book1.setAuthorId("123");
        book1.setPublisherId("1");
        //book1.setTranslations(List.of(new Translation("NoSQL", "EN")));

        //books-hash:name:NoSQL
        bookRepository.save(book1);

        book1.setNameEn("Amazon");
        //books-hash:name:Amazon
        bookRepository.save(book1);

        List<Book> books = bookRepository.findByName("NoSQL");
        assertTrue(books.isEmpty());
    }

    @Test
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
