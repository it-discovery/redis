package it.discovery.redis.repository.redis.om;

import com.redis.om.spring.repository.RedisDocumentRepository;
import it.discovery.redis.model.ShoppingCart;

public interface ShoppingCartRepository extends RedisDocumentRepository<ShoppingCart, String> {
}
