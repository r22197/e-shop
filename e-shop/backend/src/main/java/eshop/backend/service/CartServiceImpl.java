package eshop.backend.service;

import eshop.backend.exception.ProductNotFoundException;
import eshop.backend.model.Cart;
import eshop.backend.model.CartHasProduct;
import eshop.backend.model.Product;
import eshop.backend.model.User;
import eshop.backend.repository.CartRepository;
import eshop.backend.request.AddItemRequest;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private ProductService productService;
    private CartHasProductService cartHasProductService;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService, CartHasProductService cartHasProductService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartHasProductService = cartHasProductService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public String addProductToCart(Long userId, AddItemRequest addItemRequest) throws ProductNotFoundException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.getProductById(addItemRequest.getProductId());
        CartHasProduct isPresent = cartHasProductService.isProductInCart(cart, product);

        if (isPresent == null) {
            CartHasProduct cartHasProduct = new CartHasProduct();
            cartHasProduct.setProduct(product);
            cartHasProduct.setCart(cart);

            double totalPrice = addItemRequest.getPrice() * cartHasProduct.getAmount();
            cartHasProduct.setPrice((int) totalPrice);

            CartHasProduct createdProductInCart = cartHasProductService.createProductInCart(cartHasProduct);
            cart.getCartHasProducts().add(createdProductInCart);
        }

        return "Product added";
    }

    @Override
    public Cart findUserCartById(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);

        double totalPrice = 0;
        int totalAmount = 0;

        for (CartHasProduct product : cart.getCartHasProducts()) {
            totalPrice += product.getPrice();
            totalAmount += (int) product.getAmount();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalQuantity(totalAmount);

        return cartRepository.save(cart);
    }
}
