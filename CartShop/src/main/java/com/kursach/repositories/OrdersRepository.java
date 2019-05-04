package com.kursach.repositories;

import com.kursach.models.Order;

import java.util.List;

public interface OrdersRepository {
    List<Order> getOrdersBySeller(Integer id);
    List<Order> getOrdersByBuyer(Integer id);
    void deleteBySeller(Integer userId, Long order);
    void deleteByBuyer(Integer userId, Long order);
    void insert(Order order);
    void insert(List<Order> orders);

}
