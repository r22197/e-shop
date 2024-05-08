package eshop.backend;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.exception.InfiniteLoopException;
import eshop.backend.model.Category;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.request.CategoryRequest;
import eshop.backend.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void testGetAll() {
        List<Category> categories = new ArrayList<>();
        Category firstCategory = new Category();
        firstCategory.setId(1L);
        firstCategory.setName("Category 1");

        Category secondCategory = new Category();
        secondCategory.setId(2L);
        secondCategory.setName("Category 2");

        categories.add(firstCategory);
        categories.add(secondCategory);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.list();

        assertEquals(categories.size(), result.size());
        assertEquals(categories.get(0).getName(), result.get(0).getName());
        assertEquals(categories.get(1).getName(), result.get(1).getName());
    }

    @Test
    void testGetById() throws CategoryNotFoundException {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.read(1L);

        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
    }

    @Test
    void testGetById_CategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.read(1L));
    }

    @Test
    void testCreate() throws CategoryNotFoundException, InfiniteLoopException {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.create(new CategoryRequest());

        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
    }

    @Test
    void testUpdate() throws CategoryNotFoundException, InfiniteLoopException {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.update(new CategoryRequest());

        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
    }

    @Test
    void testUpdate_CategoryNotFound() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.update(new CategoryRequest()));
    }

    @Test
    void testDelete() throws CategoryNotFoundException {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.delete(1L);

        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void testDelete_CategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.delete(1L));
    }
}
