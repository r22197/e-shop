package eshop.backend.mapper;

import eshop.backend.dto.ProductDto;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());

        productDto.setCategory(product.getCategory() != null ? product.getCategory().getId() : null);

        return productDto;
    }

    public Product convertToEntity(ProductDto productDto) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        if (productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory());
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        return product;
    }
}