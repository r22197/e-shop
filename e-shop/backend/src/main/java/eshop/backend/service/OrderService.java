package eshop.backend.service;

import eshop.backend.exception.CartIsEmptyException;
import eshop.backend.exception.UserNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.Order;

import java.util.Set;

public interface OrderService {
    Order create(String email) throws UserNotFoundException, CartIsEmptyException, VariantNotFoundException;
    Order read(Long orderId);
    Order update();
    void delete(Long orderId);
    Set<Order> list();
    void cancel();
}
