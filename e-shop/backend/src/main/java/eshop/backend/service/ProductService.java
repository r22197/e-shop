package eshop.backend.service;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Product;
import eshop.backend.response.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> getAllProducts(Integer pageNumber, Integer pageSize);
    Product getProductById(Long Id) throws ProductNotFoundException;
    Product create(ProductDto productRequest) throws CategoryNotFoundException;
    void deleteProduct(Long productId) throws ProductNotFoundException;
    Product updateProduct(Long productId, ProductDto productDto) throws ProductNotFoundException, CategoryNotFoundException;
}
