package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> getAllProducts(Integer pageNumber, Integer pageSize);
    Product getById(Long Id) throws ProductNotFoundException;
    Product create(Product product);
    Product update(Long id, Product product) throws ProductNotFoundException;
    void delete(Long productId) throws ProductNotFoundException;
}
