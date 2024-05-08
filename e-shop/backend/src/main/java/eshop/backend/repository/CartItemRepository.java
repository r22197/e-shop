package eshop.backend.repository;

import eshop.backend.model.Cart;
import eshop.backend.model.CartItem;
import eshop.backend.model.Product;
import eshop.backend.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndVariant(Cart cart, Variant variant);
}