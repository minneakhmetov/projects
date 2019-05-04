/*
 * Developed by Razil Minneakhmetov on 10/29/18 2:48 AM.
 * Last modified 10/29/18 2:48 AM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.services;
import com.kursach.dto.ProductCartDto;
import com.kursach.models.ProductCart;
import com.kursach.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartServiceImpl implements CartService {

    private CartRepository repository;

    @Autowired
    public CartServiceImpl(CartRepository repository) {
        this.repository =  repository;
    }

    public void addToCart(ProductCartDto cart){
        repository.create(cart.getUserId(), cart.getProductId());
    }

    public List<ProductCart> getProductsInCart(Integer id){
        return repository.readProductsByUser(id);
    }

    public void deleteFromCart(Long productId, Integer userId){
        repository.deleteFromCart(productId, userId);

    }

    public void deleteAllCart(Integer userId){
        repository.deleteAllCart(userId);
    }



}