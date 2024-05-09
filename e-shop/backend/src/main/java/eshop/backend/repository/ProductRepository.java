package eshop.backend.repository;

import eshop.backend.model.Category;
import eshop.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // https://i.stack.imgur.com/SOCCZ.png
    List<Product> findByNameContainingIgnoreCase(String contains);
    Page<Product> findByCategory(Category category, Pageable pageable);
    //Page<Product> findByCategoryAndPriceBetween(Category category, Double lowPrice, Double maxPrice, Pageable pageable);


}