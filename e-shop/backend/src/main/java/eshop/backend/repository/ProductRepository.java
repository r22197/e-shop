package eshop.backend.repository;

import eshop.backend.model.Category;
import eshop.backend.model.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //todo: přidat jednotlivé dotazy do ProductServiceImpl


    //todo: contains
    @Query("SELECT p FROM Product p WHERE p.name = :contains")
    List<Product> findProductsByNameContains(
            @Param("contains") String contains
    );

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByCategoryAndPriceBetween(Category category, Double lowPrice, Double maxPrice, Pageable pageable);
}