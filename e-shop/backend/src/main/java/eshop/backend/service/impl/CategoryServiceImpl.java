package eshop.backend.service.impl;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.request.CategoryRequest;
import eshop.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static eshop.backend.utils.EntityUtils.findByIdOrElseThrow;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category create(CategoryRequest request) throws CategoryNotFoundException {
        var category = new Category(request);

        setParentCategoryIfExists(request, category);

        return categoryRepository.save(category);
    }

    @Override
    public Category read(Long categoryId) throws CategoryNotFoundException {
        return findByIdOrElseThrow(categoryId, categoryRepository, CategoryNotFoundException::new);
    }

    @Override
    public Category update(CategoryRequest request) throws CategoryNotFoundException {
        var persistedCategory = read(request.getId());

        persistedCategory.setName(request.getName());
        setParentCategoryIfExists(request, persistedCategory);

        return categoryRepository.save(persistedCategory);
    }

    @Override
    public void delete(Long categoryId) throws CategoryNotFoundException {
        var category = read(categoryId);

        removeParentFromChildCategories(category);

        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<Category> list() {
        return categoryRepository.findAll();
    }

    private void setParentCategoryIfExists(CategoryRequest request, Category category) throws CategoryNotFoundException {
        if (request.getParentId() != null) {
            var parent = findByIdOrElseThrow(request.getParentId(), categoryRepository, CategoryNotFoundException::new);

            category.setParent(parent);

            if (isInfiniteLoop(category, parent))
                throw new IllegalArgumentException("Infinite loop, wrong parentId.");
        }
    }

    private void removeParentFromChildCategories(Category category) {
        category.getChildCategories()
                .forEach(childCategory -> childCategory.setParent(null));
    }

    private boolean isInfiniteLoop(Category category, Category parent) {
        while (parent != null) {
            if (parent.getId().equals(category.getId())) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }
}