package eshop.backend.request;

import eshop.backend.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class CartRequest {
    private Long id;
    private double price;
    private Set<Long> cartItemIds;
}