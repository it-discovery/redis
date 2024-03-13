package it.discovery.redis.repository.jedis;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.discovery.redis.model.Book;
import it.discovery.redis.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.NumberUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JedisHashBookRepository implements BookRepository, AutoCloseable {

    private final static String PREFIX = "books-hash:";

    private final Jedis jedis;

    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    public JedisHashBookRepository(String host, int port) {
        jedis = new Jedis(host, port);
    }

    @Override
    public Book save(Book book) {
        Map<String, String> map = mapper.convertValue(book, Map.class);
        jedis.hset(getKey(book.getId()), map.entrySet().stream().
                filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        return book;
    }

    private String getKey(int id) {
        return PREFIX + id;
    }

    @Override
    public Book getOne(int id) {
        Map<String, String> map = jedis.hgetAll(getKey(id));
        if (map == null || map.isEmpty()) {
            return null;
        }
        return mapper.convertValue(map, Book.class);
    }

    @Override
    public List<Book> findAll() {
        ScanParams params = new ScanParams().count(10).match(PREFIX + "*");
        String cursor = ScanParams.SCAN_POINTER_START;

        List<Book> books = new ArrayList<>();
        do {
            ScanResult<String> result = jedis.scan(cursor, params);
            List<String> keys = result.getResult();
            books.addAll(keys.stream().map(id -> getOne(NumberUtils.parseNumber(
                    id.replaceAll(PREFIX, ""), Integer.class))).toList());
            cursor = result.getCursor();
        } while (!cursor.equals(ScanParams.SCAN_POINTER_START));
        return books;
    }

    @Override
    public void saveAll(List<Book> books) {

    }

    @Override
    public List<Book> findByName(String name) {
        return null;
    }

    @Override
    public List<Book> findWithReviews() {
        return null;
    }

    @Override
    public int findTotalPages() {
        return 0;
    }

    @Override
    public void close() throws Exception {
        if (jedis != null) {
            jedis.close();
        }
    }
}
