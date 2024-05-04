package eshop.backend.controller;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.mapper.CategoryMapper;
import eshop.backend.model.Category;
import eshop.backend.request.CategoryRequest;
import eshop.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryRequest>> getAllCategories() {
        List<CategoryRequest> categoryRequestList = categoryService.list().stream()
                .map(categoryMapper::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoryRequestList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryRequest> getCategoryById(@PathVariable Long id) throws CategoryNotFoundException {
        Category category = categoryService.read(id);
        CategoryRequest categoryRequest = categoryMapper.convertToDto(category);

        return ResponseEntity.ok(categoryRequest);
    }

    @PostMapping
    public ResponseEntity<CategoryRequest> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryMapper.convertToEntity(categoryRequest);
        categoryService.create(category);

        return new ResponseEntity<>(categoryRequest, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryRequest> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) throws CategoryNotFoundException {
        if (!Objects.equals(id, categoryRequest.getId())) {
            throw new CategoryNotFoundException(id);
        }
        Category category = categoryMapper.convertToEntity(categoryRequest);
        categoryService.update(category);

        return ResponseEntity.ok(categoryRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws CategoryNotFoundException {
        categoryService.delete(id);

        return ResponseEntity.ok().build();
    }
}