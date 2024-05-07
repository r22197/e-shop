package eshop.backend.request;

import lombok.Data;

@Data
public class WishlistRequest {
    private Long id;
    private Long userId;
    private Long variantId;
}
