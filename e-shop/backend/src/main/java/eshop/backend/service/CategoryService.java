package eshop.backend.service;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.response.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category getById(Long id)  throws CategoryNotFoundException;
    Category create(CategoryDto categoryDto) throws CategoryNotFoundException;

    Category update(Long id, CategoryDto categoryDto) throws CategoryNotFoundException;

    void delete(Long id) throws CategoryNotFoundException;
}
