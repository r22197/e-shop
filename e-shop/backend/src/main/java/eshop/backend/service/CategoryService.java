package eshop.backend.service;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    Category read(Long categoryId) throws CategoryNotFoundException;
    Category update(Category category) throws CategoryNotFoundException;
    void delete(Long categoryId) throws CategoryNotFoundException;
    List<Category> list();

}
