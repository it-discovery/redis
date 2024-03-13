package it.discovery.redis.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Person who can write books, for example
 *
 * @author admin
 */
@Getter
@Setter
public class Person extends BaseEntity {
    private String name;

    /**
     * Books that person has written
     */
    private List<Book> books;

    private Contact contact;
}
