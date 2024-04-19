package eshop.backend.service;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.model.Product;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<Category> getAll();

    Set<Product> getProductsInCategory(Long id) throws CategoryNotFoundException;

    Category getById(Long id) throws CategoryNotFoundException;
    Category create(Category category);

    Category update(Category category) throws CategoryNotFoundException;

    void delete(Long id) throws CategoryNotFoundException;
}
