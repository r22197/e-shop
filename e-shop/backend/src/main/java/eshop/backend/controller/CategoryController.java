package eshop.backend.controller;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.response.CategoryDto;
import eshop.backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        List<CategoryDto> categoriesDto = new ArrayList<>();

        categories.forEach(category -> categoriesDto.add(
                convertFromDto(category)));
        return ResponseEntity.ok(categoriesDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) throws CategoryNotFoundException {
        Category category = categoryService.getById(id);

        CategoryDto categoryDto = convertFromDto(category);
        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) throws CategoryNotFoundException {
        categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) throws CategoryNotFoundException {
        categoryService.update(id, categoryDto);
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws CategoryNotFoundException {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    private CategoryDto convertFromDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getParent() != null ? category.getParent().getId() : null
        );
    }
}