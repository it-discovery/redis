package it.discovery.redis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer extends BaseEntity {

    private String login;

    private String password;

    private String name;

    private Contact contact;
}
