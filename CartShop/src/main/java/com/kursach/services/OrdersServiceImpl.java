package com.kursach.services;

import com.kursach.models.Order;
import com.kursach.models.ProductCart;
import com.kursach.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kursach.models.Order.from;
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    public void buyServices(List<ProductCart> productCarts, Integer userId){
        ordersRepository.insert(from(productCarts, userId));
    }

    public void deleteBySeller(Integer userId, Long productId){
        ordersRepository.deleteBySeller(userId, productId);
    }

    public void deleteByBuyer(Integer userId, Long productId){
        ordersRepository.deleteByBuyer(userId, productId);
    }

    public List<Order> showBySeller(Integer userId){
        return ordersRepository.getOrdersBySeller(userId);
    }

    public List<Order> showByBuyer(Integer userId){
        return ordersRepository.getOrdersByBuyer(userId);
    }
}
