package eshop.backend.service.impl;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.request.CategoryDto;
import eshop.backend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);

        return category
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
    }

    @Override
    public Category create(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setParentCategory(categoryDto.getParentCategory());
        category.setProducts(categoryDto.getProducts());

        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, CategoryDto categoryDto) throws CategoryNotFoundException {
        Category category = getById(id);

        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setParentCategory(categoryDto.getParentCategory());
        category.setProducts(categoryDto.getProducts());

        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) throws CategoryNotFoundException {
        Category category = getById(id);

        categoryRepository.delete(category);

    }
}
