/*
 * Developed by Razil Minneakhmetov on 12/21/18 5:50 PM.
 * Last modified 12/21/18 5:50 PM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import models.Cart;
import models.Product;
import services.Service;

import javax.sql.DataSource;
import java.util.List;

public interface CartRepository extends Repository {

    void create(Cart cart);
    List<Product> readProductsByUser(Long userId);
    void delete();
    void deleteFromCart(Long productId, Long userId);
    void deleteAllCart(Long userId);
}