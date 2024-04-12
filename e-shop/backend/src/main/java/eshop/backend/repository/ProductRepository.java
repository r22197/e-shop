package eshop.backend.repository;

import eshop.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //todo: přidat jednotlivé dotazy do ProductServiceImpl
    @Query("SELECT p FROM Product p WHERE p.category = :categoryName")
    List<Product> findProductsByCategory(
            @Param("categoryName") String category
    );

    //todo: contains
    @Query("SELECT p FROM Product p WHERE p.name = :contains")
    List<Product> findProductsByNameContains(
            @Param("contains") String contains
    );

    @Query("SELECT p FROM Product p WHERE p.price > :minPrice AND p.price < :maxPrice")
    List<Product> findProductsByPriceBetween(
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice
    );
}