package eshop.backend.request;


import eshop.backend.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Category category;

    public ProductDto() {
    }

}
