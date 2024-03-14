package it.discovery.redis;

import it.discovery.redis.service.BookService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    @Bean
    BookService bookService(RedisTemplate redisTemplate) {
        return new BookService(redisTemplate);
    }

    @Configuration(proxyBeanMethods = false)
    public static class RedissonConfiguration {

        @Bean(destroyMethod = "shutdown")
        RedissonClient redissonClient(Environment env) {
            Config config = new Config();
            config.useSingleServer()
                    .setAddress(STR."redis://localhost:\{env.getRequiredProperty("spring.data.redis.port")}");
            return Redisson.create(config);
        }

        @Bean
        RedissonConnectionFactory redissonConnectionFactory(RedissonClient redissonClient) {
            return new RedissonConnectionFactory(redissonClient);
        }
    }

}
