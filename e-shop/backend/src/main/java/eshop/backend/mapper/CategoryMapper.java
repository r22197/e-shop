package eshop.backend.mapper;

import eshop.backend.request.CategoryRequest;
import eshop.backend.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryRequest convertToDto(Category category) {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(category.getId());
        categoryRequest.setName(category.getName());

        categoryRequest.setParent(category.getParent() != null ? category.getParent().getId() : null);

        return categoryRequest;
    }

    public Category convertToEntity(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setId(categoryRequest.getId());
        category.setName(categoryRequest.getName());

        if (categoryRequest.getParent() != null) {
            Category parent = new Category();
            parent.setId(categoryRequest.getParent());
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        return category;
    }
}