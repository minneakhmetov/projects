/*
 * Developed by Razil Minneakhmetov on 10/29/18 2:48 AM.
 * Last modified 10/29/18 2:48 AM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import models.Cart;
import models.Product;
import repositories.CartRepository;

import java.util.List;

public class CartServiceImpl implements CartService {

    private CartRepository repository;

    public CartServiceImpl(CartRepository repository) {
        this.repository =  repository;
    }

    public void addToCart(Cart cart){
        repository.create(cart);
    }

    public List<Product> getProductsInCart(Long id){
        return repository.readProductsByUser(id);
    }

    public void deleteFromCart(Long productId, Long userId){
        repository.deleteFromCart(productId, userId);
    }

    public void deleteAllCart(Long userId){
        repository.deleteAllCart(userId);
    }

}