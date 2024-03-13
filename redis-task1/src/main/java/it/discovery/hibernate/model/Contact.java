package it.discovery.hibernate.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Contact {
    private String phone;

    private String email;

    @Embedded
    private Address address;
}
