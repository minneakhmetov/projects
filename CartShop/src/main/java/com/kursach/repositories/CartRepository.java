/*
 * Developed by Razil Minneakhmetov on 12/21/18 5:50 PM.
 * Last modified 12/21/18 5:50 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.repositories;

import com.kursach.models.ProductCart;

import java.util.List;

public interface CartRepository {
    void create(Integer userId, Long productId);
    List<ProductCart> readProductsByUser(Integer userId);
    void delete();
    void deleteFromCart(Long productId, Integer userId);
    void deleteAllCart(Integer userId);
}