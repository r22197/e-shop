package eshop.backend.service.impl;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category read(Long categoryId) throws CategoryNotFoundException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    @Override
    public Category update(Category category) throws CategoryNotFoundException {
        var persistedCategory = read(category.getId());

        persistedCategory.setName(category.getName());
        persistedCategory.setParent(category.getParent());

        return categoryRepository.save(persistedCategory);
    }

    @Override
    public void delete(Long categoryId) throws CategoryNotFoundException {
        Category category = read(categoryId);

        category.getChildCategories()
                .forEach(childCategory -> childCategory.setParent(null));

        categoryRepository.delete(category);
    }

    @Override
    public List<Category> list() {
        return categoryRepository.findAll();
    }
}