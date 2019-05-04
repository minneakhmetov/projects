/*
 * Developed by Razil Minneakhmetov on 10/25/18 7:33 PM.
 * Last modified 10/25/18 7:33 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.repositories;

import com.kursach.models.ProductCart;
import com.kursach.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartRepositoryImpl implements CartRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CartRepositoryImpl(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<ProductCart> rowMapperCart = (resultSet, i) -> {
        User user = User.builder()
                .name(resultSet.getString("name"))
                .photoURL(resultSet.getString("photo_url"))
                .surname(resultSet.getString("surname"))
                .vkId(resultSet.getInt("user_id"))
                .build();
        return
            ProductCart.builder()
                    .id(resultSet.getLong("id"))
                    .price(resultSet.getString("price"))
                    .productId(resultSet.getLong("product_id"))
                    .user(user)
                    .activity(resultSet.getString("activity"))
                    .build();
    };

    private static final String SQL_INSERT =
            "INSERT INTO cart (user_id, product_id) VALUES (?, ?)";

    private static final String SQL_SELECT =
            "SELECT cart.id as id, price, activity, service.user_id as user_id, name, surname, photo_url, product_id FROM cart join (select id, price, activity, user_id, name, photo_url, vk_id, surname from product join user_table ut on product.user_id = ut.vk_id) as service on cart.product_id = service.id where cart.user_id = ?";

    private static final String SQL_DELETE =
            "DELETE FROM cart";

    private static final String SQL_DELETE_FROM_CART =
            "DELETE FROM cart where id = ? and user_id = ?";

    private static final String SQL_DELETE_FROM_CART_ALL =
            "DELETE FROM cart where user_id = ?";

    public void create(Integer userId, Long productId) {
        jdbcTemplate.update(SQL_INSERT, userId, productId);
    }

    public List<ProductCart> readProductsByUser(Integer userId) {
        return jdbcTemplate.query(SQL_SELECT, rowMapperCart, userId);
    }

    public void delete() {
        jdbcTemplate.update(SQL_DELETE);
    }

    public void deleteFromCart(Long cartId, Integer userId) {
        jdbcTemplate.update(SQL_DELETE_FROM_CART, cartId, userId);
    }

    public void deleteAllCart(Integer userId) {
        jdbcTemplate.update(SQL_DELETE_FROM_CART_ALL, userId);
    }

}