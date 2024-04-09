package eshop.backend.repository;

import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartHasProductRepository extends JpaRepository<CartHasProduct, Long> {
    @Query("SELECT chp FROM CartHasProduct chp WHERE chp.cart= :cart AND chp.product = :product")
    CartHasProduct isProductInCart(
            @Param("cart") Cart cart,
            @Param("product")Product product
            );
}
