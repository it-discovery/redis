package it.discovery.hibernate.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Book authors
 *
 * @author admin
 */
@Table
@Entity
@Getter
@Setter
public class Person extends BaseEntity {
    private String name;

    /**
     * Books that person has written
     */
    @OneToMany(mappedBy = "author")
    private List<Book> books;

    @Embedded
    private Contact contact;
}
