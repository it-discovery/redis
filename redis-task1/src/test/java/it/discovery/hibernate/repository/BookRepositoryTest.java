package it.discovery.hibernate.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import it.discovery.hibernate.model.Book;
import it.discovery.hibernate.model.Hit;
import it.discovery.hibernate.model.Person;
import it.discovery.hibernate.model.Publisher;
import it.discovery.hibernate.model.Review;
import jakarta.persistence.EntityManager;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    EntityManager em;

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
        bookRepository.saveAll(List.of(book1, book2));

        List<Book> books = bookRepository.findWithReviews();
        assertEquals(1, books.size());
        assertEquals("Hibernate", books.get(0).getNameEn());
    }

    @Test
    void findWithHits_returnHitCount() {
        Person author = new Person();
        author.setName("Gavin King");
        Publisher publisher = new Publisher();
        publisher.setName("Packt");

        Book book = new Book();
        book.setNameEn("JPA");
        book.setAuthor(author);
        book.setPublisher(publisher);

        Hit hit = new Hit();
        hit.setBrowser("Chrome");
        hit.setOrigin("Ads");
        hit.setBook(book);
        book.setHits(List.of(hit));

        bookRepository.saveAndFlush(book);

        em.clear();

        Book book2 = bookRepository.getReferenceById(book.getId());
        assertNotNull(book2);
        assertEquals(1, book2.getHitCount());
        assertEquals(1, book2.getHits().size());
    }

    @Test
    void findFirstBookByAuthor_IdOrderByCreatedDesc_returnsLastBook() {
        Person author = new Person();
        author.setName("Gavin King");
        Publisher publisher = new Publisher();
        publisher.setName("Packt");

        Book book = new Book();
        book.setNameEn("JPA");
        book.setAuthor(author);
        book.setPublisher(publisher);

        bookRepository.save(book);

        Book book2 = new Book();
        book2.setNameEn("JPA2");
        book2.setAuthor(author);
        book2.setPublisher(publisher);

        bookRepository.save(book2);

        Book book3 = bookRepository.findFirstBookByAuthor_IdOrderByCreatedDesc(author.getId());
        assertNotNull(book3);
        assertEquals(book2.getId(), book3.getId());
    }
}
