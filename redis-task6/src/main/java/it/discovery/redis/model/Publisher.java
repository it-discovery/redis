package it.discovery.redis.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Book publisher
 */
@Getter
@Setter
public class Publisher extends BaseEntity {
    private String name;

    private List<Book> books;

    private Contact contact;
}
