package it.discovery.redis.lock;

import it.discovery.redis.BaseRedisTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedisPubSubGlobalLockTest extends BaseRedisTest {

    GlobalLock globalLock;

    RedissonClient client;

    @BeforeEach
    void setup() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(STR."redis://localhost:\{redis.getMappedPort(6379)}");
        client = Redisson.create(config);
        globalLock = new RedisPubSubGlobalLock("locks", client);
    }

    @AfterEach
    void tearDown() {
        if (client != null) {
            client.shutdown();
        }
    }

    //TODO add multi-threading tests
    @Test
    void lock_noLockExists_success() {
        globalLock.lock();

        assertTrue(globalLock.isLocked());
    }

    @Test
    void unlock_lockExisted_success() {
        globalLock.lock();

        globalLock.unlock();

        assertFalse(globalLock.isLocked());
    }
}