package eshop.backend.request;

import lombok.Data;

@Data
public class VariantRequest {
    private Long id;
    private Integer quantity;
    private Long productId;
    private double price;
}
