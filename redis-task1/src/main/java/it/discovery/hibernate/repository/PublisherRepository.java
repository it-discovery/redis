package it.discovery.hibernate.repository;

import it.discovery.hibernate.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}
