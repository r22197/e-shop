package eshop.backend.service;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Product;
import eshop.backend.request.ProductRequest;
import eshop.backend.request.ProductSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {
    Product create(ProductRequest request) throws CategoryNotFoundException;
    Product read(Long productId) throws ProductNotFoundException;
    Product update(ProductRequest request) throws ProductNotFoundException, CategoryNotFoundException;
    void delete(Long productId) throws ProductNotFoundException;
    Page<Product> pageByCategoryAndSpecifications(Long categoryId, ProductSearchRequest searchRequest, PageRequest pageRequest) throws CategoryNotFoundException;
    Page<Product> pageAllProducts(PageRequest request);
    List<Product> search(String query);
}
