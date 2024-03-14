package it.discovery.redis.repository.redis.om;

import it.discovery.redis.model.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingCartRepositoryTest extends BaseSpringDataRedisTest {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Test
    void save_validCart_success() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId("123");
        cart.setBooks(List.of(1, 2));
        cart.setCreatedAt(LocalDateTime.now());

        shoppingCartRepository.save(cart);

        ShoppingCart cart1 = shoppingCartRepository.findById(cart.getUserId())
                .orElseThrow(() -> new RuntimeException(STR."Cart with ID \{cart.getUserId()} doesn't exist"));
        assertEquals(cart.getBooks(), cart1.getBooks());

    }

}