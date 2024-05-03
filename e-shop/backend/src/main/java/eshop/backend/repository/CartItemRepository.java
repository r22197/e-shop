package eshop.backend.repository;

import eshop.backend.model.Cart;
import eshop.backend.model.CartItem;
import eshop.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndVariantProduct(Cart cart, Product product);
    List<CartItem> findByCart(Cart cart);

}