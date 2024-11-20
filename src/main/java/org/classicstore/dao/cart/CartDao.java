package org.classicstore.dao.cart;

import org.classicstore.model.Cart;
import org.classicstore.model.Product;
import org.classicstore.model.User;

import java.util.*;

public class CartDao {


private final Map<Product, Cart> cartItems = new HashMap<>();


    public String addToCart(User user, Product product, int quantity) {

        if (cartItems.containsKey(product)) {
            return product.getProductName() + " is already in the cart!";
        }
        Cart cart = new Cart(user, product, quantity);
        cartItems.put(product, cart);
        return product.getProductName() + " added to cart!";


    }


    public void updateQuantity(User user, Product product, int change) {
        if (cartItems.containsKey(product)) {
            Cart cart = cartItems.get(product);
            int newQuantity = cart.getQuantity() + change;
            if (newQuantity >= 1) {
                cart.setQuantity(newQuantity);
            } else {
                cart.setQuantity(1);
            }
        }
    }


    public void removeFromCart(User user, Product product) {
        cartItems.remove(product);
    }

    public void clearCart(User user) {

    }

    public List<Cart> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }

}


