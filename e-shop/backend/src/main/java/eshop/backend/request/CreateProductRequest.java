package eshop.backend.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
    private String name;
    private String description;
    private double price;

    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;


    public CreateProductRequest() {
    }

    public CreateProductRequest(String name, String description, double price, String topLevelCategory, String secondLevelCategory, String thirdLevelCategory) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.topLevelCategory = topLevelCategory;
        this.secondLevelCategory = secondLevelCategory;
        this.thirdLevelCategory = thirdLevelCategory;
    }
}
