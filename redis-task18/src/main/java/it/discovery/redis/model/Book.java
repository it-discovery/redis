package it.discovery.redis.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Book in a library
 */
@Getter
@Setter
@ToString
public class Book extends BaseEntity {
    private String nameEn;

    private String nameRu;

    private String nameUk;

    private List<Translation> translations;

    private Complexity complexity;

    private String authorId;

    private String publisherId;

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
