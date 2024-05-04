package eshop.backend.request;

import lombok.Data;

import java.util.Set;

@Data
public class ReviewRequest {
    private Long id;
    private int rating;
    private String text;
    private Set<String> pros;
    private Set<String> cons;
    private Long userId;
    private Long productId;
}
