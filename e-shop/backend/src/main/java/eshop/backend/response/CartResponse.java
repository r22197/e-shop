package eshop.backend.response;

import eshop.backend.model.Cart;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartResponse {
    private Cart cart;
    private BigDecimal totalPrice;
}
