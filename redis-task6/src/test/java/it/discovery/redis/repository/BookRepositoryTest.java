package it.discovery.redis.repository;

import it.discovery.redis.model.Book;
import it.discovery.redis.model.Person;
import it.discovery.redis.model.Publisher;
import it.discovery.redis.model.Review;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookRepositoryTest {

    BookRepository bookRepository;

    @Test
    void findWithReviews_returnsSingleBook() {
        Person author = new Person();
        author.setName("Gavin King");
        Publisher publisher = new Publisher();
        publisher.setName("Packt");

        Book book1 = new Book();
        book1.setNameEn("JPA");
        book1.setAuthor(author);
        book1.setPublisher(publisher);

        Book book2 = new Book();
        book2.setNameEn("Hibernate");
        book2.setAuthor(author);
        book2.setPublisher(publisher);

        Review review = new Review();
        review.setComment("good");
        review.setRate(10);
        book2.addReview(review);
        //bookRepository.saveAll(List.of(book1, book2));

        List<Book> books = bookRepository.findWithReviews();
        assertEquals(1, books.size());
        assertEquals("Hibernate", books.get(0).getNameEn());
    }

}
