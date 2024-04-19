package eshop.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CartHasProductDto {
    private Long id;
    private Integer quantity;
    private Long cart;
    private Long product;
}
