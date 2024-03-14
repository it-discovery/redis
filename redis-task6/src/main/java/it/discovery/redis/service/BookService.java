package it.discovery.redis.service;

import it.discovery.redis.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    //private final BookRepository bookRepository;

    private final RedisTemplate<Integer, ?> redisTemplate;

    private final HashMapper mapper = new ObjectHashMapper();

    public List<Book> findByName(String name) {
        //return bookRepository.findByName(name);
        //TODO
        return null;
    }

    public Book findById(int id) {
        HashOperations<Integer, Object, Object> hashOperations = redisTemplate.opsForHash();
        Map<Object, Object> map = hashOperations.entries(id);
        return (Book) mapper.fromHash(map);
    }

    public void saveBook(Book book) {
        //bookRepository.save(book);
        HashOperations<Integer, Object, Object> hashOperations = redisTemplate.opsForHash();
        Map<String, String> map = mapper.toHash(book);
        hashOperations.putAll(book.getId(), map);
    }

    /**
     * Returns all the books where number of pages is greater than pages parameter
     *
     * @param pages
     * @return
     */
    public List<Book> findByPagesGreaterThan(int pages) {
        //TODO implement
        return null;
    }
}
