package eshop.backend.service.impl;

import eshop.backend.enums.OrderStatus;
import eshop.backend.exception.*;
import eshop.backend.model.CartItem;
import eshop.backend.model.Order;
import eshop.backend.model.OrderItem;
import eshop.backend.repository.OrderRepository;
import eshop.backend.repository.UserRepository;
import eshop.backend.service.CartService;
import eshop.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static eshop.backend.utils.EntityUtils.findByEmailOrElseThrow;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    @Override
    public Order create(String email) throws CartIsEmptyException, VariantNotFoundException, UserNotFoundException {
        var user = findByEmailOrElseThrow(email, userRepository);
        var cart = cartService.readByUserEmail(email);

        if (cart.getCartItems().isEmpty()) {
            throw new CartIsEmptyException();
        }

        var orderItems = convertToOrderItems(cart.getCartItems());
        var order = new Order(user, orderItems);

        cartService.deleteAllItemsByUserEmail(email);
        return orderRepository.save(order);
    }

    @Override
    public Order read(Long orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Override
    public Order update() {
        return null;
    }

    @Override
    public void delete(Long orderId) {

    }

    @Override
    public Page<Order> list(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public void cancel(Long orderId) throws OrderNotFoundException {
        var order = read(orderId);

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    private Set<OrderItem> convertToOrderItems(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> {
                    try {
                        validateQuantityOfVariant(cartItem);
                        return new OrderItem(cartItem);
                    } catch (VariantOutOfStockException exception) {
                        throw new RuntimeException(exception);
                    }
                })
                .collect(Collectors.toSet());
    }

    private void validateQuantityOfVariant(CartItem cartItem) throws VariantOutOfStockException {
        if (cartItem.getVariant().getQuantity() < 1) {
            throw new VariantOutOfStockException(cartItem.getVariant().getId());
        }
    }
}
