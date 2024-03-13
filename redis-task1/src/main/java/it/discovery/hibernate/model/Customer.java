package it.discovery.hibernate.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
public class Customer extends BaseEntity {

    private String login;

    private String password;

    private String name;

    @Embedded
    private Contact contact;
}
