package it.discovery.redis.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity {
    private Integer id;

    private LocalDateTime created;

    private LocalDateTime modified;

    public void OnPersist() {
        created = LocalDateTime.now();
    }

    public void onUpdate() {
        modified = LocalDateTime.now();
    }
}
