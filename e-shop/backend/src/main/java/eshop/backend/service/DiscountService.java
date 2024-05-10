package eshop.backend.service;

import eshop.backend.exception.DiscountNotFoundException;
import eshop.backend.model.Discount;
import eshop.backend.request.DiscountRequest;

public interface DiscountService {
    Discount create(DiscountRequest request);
    Discount read(Long discountId) throws DiscountNotFoundException;
    Discount update(DiscountRequest request) throws DiscountNotFoundException;
    void delete(Long discountId) throws DiscountNotFoundException;

}
