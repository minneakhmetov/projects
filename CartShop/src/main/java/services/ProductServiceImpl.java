/*
 * Developed by Razil Minneakhmetov on 10/28/18 2:47 PM.
 * Last modified 10/28/18 2:47 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import dto.ProductDto;
import forms.LoginForm;
import models.Activity;
import models.Product;
import models.ProductActivity;
import repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private ActivityRepository activityRepository;
    private Random random;

    public ProductServiceImpl(ProductRepository productRepository, CartRepository cartRepository, ActivityRepository activityRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.activityRepository = activityRepository;
        random = new Random(System.currentTimeMillis());
    }

    public ProductServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public List<ProductActivity> getAll(){
        return productRepository.readAll();
    }

    public void addAll(List<LoginForm> forms){
        cartRepository.delete();
        productRepository.delete();


        List<ProductDto> productDtos = new ArrayList<>();
        List<Activity> activities = activityRepository.getAll();
        for (int i = 0; i < forms.size(); i++){
            ProductDto productDto = ProductDto.builder()
                    .name(forms.get(i).getName())
                    .photoURL(forms.get(i).getPhotoURL())
                    .vkId(forms.get(i).getVkId())
                    .price(random.nextInt(3)*500 + 1000)
                    .activity(getRandomId(activities))
                    .build();
            productDtos.add(productDto);
        }
        productRepository.batchUpdate(productDtos);
    }

    public ProductActivity read(Long id){
        return productRepository.readOne(id);
    }

    public List<ProductActivity> find(String string){
        return productRepository.find(string);
    }

    private long getRandomId(List<Activity> activities){
        while (true) {
            int number = random.nextInt(activities.size());
            for(int i = 0; i < activities.size(); i++){
                if (activities.get(i).getId() == number)
                    return number;
            }
        }
    }
}