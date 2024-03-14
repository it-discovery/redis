package it.discovery.redis.repository.spring.data;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainerConfiguration {

    @Bean
    @ServiceConnection(name = "redis")
    GenericContainer<?> redis() {
        return new GenericContainer<>("redis:7-alpine").withExposedPorts(6379);
    }

    @Bean(destroyMethod = "shutdown")
    RedissonClient redissonClient(GenericContainer<?> redis) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(STR."redis://localhost:\{redis.getMappedPort(6379)}");
        return Redisson.create(config);
    }
}
