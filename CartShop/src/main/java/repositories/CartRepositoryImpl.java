/*
 * Developed by Razil Minneakhmetov on 10/25/18 7:33 PM.
 * Last modified 10/25/18 7:33 PM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import models.Cart;
import models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.List;

public class CartRepositoryImpl implements CartRepository, Repository {

    private JdbcTemplate jdbcTemplate;

    public CartRepositoryImpl(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Product> rowMapper = (resultSet, i) -> Product.builder()
            .avatar(resultSet.getString("avatar"))
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name"))
            .price(resultSet.getString("price"))
            .build();

    private static final String SQL_INSERT =
            "INSERT INTO cart (user_id, product_id) VALUES (?, ?)";

    private static final String SQL_SELECT =
            "SELECT * FROM cart join product on cart.product_id = product.id where user_id = ?";

    private static final String SQL_DELETE =
            "DELETE FROM cart";

    private static final String SQL_DELETE_FROM_CART =
            "DELETE FROM cart where product_id = ? and user_id = ?";

    private static final String SQL_DELETE_FROM_CART_ALL =
            "DELETE FROM cart where user_id = ?";

    public void create(Cart cart){
        jdbcTemplate.update(SQL_INSERT, cart.getUserId(), cart.getProductId());
    }

    public List<Product> readProductsByUser(Long userId){
        return jdbcTemplate.query(SQL_SELECT, rowMapper, userId);
    }

    public void delete(){
        jdbcTemplate.update(SQL_DELETE);
    }

    public void deleteFromCart(Long productId, Long userId){
        jdbcTemplate.update(SQL_DELETE_FROM_CART, productId, userId);
    }
    public void deleteAllCart(Long userId){
        jdbcTemplate.update(SQL_DELETE_FROM_CART_ALL, userId);
    }

}