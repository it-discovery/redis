package it.discovery.redis.repository;

import java.util.List;

import it.discovery.redis.model.Book;

public interface BookRepository {

    /**
     * Saves this book instance
     *
     * @param book
     * @return
     */
    Book save(Book book);

    /**
     * Returns book by its identifier
     *
     * @param id
     * @return
     */
    Book getOne(int id);

    /**
     * Returns all the books
     *
     * @return
     */
    List<Book> findAll();

    /**
     * Saves specified book instances
     *
     * @param books
     */
    void saveAll(List<Book> books);

    /**
     * Returns all the books with exact name irregardless of locale
     *
     * @param name
     * @return
     */
    List<Book> findByName(String name);

    /**
     * Returns all the books that has at least one review
     *
     * @return
     */
    List<Book> findWithReviews();

    /**
     * Returns overall number of pages for all the books
     *
     * @return
     */
    int findTotalPages();

}
