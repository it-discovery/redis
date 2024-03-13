package it.discovery.redis.repository;

import it.discovery.redis.model.Person;

import java.util.List;

public interface PersonRepository {

    /**
     * Returns all the persons sorted by name
     *
     * @return
     */
    List<Person> findByOrderByNameAsc();
}
