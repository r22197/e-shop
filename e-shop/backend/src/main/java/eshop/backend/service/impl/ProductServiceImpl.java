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
        var product = read(request.getId());

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImagePath(request.getImagePath());
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

        var productPage = productRepository.findAllByCategory(category, specification, pageRequest);

        Map<Attribute, Map<AttributeValue, Long>> attributeValuesMap = listAttributesAndValuesCountByCategory(category, productPage);

        BigDecimal minPrice = minPrice(attributeValuesMap);
        BigDecimal maxPrice = maxPrice(attributeValuesMap);

        return productPage;
    }

    @JsonProperty
    private Map<Attribute, Map<AttributeValue, Long>> listAttributesAndValuesCountByCategory(Category category, Page<Product> productPage) {
        // Initialize the attributeValuesMap with all possible attribute-value pairs in the category
        Map<Attribute, Map<AttributeValue, Long>> attributeValuesMap = category.getProducts().stream()
                .flatMap(product -> product.getAttributes().stream()
                        .flatMap(attribute -> attribute.getValues().stream()
                                .map(attributeValue -> new AbstractMap.SimpleEntry<>(attribute, attributeValue))))
                .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey,
                        Collectors.groupingBy(AbstractMap.SimpleEntry::getValue, Collectors.counting())));

        // Update the count of each attribute-value pair based on the products in the current page
        for (Product product : productPage.getContent()) {
            for (Attribute attribute : product.getAttributes()) {
                Map<AttributeValue, Long> valueCounts = attributeValuesMap.get(attribute);
                for (AttributeValue value : attribute.getValues()) {
                    valueCounts.put(value, valueCounts.getOrDefault(value, 0L) + 1);
                }
            }
        }

        return attributeValuesMap;
    }
    @JsonProperty
    private BigDecimal minPrice(Map<Attribute, Map<AttributeValue, Long>> attributeValuesMap) {
        return attributeValuesMap.values().stream()
                .flatMap(valueCounts -> valueCounts.keySet().stream())
                .flatMap(attributeValue -> attributeValue.getVariants().stream())
                .map(Variant::getPrice)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    @JsonProperty
    private BigDecimal maxPrice(Map<Attribute, Map<AttributeValue, Long>> attributeValuesMap) {
        return attributeValuesMap.values().stream()
                .flatMap(valueCounts -> valueCounts.keySet().stream())
                .flatMap(attributeValue -> attributeValue.getVariants().stream())
                .map(Variant::getPrice)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public List<Product> search(String query) {
        if (query.isEmpty()) {
            return Collections.emptyList();
        }
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    private void setCategoryIfSelectedAndExists(Product product, ProductRequest request) throws CategoryNotFoundException {
        if (request.getCategoryId() != null) {
            var category = findByIdOrElseThrow(request.getCategoryId(), categoryRepository, CategoryNotFoundException::new);

            product.setCategory(category);
        }
    }
}
