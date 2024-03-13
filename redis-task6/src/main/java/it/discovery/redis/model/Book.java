package it.discovery.redis.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Book in a library
 */
@Getter
@Setter
public class Book extends BaseEntity {
    private String nameEn;

    private String nameRu;

    private String nameUk;

    private Complexity complexity;

    private Person author;

    private Publisher publisher;

    /**
     * Publishing year
     */
    private int year;

    /**
     * Total number of pages
     */
    private int pages;

    private List<Review> reviews;

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);
        review.setBook(this);
    }
}
