package eshop.backend.service.impl;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Product> getProductsInCategory(Long id, Integer pageNumber, Integer pageSize, String sortBy, Double lowPrice, Double maxPrice) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with the ID " + id));

        Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by("price").ascending() : Sort.by("price").descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        if (lowPrice != null && maxPrice != null) {
            return productRepository.findByCategoryAndPriceBetween(category, lowPrice, maxPrice, pageable);
        } else {
            return productRepository.findByCategory(category, pageable);
        }
    }


    @Override
    public Product getById(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);

        return product
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public Product create(Product product) {

        if (categoryRepository.findById(product.getCategory().getId()).isPresent()) {
            product.setCategory(categoryRepository.findById(product.getCategory().getId()).get());
        }

        productRepository.save(product);

        return product;
    }

    @Override
    public Product update(Long id, Product product) throws ProductNotFoundException {
        Product existingProduct = getById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        if (categoryRepository.findById(product.getCategory().getId()).isPresent()) {
            existingProduct.setCategory(categoryRepository.findById(product.getCategory().getId()).get());
        }


        productRepository.save(existingProduct);

        return product;
    }

    @Override
    public void delete(Long productId) throws ProductNotFoundException {
        Product product = getById(productId);

        productRepository.delete(product);
    }
}

