/*
 * Developed by Razil Minneakhmetov on 10/28/18 2:47 PM.
 * Last modified 10/28/18 2:47 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.services;

import com.kursach.forms.ServiceForm;

import com.kursach.models.Product;
import com.kursach.models.User;
import com.kursach.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createService(ServiceForm form, Integer vkId){
        Product product = Product.builder()
                .userId(vkId)
                .price(form.getPrice())
                .activity(form.getServiceName())
                .build();
        productRepository.create(product);
    }


    public List<Product> getAll(){
        return productRepository.readAll();
    }

    public Product read(Long id){
        return productRepository.readOne(id);
    }

    public List<Product> find(String string){
        return productRepository.find(string);
    }
}