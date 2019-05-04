package com.kursach.services;

import com.kursach.models.Order;
import com.kursach.models.ProductCart;

import java.util.List;

public interface OrdersService {
    void buyServices(List<ProductCart> productCarts, Integer userId);
    void deleteBySeller(Integer userId, Long productId);
    void deleteByBuyer(Integer userId, Long productId);
    List<Order> showBySeller(Integer userId);
    List<Order> showByBuyer(Integer userId);

}
