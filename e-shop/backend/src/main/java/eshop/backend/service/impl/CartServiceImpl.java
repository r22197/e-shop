package eshop.backend.service.impl;

import eshop.backend.exception.CartNotFoundException;
import eshop.backend.exception.UserNotFoundException;
import eshop.backend.exception.VariantNotFoundException;
import eshop.backend.model.Cart;
import eshop.backend.model.CartItem;
import eshop.backend.repository.CartItemRepository;
import eshop.backend.repository.CartRepository;
import eshop.backend.repository.UserRepository;
import eshop.backend.repository.VariantRepository;
import eshop.backend.response.CartResponse;
import eshop.backend.service.CartService;
import eshop.backend.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static eshop.backend.utils.EntityUtils.findByEmailOrElseThrow;
import static eshop.backend.utils.EntityUtils.findByIdOrElseThrow;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository itemRepository;
    private final VariantRepository variantRepository;
    private final UserRepository userRepository;
    private final PriceService priceService;

    @Override
    public Cart createByUserEmail(String email) throws UserNotFoundException {
        var user = findByEmailOrElseThrow(email, userRepository);

        Cart cart = new Cart();
        cart.setUser(user); //todo u create uživatele

        return cartRepository.save(cart);
    }

    @Override
    public Cart readByUserEmail(String email) throws UserNotFoundException, VariantNotFoundException {
        var user = findByEmailOrElseThrow(email, userRepository);
        var cart = cartRepository.findCartByUser(user);
        var response = new CartResponse();

        response.setCart(cart);
        response.setTotalPrice(cart.getTotalPriceForJson());

        return cartRepository.findCartByUser(user);
    }

    @Override
    public Cart addItemByUserEmail(String email, Long variantId) throws UserNotFoundException, VariantNotFoundException {
        var cart = readByUserEmail(email);
        var variant = findByIdOrElseThrow(variantId, variantRepository, VariantNotFoundException::new);
        var item = new CartItem(cart, variant);

        cart.addItem(item);

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateItemQuantityByUserEmail(String email, Long variantId, Integer quantity) throws UserNotFoundException, VariantNotFoundException {
        var cart = readByUserEmail(email);
        var variant = findByIdOrElseThrow(variantId, variantRepository, VariantNotFoundException::new);
        var item = itemRepository.findByCartAndVariant(cart, variant);

        item.setQuantity(quantity);
        itemRepository.save(item); //todo: je nutné?

        return cartRepository.save(cart);
    }

    @Override
    public void removeItemByUserEmail(String email, Long variantId) throws UserNotFoundException, VariantNotFoundException {
        var cart = readByUserEmail(email);
        var variant = findByIdOrElseThrow(variantId, variantRepository, VariantNotFoundException::new);
        var item = itemRepository.findByCartAndVariant(cart, variant);

        cart.removeItem(item);

        cartRepository.save(cart);
    }

    @Override
    public void delete(Long cartId) throws CartNotFoundException {
        var cart = findByIdOrElseThrow(cartId, cartRepository, CartNotFoundException::new);

        cartRepository.delete(cart);
    } //todo: použít u delete user

    @Override
    public void deleteAllItemsByUserEmail(String email) throws UserNotFoundException {
        var user = findByEmailOrElseThrow(email, userRepository);

        itemRepository.deleteAllByCartUser(user);
    }
}
