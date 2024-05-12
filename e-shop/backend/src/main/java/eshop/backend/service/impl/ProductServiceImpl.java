package eshop.backend.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.*;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.request.ProductRequest;
import eshop.backend.repository.ProductSearchSpecification;
import eshop.backend.request.ProductSearchRequest;
import eshop.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static eshop.backend.utils.EntityUtils.findByIdOrElseThrow;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductSidebarFilter productsAttributeFilter;

    @Override
    public Product create(ProductRequest request) throws CategoryNotFoundException {
        var product = new Product(request);

        setCategoryIfSelectedAndExists(product, request);

        return productRepository.save(product);
    }

    @Override
    public Product read(Long productId) throws ProductNotFoundException {
        return findByIdOrElseThrow(productId, productRepository, ProductNotFoundException::new);
    }

    @Override
    public Product update(ProductRequest request) throws ProductNotFoundException, CategoryNotFoundException {
        var product = read(request.id());

        product.setName(request.name());
        product.setDescription(request.description());
        product.setImagePath(request.imagePath());
        setCategoryIfSelectedAndExists(product, request);

        return productRepository.save(product);
    }

    @Override
    public void delete(Long productId) throws ProductNotFoundException {
        var product = read(productId);
        productRepository.delete(product);
    }

    @Override
    public Page<Product> pageAllProducts(PageRequest request) {
        return productRepository.findAll(request);
    }

    @Override
    public Page<Product> pageByCategoryAndSpecifications(Long categoryId, ProductSearchRequest searchRequest, PageRequest pageRequest) throws CategoryNotFoundException {
        var category = findByIdOrElseThrow(categoryId, categoryRepository, CategoryNotFoundException::new);
        var specification = new ProductSearchSpecification(searchRequest);

        /*
        Map<Attribute, Map<AttributeValue, Long>> attributeValuesMap = productsAttributeFilter.listAttributesAndValuesCountByCategory(category, productPage.getContent());
        BigDecimal minPrice = productsAttributeFilter.minPrice(attributeValuesMap);
        BigDecimal maxPrice = productsAttributeFilter.maxPrice(attributeValuesMap);

         */
        return productRepository.findAllByCategory(category, specification, pageRequest);
    }

    @Override
    public List<Product> search(String query) {
        if (query.isEmpty()) {
            return Collections.emptyList();
        }
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    private void setCategoryIfSelectedAndExists(Product product, ProductRequest request) throws CategoryNotFoundException {
        if (request.categoryId() != null) {
            var category = findByIdOrElseThrow(request.categoryId(), categoryRepository, CategoryNotFoundException::new);

            product.setCategory(category);
        }
    }
}
