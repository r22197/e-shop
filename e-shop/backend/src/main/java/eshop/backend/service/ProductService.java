package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Product;
import eshop.backend.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.nio.file.Path;
import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest productRequest);
    public String deleteProduct(Long productId) throws ProductNotFoundException;
    public Product updateProduct(Long productId) throws ProductNotFoundException;
    public Product findProductById(Long Id) throws ProductNotFoundException;
    public List<Product> findAllProducts(String category);
}
