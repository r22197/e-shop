package eshop.backend.repository;

import eshop.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findProductByCategoryContains(
            @Param("category") String category
    );

    //todo: contains
    @Query("SELECT p FROM Product p WHERE p.name = :contains")
    List<Product> findProductByNameContains(
            @Param("contains") String contains
    );
}