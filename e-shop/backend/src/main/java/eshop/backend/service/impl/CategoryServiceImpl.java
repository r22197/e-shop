package eshop.backend.service.impl;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import eshop.backend.repository.CategoryRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Product> getProductsInCategory(Long id, Integer pageNumber, Integer pageSize, String sortBy) throws CategoryNotFoundException {
        Category category = getById(id);
        Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by("price").ascending() : Sort.by("price").descending();

        return productRepository.findByCategory(category, PageRequest.of(pageNumber, pageSize, sort));
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

        return category;
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