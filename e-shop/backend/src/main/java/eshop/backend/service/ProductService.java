package eshop.backend.service;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product create(Product product) throws CategoryNotFoundException;
    Product read(Long productId) throws ProductNotFoundException;
    Product update(Product product) throws ProductNotFoundException, CategoryNotFoundException;
    void delete(Long productId) throws ProductNotFoundException;
    Page<Product> list(Integer pageNumber, Integer pageSize);
    Page<Product> listByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, Double lowPrice, Double maxPrice) throws CategoryNotFoundException;
    List<Product> search(String query);
}
