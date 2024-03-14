package it.discovery.redis.model;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document("carts")
public class ShoppingCart {

    @Id
    private String userId;

    private List<Integer> books;

    @Indexed
    private int discount;

    @CreatedDate
    private LocalDateTime createdAt;
}
