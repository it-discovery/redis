package it.discovery.hibernate.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Formula;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Book in a library
 */
@Table
@Entity
@Getter
@Setter
public class Book extends BaseEntity {
    private String nameEn;

    private String nameRu;

    private String nameUk;

    private Complexity complexity;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHOR_ID")
    private Person author;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "PUBLISHER_ID")
    private Publisher publisher;

    /**
     * Publishing year
     */
    private int year;

    /**
     * Total number of pages
     */
    private int pages;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Hit> hits;

    @Formula("(SELECT count(h.id) FROM Hit h WHERE h.book_id=id)")
    private int hitCount;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);
        review.setBook(this);
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    @Formula("(SELECT count(h.id) FROM Hit h WHERE h.book_id=id)")
    public int getHitCount() {
        return hitCount;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


}
