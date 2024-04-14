package eshop.backend.service.impl;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.response.ProductDto;
import eshop.backend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Product> getAllProducts(Integer pageNumber, Integer pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);

        return product
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public Product create(ProductDto productDto) throws CategoryNotFoundException {
        Product product = new Product();
        convertDtoToProduct(productDto, product);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        Product product = getProductById(productId);

        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long productId, ProductDto productDto) throws ProductNotFoundException, CategoryNotFoundException {
        Product product = getProductById(productId);

        convertDtoToProduct(productDto, product);

        return productRepository.save(product);
    }

    private void convertDtoToProduct(ProductDto productDto, Product product) throws CategoryNotFoundException {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);
        Category category1 = categoryService.getById(productDto.getCategory());

        product.setCategory(category1);
    }
}
