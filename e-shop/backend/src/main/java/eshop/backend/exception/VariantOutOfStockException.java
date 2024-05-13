package eshop.backend.exception;

public class VariantOutOfStockException extends Exception {
    public VariantOutOfStockException(Long variantId) {
        super(String.format("The requested quantity of variant with id %d is not available after purchase.", variantId));
    }
}
