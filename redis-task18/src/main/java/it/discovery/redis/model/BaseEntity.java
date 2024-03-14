package it.discovery.redis.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
