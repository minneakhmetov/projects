/*
 * Developed by Razil Minneakhmetov on 12/21/18 6:49 PM.
 * Last modified 12/21/18 6:49 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import forms.LoginForm;
import models.Product;
import models.ProductActivity;

import java.util.List;

public interface ProductService extends Service {
    List<ProductActivity> getAll();
    void addAll(List<LoginForm> forms);
    ProductActivity read(Long id);
    List<ProductActivity> find(String string);
}