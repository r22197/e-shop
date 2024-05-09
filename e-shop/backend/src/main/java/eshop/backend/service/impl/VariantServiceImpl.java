package eshop.backend.service.impl;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.Variant;
import eshop.backend.repository.AttributeValueRepository;
import eshop.backend.repository.ProductRepository;
import eshop.backend.repository.VariantRepository;
import eshop.backend.request.VariantRequest;
import eshop.backend.response.VariantResponse;
import eshop.backend.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static eshop.backend.utils.EntityUtils.findByIdOrElseThrow;

@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements VariantService {
    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final PriceServiceImpl priceService;

    @Override
    public Variant create(VariantRequest request) throws ProductNotFoundException {
        var product = findByIdOrElseThrow(request.getId(), productRepository, ProductNotFoundException::new);
        var variant = new Variant(request);

        variant.setProduct(product);
        manageAttributeValuesIfExist(variant, request);

        return variantRepository.save(variant);
    }

    @Override
    public VariantResponse read(Long variantId) throws VariantNotFoundException {
        var variant = findByIdOrElseThrow(variantId, variantRepository, VariantNotFoundException::new);
        //var priceValue = priceService.readLastPriceByVariantId(variantId).getPrice();
        var response = new VariantResponse(variant);

        //response.setStandardPrice(priceValue);
        setDiscountPriceIfExists(response);

        return response;
    }

    @Override
    public Variant update(VariantRequest request) throws VariantNotFoundException {
        var persistedVariant = findByIdOrElseThrow(request.getId(), variantRepository, VariantNotFoundException::new);

        persistedVariant.setQuantity(request.getQuantity());
        manageAttributeValuesIfExist(persistedVariant, request);
        findIfPriceChangedThenCreate(request, persistedVariant);

        return variantRepository.save(persistedVariant);
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

    private void setDiscountPriceIfExists(VariantResponse response) throws VariantNotFoundException {
        var discount = response.getProduct().getCategory().getDiscount();
        //var priceValue = priceService.readLastPriceByVariantId(response.getId()).getPrice();
        var isCategoryDiscounted = discount != null;

        if (isCategoryDiscounted) {
            var discountAmount = BigDecimal.valueOf(discount.getAmount());
            //var discountedPrice = priceValue.multiply(BigDecimal.ONE.subtract(discountAmount));
            //response.setPriceAfterDiscount(discountedPrice);
        }
    }

    private void manageAttributeValuesIfExist(Variant variant, VariantRequest request) {
        var isAttributeValuesNotEmpty = request.getAttributeValueIds() != null && !request.getAttributeValueIds().isEmpty();

        if (isAttributeValuesNotEmpty) {
            var attributeValues = attributeValueRepository.findAllById(request.getAttributeValueIds());
            variant.setValues(new HashSet<>(attributeValues));
        }
    }

    private void findIfPriceChangedThenCreate(VariantRequest request, Variant persistedVariant) throws VariantNotFoundException {
        /*
        var price = priceService.readLastPriceByVariantId(persistedVariant.getId()).getPrice();
        var isNotIdenticalAsLastPrice = !Objects.equals(price, request.getPriceRequest().getPrice());

        if (isNotIdenticalAsLastPrice) {
            priceService.create(request.getPriceRequest());
        }

         */
    }
}
