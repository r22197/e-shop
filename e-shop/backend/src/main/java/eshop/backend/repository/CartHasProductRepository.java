package eshop.backend.repository;

import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartHasProductRepository extends JpaRepository<CartHasProduct, Long> {

    Optional<CartHasProduct> findByProduct(Product product);

}