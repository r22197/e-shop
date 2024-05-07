package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.Variant;
import eshop.backend.request.VariantRequest;
import eshop.backend.response.VariantResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface VariantService {
    Variant create(VariantRequest request) throws ProductNotFoundException;
    VariantResponse read(Long variantId) throws VariantNotFoundException;
    Variant update(VariantRequest request) throws VariantNotFoundException;
    void delete(Long variantId) throws VariantNotFoundException;
    List<Variant> list(Sort.Direction direction, String attribute);
}
