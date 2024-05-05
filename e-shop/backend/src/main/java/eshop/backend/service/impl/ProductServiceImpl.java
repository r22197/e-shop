package eshop.backend.service.impl;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.request.ProductRequest;
import eshop.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
    public Product create(ProductRequest request) throws CategoryNotFoundException {
        var product = new Product(request);

        setProductCategory(request, product);

        return productRepository.save(product);
    }

    @Override
    public Product read(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public Product update(ProductRequest request) throws ProductNotFoundException, CategoryNotFoundException {
        var persistedProduct = read(request.getId());

        persistedProduct.setName(request.getName());
        persistedProduct.setDescription(request.getDescription());
        persistedProduct.setImagePath(request.getImagePath());
        setProductCategory(request, persistedProduct);

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

    @Override
    public List<Product> search(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }
        return productRepository.findByNameContainingIgnoreCase(query.toLowerCase());
    }

    private void setProductCategory(ProductRequest request, Product product) throws CategoryNotFoundException {
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

            product.setCategory(category);
        }
    }
}

