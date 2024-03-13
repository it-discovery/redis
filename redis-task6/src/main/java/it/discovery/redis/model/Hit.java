package it.discovery.redis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hit extends BaseEntity {

    private String ip;

    private String browser;

    /**
     * Details on how(where) user hit this link
     */
    private String origin;

    private Book book;
}
