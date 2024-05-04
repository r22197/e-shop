package eshop.backend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CartItemRequest {
    private Long id;
    private Integer quantity;
    private Long cart;
    private double price;

    private Long productId;
}
