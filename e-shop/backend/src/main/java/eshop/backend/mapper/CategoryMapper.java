package eshop.backend.mapper;

import eshop.backend.dto.CategoryDto;
import eshop.backend.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        categoryDto.setParent(category.getParent() != null ? category.getParent().getId() : null);

        return categoryDto;
    }

    public Category convertToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());

        if (categoryDto.getParent() != null) {
            Category parent = new Category();
            parent.setId(categoryDto.getParent());
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        return category;
    }
}