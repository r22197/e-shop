package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Product;
import eshop.backend.repository.ProductRepository;
import eshop.backend.request.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        Product product = getProductById(productId);

        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long productId, ProductDto productDto) throws ProductNotFoundException {
        Product product = getProductById(productId);

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());

        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);

        return product
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist with id " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}