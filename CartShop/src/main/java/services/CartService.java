/*
 * Developed by Razil Minneakhmetov on 12/21/18 6:46 PM.
 * Last modified 12/21/18 6:46 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import models.Cart;
import models.Product;

import java.util.List;

public interface CartService extends Service {
    void addToCart(Cart cart);

    List<Product> getProductsInCart(Long id);

    void deleteFromCart(Long productId, Long userId);

    void deleteAllCart(Long userId);
}
