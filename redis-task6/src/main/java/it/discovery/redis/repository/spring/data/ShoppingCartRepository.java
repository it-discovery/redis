package it.discovery.redis.repository.spring.data;

import it.discovery.redis.model.ShoppingCart;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface ShoppingCartRepository extends KeyValueRepository<ShoppingCart, String> {
}
