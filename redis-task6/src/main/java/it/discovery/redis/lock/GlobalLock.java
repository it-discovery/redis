package it.discovery.redis.lock;

public interface GlobalLock {

    //TODO add timeout
    void lock();

    void unlock();

    boolean isLocked();
}
