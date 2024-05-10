package eshop.backend.request;

import eshop.backend.enums.DiscountType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class DiscountRequest {
    private Long id;
    private String name;
    private double amount;
    private DiscountType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Set<Long> categoryIds;
}

