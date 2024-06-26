package eshop.backend.service;

import eshop.backend.exception.AttributeNotFoundException;
import eshop.backend.model.Attribute;
import eshop.backend.request.AttributeRequest;

import java.util.List;

public interface AttributeService {
    Attribute create(AttributeRequest attribute);
    Attribute read(Long attributeId) throws AttributeNotFoundException;
    Attribute update(AttributeRequest attribute) throws AttributeNotFoundException;
    void delete(Long attributeId) throws AttributeNotFoundException;
    List<Attribute> list();
}
