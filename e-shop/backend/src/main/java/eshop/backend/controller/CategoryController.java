package eshop.backend.controller;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.mapper.CategoryMapper;
import eshop.backend.model.Category;
import eshop.backend.dto.CategoryDto;
import eshop.backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoryDtoList = categoryService.getAll().stream()
                .map(categoryMapper::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoryDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) throws CategoryNotFoundException {
        Category category = categoryService.getById(id);
        CategoryDto categoryDto = categoryMapper.convertToDto(category);

        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.convertToEntity(categoryDto);
        categoryService.create(category);

        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) throws CategoryNotFoundException {
        if (!Objects.equals(id, categoryDto.getId())) {
            throw new CategoryNotFoundException("ID of the category is not equal to the one being updated.");
        }
        Category category = categoryMapper.convertToEntity(categoryDto);
        categoryService.update(category);

        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws CategoryNotFoundException {
        categoryService.delete(id);

        return ResponseEntity.ok().build();
    }
}