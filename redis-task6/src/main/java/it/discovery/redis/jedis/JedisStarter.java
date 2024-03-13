package it.discovery.redis.jedis;

import it.discovery.redis.model.Book;
import it.discovery.redis.model.Complexity;
import it.discovery.redis.repository.jedis.JedisHashBookRepository;

import java.util.List;

public class JedisStarter {

    public static void main(String[] args) {
        try (JedisHashBookRepository bookRepository = new JedisHashBookRepository("localhost", 6379)) {
            Book book = new Book();
            book.setId(1);
            book.setNameEn("Redis");
            book.setComplexity(Complexity.HIGH);
            book.setYear(2024);

            bookRepository.save(book);

            Book book1 = bookRepository.getOne(book.getId());
            System.out.println(book1);

            List<Book> books = bookRepository.findAll();
            System.out.println("***BOOKS***");
            System.out.println(books);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
