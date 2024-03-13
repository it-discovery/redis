package it.discovery.redis.repository.redisson;

import it.discovery.redis.model.Book;
import it.discovery.redis.repository.BookRepository;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.util.NumberUtils;

import java.util.List;

public class RedissonBookRepository implements BookRepository, AutoCloseable {

    private final static String PREFIX = "books-json:";

    private final RedissonClient client;

    private final Codec JSON = new JsonJacksonCodec();

    public RedissonBookRepository(String host, int port) {
        Config config = new Config();
        config.useSingleServer().setAddress(STR."redis://\{host}:\{port}");
        client = Redisson.create(config);
    }

    private RBucket<Book> getBucket(int id) {
        return client.getBucket(PREFIX + id, JSON);
    }

    @Override
    public Book save(Book book) {
        RBucket<Book> bucket = getBucket(book.getId());
        bucket.set(book);
        return book;
    }

    @Override
    public Book getOne(int id) {
        return getBucket(id).get();
    }

    @Override
    public List<Book> findAll() {
        RKeys keys = client.getKeys();
        return keys.getKeysStreamByPattern(PREFIX + "*", 10)
                .map(key -> getOne(NumberUtils.parseNumber(key.replaceAll(PREFIX, ""), Integer.class))).toList();
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
        if (client != null) {
            client.shutdown();
        }
    }
}
