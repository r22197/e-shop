package eshop.backend.service.impl;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Product;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.request.ProductRequest;
import eshop.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static eshop.backend.utils.EntityUtils.findByIdOrElseThrow;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product create(ProductRequest request) throws CategoryNotFoundException {
        var product = new Product(request);
        setCategoryIfExists(request, product);

        return productRepository.save(product);
    }

    @Override
    public Product read(Long productId) throws ProductNotFoundException {
        return findByIdOrElseThrow(productId, productRepository, ProductNotFoundException::new);
    }

    @Override
    public Product update(ProductRequest request) throws ProductNotFoundException, CategoryNotFoundException {
        var persistedProduct = read(request.getId());

        persistedProduct.setName(request.getName());
        persistedProduct.setDescription(request.getDescription());
        persistedProduct.setImagePath(request.getImagePath());
        setCategoryIfExists(request, persistedProduct);

        return productRepository.save(persistedProduct);
    }

    @Override
    public void delete(Long productId) throws ProductNotFoundException {
        var product = read(productId);
        productRepository.delete(product);
    }

    @Override
    public Page<Product> pageOfAllProducts(Sort.Direction direction, String attribute, Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(direction, attribute);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> listByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, Double lowPrice, Double maxPrice) throws CategoryNotFoundException {
        var category = findByIdOrElseThrow(categoryId, categoryRepository, CategoryNotFoundException::new);

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

    private void setCategoryIfExists(ProductRequest request, Product product) throws CategoryNotFoundException {
        if (request.getCategoryId() != null) {
            var category = findByIdOrElseThrow(request.getCategoryId(), categoryRepository, CategoryNotFoundException::new);

            product.setCategory(category);
        }
    }
}
