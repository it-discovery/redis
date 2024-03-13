package it.discovery.hibernate.repository;

import it.discovery.hibernate.model.Contact;
import it.discovery.hibernate.model.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PublisherRepositoryTest {

    @Autowired
    PublisherRepository publisherRepository;

    @Test
    void findAll_returnsAllPublishers() {
        Publisher publisher = new Publisher();
        publisher.setName("Packt");
        Contact contact = new Contact();
        contact.setEmail("info@packt.com");
        contact.setPhone("1234567");
        publisher.setContact(contact);

        publisherRepository.save(publisher);

        List<Publisher> list = publisherRepository.findAll();
        assertEquals(1, list.size());
        assertEquals("Packt", list.get(0).getName());
        assertEquals("1234567", list.get(0).getContact().getPhone());
        assertTrue(list.get(0).getCreated().isBefore(LocalDateTime.now()));
    }

}
