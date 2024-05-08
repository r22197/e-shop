package eshop.backend.service.impl;

import eshop.backend.exception.CartIsEmptyException;
import eshop.backend.exception.UserNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.CartItem;
import eshop.backend.model.Order;
import eshop.backend.model.OrderItem;
import eshop.backend.repository.OrderRepository;
import eshop.backend.repository.UserRepository;
import eshop.backend.service.CartService;
import eshop.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
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
    public Order read(Long orderId) {
        return null;
    }

    @Override
    public Order update() {
        return null;
    }

    @Override
    public void delete(Long orderId) {

    }

    @Override
    public Set<Order> list() {
        return null;
    }

    @Override
    public void cancel() {

    }

    private Set<OrderItem> convertToOrderItems(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(OrderItem::new)
                .collect(Collectors.toSet());
    }
}
