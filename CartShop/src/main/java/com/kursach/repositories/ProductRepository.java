/*
 * Developed by Razil Minneakhmetov on 12/21/18 5:52 PM.
 * Last modified 12/21/18 5:52 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.repositories;

import com.kursach.models.Product;

import java.util.List;

public interface ProductRepository {
    void create(Product product);
    void delete();
    Product readOne(Long id);
    List<Product> readAll();
    List<Product> find(String string);
}