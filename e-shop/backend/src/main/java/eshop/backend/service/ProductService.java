package eshop.backend.service;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Product;
import eshop.backend.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    Product create(ProductRequest request) throws CategoryNotFoundException;
    Product read(Long productId) throws ProductNotFoundException;
    Product update(ProductRequest request) throws ProductNotFoundException, CategoryNotFoundException;
    void delete(Long productId) throws ProductNotFoundException;
    Page<Product> pageOfAllProducts(Sort.Direction direction, String attribute, Integer pageNumber, Integer pageSize);
    Page<Product> listByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, Double lowPrice, Double maxPrice) throws CategoryNotFoundException;
    List<Product> search(String query);
}
