/*
 * Developed by Razil Minneakhmetov on 10/24/18 10:26 PM.
 * Last modified 10/24/18 10:26 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.repositories;


import com.kursach.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ProductRepositoryImpl implements ProductRepository {
    
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepositoryImpl(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //language=SQL
    private static final String SQL_INSERT =
            "INSERT INTO product (price, activity, user_id) values (?, ?, ?)";

    //language=SQL
    private static final String SQL_SELECT =
            "SELECT * from product join user_table on product.user_id = user_table.vk_id where id = ?";

    //language=SQL
    private static final String SQL_SELECT_ALL =
            "SELECT * from product join user_table on product.user_id = user_table.vk_id";

    //language=SQL
    private static final String SQL_DELETE =
            "DELETE from product";

    private static final String SQL_FIND =
            "select * from product join user_table on product.user_id = user_table.vk_id where (position(upper(?) in (upper(name), upper(activity)) )) > 0";


    private RowMapper<Product> rowMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            return Product.builder()
                    .avatar(resultSet.getString("photo_url"))
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name") + " " + resultSet.getString("surname"))
                    .price(resultSet.getString("price"))
                    .activity(resultSet.getString("activity"))
                    .build();
        }
    };


    public void create(Product product){
        jdbcTemplate.update(SQL_INSERT, product.getPrice(), product.getActivity(), product.getUserId());
    }

    public void delete(){
        jdbcTemplate.update(SQL_DELETE);
    }

    public Product readOne(Long id){
        return jdbcTemplate.queryForObject(SQL_SELECT, rowMapper, id);
    }

    public List<Product> readAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    public List<Product> find(String string){
        return jdbcTemplate.query(SQL_FIND, rowMapper, string);
    }







}