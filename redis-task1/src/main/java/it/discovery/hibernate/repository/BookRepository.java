package it.discovery.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.discovery.hibernate.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * Returns all the books with exact name and
     * specified locale
     * @param name
     * @param locale
     * @return
     */
    //List<Book> findByName(String name, String locale);

    /**
     * Returns all the books with exact name irregardless of locale
     * @param name
     * @return
     */
    //List<Book> findByName(String name);

    /**
     * Returns all the books that has at least one review
     *
     * @return
     */
    @Query("FROM Book b join b.reviews r WHERE r.id is not null")
    List<Book> findWithReviews();

    /**
     * Returns all the books where number of pages is greater than pages parameter
     *
     * @param pages
     * @return
     */
    List<Book> findByPagesGreaterThan(int pages);

    /**
     * Returns all the books of the specified author
     * ignore them
     *
     * @param name
     * @param pages
     * @return
     */
    List<Book> findAllByAuthor_Name(String name);

    /**
     * Returns overall number of pages for all the books
     *
     * @return
     */
    @Query("SELECT sum(b.pages) FROM Book b")
    int findTotalPages();

    /**
     * Returns last book written by author
     *
     * @param personId
     * @return
     */
    Book findFirstBookByAuthor_IdOrderByCreatedDesc(int personId);
}
