package it.discovery.hibernate.model;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private LocalDateTime created;

    private LocalDateTime modified;

    @PrePersist
    public void OnPersist() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        modified = LocalDateTime.now();
    }
}
