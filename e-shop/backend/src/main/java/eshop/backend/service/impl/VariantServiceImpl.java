package eshop.backend.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.Variant;
import eshop.backend.repository.AttributeValueRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.repository.VariantRepository;
import eshop.backend.request.VariantRequest;
import eshop.backend.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static eshop.backend.utils.EntityUtils.findByIdOrElseThrow;

@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements VariantService {
    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;
    private final AttributeValueRepository attributeValueRepository;

    @Override
    public Variant create(VariantRequest request) throws ProductNotFoundException {
        var product = findByIdOrElseThrow(request.id(), productRepository, ProductNotFoundException::new);
        var variant = new Variant(request);

        variant.setProduct(product);
        manageAttributeValuesIfExist(variant, request);

        return variantRepository.save(variant);
    }

    @Override
    public Variant read(Long variantId) throws VariantNotFoundException {
        var variant = findByIdOrElseThrow(variantId, variantRepository, VariantNotFoundException::new);

        calculateDiscountedPriceForJson(variant);

        return variant;
    }

    @Override
    public Variant update(VariantRequest request) throws VariantNotFoundException {
        var variant = findByIdOrElseThrow(request.id(), variantRepository, VariantNotFoundException::new);

        variant.setQuantity(request.quantity());
        variant.setPrice(request.price());
        manageAttributeValuesIfExist(variant, request);

        return variantRepository.save(variant);
    }

    @Override
    public void delete(Long variantId) throws VariantNotFoundException {
        var variant = findByIdOrElseThrow(variantId, variantRepository, VariantNotFoundException::new);

        variantRepository.delete(variant);
    }

    @Override
    public List<Variant> list(Sort.Direction direction, String attribute) {
        return variantRepository.findAll(Sort.by(direction, attribute));
    }

    private void manageAttributeValuesIfExist(Variant variant, VariantRequest request) {
        var isAttributeValuesNotEmpty = request.attributeValueIds().isEmpty();

        if (isAttributeValuesNotEmpty) {
            var attributeValues = attributeValueRepository.findAllById(request.attributeValueIds());
            variant.setValues(new HashSet<>(attributeValues));
        }
    }

    @JsonProperty("discounted_price")
    private BigDecimal calculateDiscountedPriceForJson(Variant variant) {
        var discount = variant.getProduct().getCategory().getDiscount();

        if (discount == null)
            return variant.getPrice();

        return variant.getPrice().multiply(BigDecimal.valueOf(1 - discount.getAmount()));
    }
}
