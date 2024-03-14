package it.discovery.redis.repository.jedis;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.discovery.redis.model.Book;
import it.discovery.redis.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JedisHashBookRepository implements BookRepository, AutoCloseable {

    private final static String PREFIX = "books-hash:";

    private final static String PREFIX_NAME = STR."\{PREFIX}name:";

    private final Jedis jedis;

    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    public JedisHashBookRepository(String host, int port) {
        jedis = new Jedis(host, port);
    }

    @Override
    public Book save(Book book) {
        Book book1 = getOne(book.getId());
        if (book1 != null) {
            if (StringUtils.hasLength(book1.getNameEn())) {
                jedis.srem(PREFIX_NAME + book1.getNameEn(), STR."\{book.getId()}");
            }
            if (StringUtils.hasLength(book1.getNameUk())) {
                jedis.srem(PREFIX_NAME + book1.getNameUk(), STR."\{book.getId()}");
            }
        }

        jedis.hset(getKey(book.getId()), bookToMap(book));

        if (StringUtils.hasLength(book.getNameEn())) {
            //books-hash:name:Redis
            jedis.sadd(PREFIX_NAME + book.getNameEn(), STR."\{book.getId()}");
        }
        if (StringUtils.hasLength(book.getNameUk())) {
            jedis.sadd(PREFIX_NAME + book.getNameUk(), STR."\{book.getId()}");
        }
        return book;
    }

    private String getKey(int id) {
        return PREFIX + id;
    }

    private Map bookToMap(Book book) {
        Map<String, String> map = mapper.convertValue(book, Map.class);
        return map.entrySet().stream().
                filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
        Transaction transaction = jedis.multi();
        try {
            books.stream().map(this::bookToMap)
                    .forEach(book -> transaction.hset(getKey((Integer) book.get("id")),
                            book));
            transaction.exec();
        } catch (Exception e) {
            transaction.discard();
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Book> findByName(String name) {
        //books-hash:name:Redis
        Set<String> keys = jedis.smembers(PREFIX_NAME + name);
        return keys.stream().map(key -> getOne(NumberUtils.parseNumber(
                key.replaceAll(PREFIX, ""), Integer.class))).toList();
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
