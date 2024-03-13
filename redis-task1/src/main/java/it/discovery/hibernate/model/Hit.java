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
public class Hit extends BaseEntity {

    private String ip;

    private String browser;

    /**
     * Details on how(where) user hit this link
     */
    private String origin;

    private boolean mobile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;
}
