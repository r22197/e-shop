package eshop.backend;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Attribute;
import eshop.backend.model.AttributeValue;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import eshop.backend.model.Variant;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.repository.ProductSearchSpecification;
import eshop.backend.request.ProductSearchRequest;
import eshop.backend.service.ProductService;
import eshop.backend.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {
    private ProductService productService;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        categoryRepository = Mockito.mock(CategoryRepository.class);
        productService = new ProductServiceImpl(productRepository, categoryRepository);
    }

    @Test
    void testPageProductsBySpecification() throws CategoryNotFoundException {
        Category category = new Category();
        category.setName("Test Category");

        Product product1 = new Product();
        product1.setName("Test Product 1");
        product1.setDescription("Test Description 1");
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setName("Test Product 2");
        product2.setDescription("Test Description 2");
        product2.setCategory(category);

        Attribute attribute1 = new Attribute();
        attribute1.setName("Test Attribute 1");

        Attribute attribute2 = new Attribute();
        attribute2.setName("Test Attribute 2");

        AttributeValue attributeValue1 = new AttributeValue();
        attributeValue1.setValue("Test Value 1");
        attributeValue1.setAttribute(attribute1);

        AttributeValue attributeValue2 = new AttributeValue();
        attributeValue2.setValue("Test Value 2");
        attributeValue2.setAttribute(attribute2);

        Set<Attribute> attributes1 = new HashSet<>();
        attributes1.add(attribute1);
        product1.setAttributes(attributes1);

        Set<Attribute> attributes2 = new HashSet<>();
        attributes2.add(attribute2);
        product2.setAttributes(attributes2);

        Set<Variant> variants1 = new HashSet<>();
        Variant variant1 = new Variant();
        variant1.setPrice(BigDecimal.valueOf(10.0));
        variants1.add(variant1);

        product2.setVariants(variants1);

        /*
        when(categoryRepository.findById(category.getId())).thenReturn(java.util.Optional.of(category));
        when(productRepository.findAllByCategory(any(ProductSearchSpecification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(product1, product2), PageRequest.of(0, 10), 2));

        ProductSearchRequest searchRequest = new ProductSearchRequest(
                category.getId(),
                BigDecimal.valueOf(5.0),
                BigDecimal.valueOf(15.0),
                new HashSet<>(Arrays.asList(attribute1.getId(), attribute2.getId()))
        );

        Page<Product> result = productService.pageByCategoryAndSpecifications(searchRequest, PageRequest.of(0, 10));

        System.out.println(result.stream().toList());

        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().contains(product1));
        assertTrue(result.getContent().contains(product2));

         */
    }
}