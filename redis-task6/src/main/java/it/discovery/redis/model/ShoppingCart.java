package it.discovery.redis.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RedisHash(value = "carts", timeToLive = 900)
public class ShoppingCart {

    @Id
    private String userId;

    private List<Integer> books;

    private LocalDateTime createdAt;

    @Version
    private Integer version;
}
