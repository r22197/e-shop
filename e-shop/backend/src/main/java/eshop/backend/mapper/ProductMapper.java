package eshop.backend.mapper;

import eshop.backend.request.ProductRequest;
import eshop.backend.model.Category;
import eshop.backend.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductRequest convertToDto(Product product) {
        ProductRequest productRequest = new ProductRequest();

        productRequest.setId(product.getId());
        productRequest.setName(product.getName());
        productRequest.setDescription(product.getDescription());
        productRequest.setPrice(product.getPrice());
        productRequest.setImagePath(product.getImagePath());

        productRequest.setCategory(product.getCategory() != null ? product.getCategory().getId() : null);

        return productRequest;
    }

    public Product convertToEntity(ProductRequest productRequest) {
        Product product = new Product();

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImagePath(productRequest.getImagePath());

        if (productRequest.getCategory() != null) {
            Category category = new Category();
            category.setId(productRequest.getCategory());
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        return product;
    }
}