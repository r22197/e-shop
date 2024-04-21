package eshop.backend.service.impl;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.repository.CategoryRepository;
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
    public Category create(Category category) {
        categoryRepository.save(category);

        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) throws CategoryNotFoundException {
        Category existingCategory = getById(category.getId());
        existingCategory.setName(category.getName());
        existingCategory.setParent(category.getParent());
        categoryRepository.save(existingCategory);

        return category;
    }

    @Override
    public void delete(Long id) throws CategoryNotFoundException {
        Category category = getById(id);
        category.getChildCategories().forEach(childCategory -> childCategory.setParent(null));

        categoryRepository.delete(category);
    }
}