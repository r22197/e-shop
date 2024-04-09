package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Product;
import eshop.backend.request.ProductDto;

import java.util.List;

public interface ProductService {
    Product getProductById(Long Id) throws ProductNotFoundException;
    Product createProduct(ProductDto productRequest);
    void deleteProduct(Long productId) throws ProductNotFoundException;
    Product updateProduct(Long productId, ProductDto productDto) throws ProductNotFoundException;
    List<Product> getAllProducts();
}
