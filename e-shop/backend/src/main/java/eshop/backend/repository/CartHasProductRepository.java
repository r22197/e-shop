package eshop.backend.repository;

import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartHasProductRepository extends JpaRepository<CartHasProduct, Long> {
    Optional<CartHasProduct> findByCartAndProduct(Cart cart, Product product);
    List<CartHasProduct> findByCart(Cart cart);

}