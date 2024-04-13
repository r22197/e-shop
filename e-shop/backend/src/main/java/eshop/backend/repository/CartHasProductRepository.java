package eshop.backend.repository;

import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartHasProductRepository extends JpaRepository<CartHasProduct, Long> {

    Optional<CartHasProduct> findByProduct(Product product);

}