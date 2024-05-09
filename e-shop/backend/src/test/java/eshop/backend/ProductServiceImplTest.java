package eshop.backend;
import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.request.ProductRequest;
import eshop.backend.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, categoryRepository);
    }

    @Test
    void testSearchProducts() {
        String query = "test";
        List<Product> expectedProducts = Collections.emptyList();
        when(productRepository.findByNameContainingIgnoreCase(query.toLowerCase())).thenReturn(expectedProducts);

        List<Product> resultProducts = productService.search(query);

        assertEquals(expectedProducts, resultProducts);
        verify(productRepository).findByNameContainingIgnoreCase(query.toLowerCase());
    }

    @Test
    void testGetProductsInCategory() throws CategoryNotFoundException {
        long categoryId = 1;
        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "asc";
        double lowPrice = 0;
        double maxPrice = 100;
        Page<Product> expectedPage = new PageImpl<>(Collections.emptyList());
        Category category = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        //when(productRepository.findByCategoryAndPriceBetween(category, lowPrice, maxPrice, PageRequest.of(pageNumber, pageSize, Sort.by("price").ascending()))).thenReturn(expectedPage);

        Page<Product> resultPage = productService.listByCategory(categoryId, pageNumber, pageSize, sortBy, lowPrice, maxPrice);

        assertEquals(expectedPage, resultPage);
        verify(categoryRepository).findById(categoryId);
        //verify(productRepository).findByCategoryAndPriceBetween(category, lowPrice, maxPrice, PageRequest.of(pageNumber, pageSize, Sort.by("price").ascending()));
    }

    @Test
    void testGetById() throws ProductNotFoundException {
        long productId = 1;
        Product expectedProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        Product resultProduct = productService.read(productId);

        assertEquals(expectedProduct, resultProduct);
        verify(productRepository).findById(productId);
    }

    @Test
    void testGetByIdThrowsProductNotFoundException() {
        long productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.read(productId));
        verify(productRepository).findById(productId);
    }

    @Test
    void testCreate() throws CategoryNotFoundException {
        Product product = new Product();
        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        Product createdProduct = productService.create(new ProductRequest());

        assertNotNull(createdProduct);
        assertEquals(category, createdProduct.getCategory());
        verify(productRepository).save(product);
    }

    @Test
    void testUpdate() throws ProductNotFoundException, CategoryNotFoundException {
        long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Name");
        updatedProduct.setDescription("Updated Description");
        //updatedProduct.setPrice(20.0);
        updatedProduct.setImagePath("updatedImagePath");
        Category category = new Category();
        category.setId(1L);
        updatedProduct.setCategory(category);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        Product resultProduct = productService.update(new ProductRequest());

        assertNotNull(resultProduct);
        assertEquals(updatedProduct.getName(), resultProduct.getName());
        assertEquals(updatedProduct.getDescription(), resultProduct.getDescription());
        //assertEquals(updatedProduct.getPrice(), resultProduct.getPrice());
        assertEquals(updatedProduct.getImagePath(), resultProduct.getImagePath());
        assertEquals(category, resultProduct.getCategory());
        verify(productRepository).save(existingProduct);
    }

    @Test
    void testUpdateThrowsProductNotFoundException() {
        long productId = 1;
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);

        assertThrows(ProductNotFoundException.class, () -> productService.update(new ProductRequest()));
        verify(productRepository).findById(productId);
    }

    @Test
    void testDelete() throws ProductNotFoundException {
        long productId = 1;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.delete(productId);

        verify(productRepository).delete(product);
    }

    @Test
    void testDeleteThrowsProductNotFoundException() {
        long productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.delete(productId));
        verify(productRepository).findById(productId);
    }
}
