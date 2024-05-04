package eshop.backend.service;

import eshop.backend.exception.AttributeNotFoundException;
import eshop.backend.model.Attribute;

import java.util.List;

public interface AttributeService {
    Attribute create(Attribute attribute);
    Attribute read(Long attributeId) throws AttributeNotFoundException;
    Attribute update(Attribute attribute) throws AttributeNotFoundException;
    void delete(Long attributeId) throws AttributeNotFoundException;
    List<Attribute> list();
}
