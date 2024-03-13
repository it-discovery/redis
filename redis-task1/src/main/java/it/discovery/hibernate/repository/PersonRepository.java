package it.discovery.hibernate.repository;

import it.discovery.hibernate.model.Person;
import it.discovery.hibernate.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    /**
     * Returns all the persons sorted by name
     *
     * @return
     */
    List<Person> findByOrderByNameAsc();

    /**
     * Finds an author who wrote the published the
     * most books using this publisher
     *
     * @param publisher
     * @return
     */
    Optional<Person> findMostEfficient(Publisher publisher);
}
