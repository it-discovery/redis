package it.discovery.hibernate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
public class Review extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    private String comment;

    private int rate;
}
