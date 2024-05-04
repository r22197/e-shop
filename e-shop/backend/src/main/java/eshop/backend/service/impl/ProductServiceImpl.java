package eshop.backend.service.impl;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product create(Product product) throws CategoryNotFoundException {
        categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new CategoryNotFoundException(product.getCategory().getId()));

        return productRepository.save(product);
    }

    @Override
    public Product read(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public Product update(Product product) throws ProductNotFoundException, CategoryNotFoundException {
        categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new CategoryNotFoundException(product.getCategory().getId()));

        Product persistedProduct = read(product.getId());

        persistedProduct.setName(product.getName());
        persistedProduct.setDescription(product.getDescription());
        persistedProduct.setPrice(product.getPrice());
        persistedProduct.setImagePath(product.getImagePath());

        return productRepository.save(persistedProduct);
    }

    @Override
    public void delete(Long productId) throws ProductNotFoundException {
        productRepository.deleteById(
                read(productId).getId()
        );
    }

    @Override
    public Page<Product> list(Integer pageNumber, Integer pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Page<Product> listByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, Double lowPrice, Double maxPrice) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        Sort.Direction direction = sortBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, direction);

        return productRepository.findByCategoryAndPriceBetween(category, lowPrice, maxPrice, pageable);
    }

    public List<Product> search(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }
        String queryLowerCase = query.toLowerCase();
        return productRepository.findByNameContainingIgnoreCase(queryLowerCase);
    }
}

