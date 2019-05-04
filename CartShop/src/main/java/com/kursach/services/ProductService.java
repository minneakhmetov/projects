/*
 * Developed by Razil Minneakhmetov on 12/21/18 6:49 PM.
 * Last modified 12/21/18 6:49 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.services;

import com.kursach.forms.ServiceForm;
import com.kursach.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product read(Long id);
    List<Product> find(String string);
    void createService(ServiceForm form, Integer vkId);
}