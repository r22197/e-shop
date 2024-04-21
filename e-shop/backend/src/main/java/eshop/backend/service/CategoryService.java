package eshop.backend.service;

import eshop.backend.exception.CategoryNotFoundException;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<Category> getAll();
    Category getById(Long id) throws CategoryNotFoundException;
    Category create(Category category);

    Category update(Category category) throws CategoryNotFoundException;

    void delete(Long id) throws CategoryNotFoundException;
}
