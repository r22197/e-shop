package eshop.backend.exception;

public class AttributeValueNotFoundException extends Exception {
    public AttributeValueNotFoundException(Long attributeId) {
        super(String.format("Attribute with ID %d was not found.", attributeId));
    }
}

//todo: změnit hodnoty attribute na attribute value pro všechny třídy