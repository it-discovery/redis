package it.discovery.redis.lock;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import java.util.concurrent.atomic.AtomicBoolean;

//TODO use RAtomicLong only without AtomicBoolean
public class RedisPubSubGlobalLock implements GlobalLock {

    private final static String GLOBAL_LOCK_PROPERTY = "global-lock";

    private final static String MSG_UNLOCK = "unlock";

    private final RTopic topic;

    private final RAtomicLong atomicLong;

    private final AtomicBoolean locked = new AtomicBoolean(false);

    public RedisPubSubGlobalLock(String channel, RedissonClient redisson) {
        topic = redisson.getTopic(channel);
        atomicLong = redisson.getAtomicLong(GLOBAL_LOCK_PROPERTY);
        topic.addListener(String.class, (_, msg) -> {
            if (MSG_UNLOCK.equals(msg)) {
                locked.set(true);
            }
        });
    }

    @Override
    public void lock() {
        if (locked.get()) {
            throw new IllegalStateException("Unable to lock because global lock has been already acquired");
        }
        boolean success = atomicLong.compareAndSet(0, 1);
        if (success) {
            locked.set(true);
        } else {
            while (!locked.get()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException _) {
                }
            }
        }
    }

    @Override
    public void unlock() {
        if (!locked.get()) {
            throw new IllegalStateException("Unable to unlock because no lock has been acquired");
        }
        atomicLong.set(0);
        topic.publish(MSG_UNLOCK);
        locked.set(false);
    }

    @Override
    public boolean isLocked() {
        return locked.get();
    }
}
