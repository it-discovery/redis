package it.discovery.redis;

import it.discovery.redis.repository.spring.data.ContainerConfiguration;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataRedisTest
@Testcontainers
@ContextConfiguration(classes = ContainerConfiguration.class)
public class BaseSpringDataRedisTest {
//    @Container
//    @ServiceConnection
//    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine").withExposedPorts(6379);

//    @DynamicPropertySource
//    static void mongoProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
//    }
}
