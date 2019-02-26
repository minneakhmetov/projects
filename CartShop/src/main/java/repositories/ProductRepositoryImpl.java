/*
 * Developed by Razil Minneakhmetov on 10/24/18 10:26 PM.
 * Last modified 10/24/18 10:26 PM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import dto.ProductDto;
import forms.LoginForm;
import lombok.SneakyThrows;
import models.Product;
import models.ProductActivity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class ProductRepositoryImpl implements ProductRepository, Repository {
    private JdbcTemplate jdbcTemplate;

    public ProductRepositoryImpl(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //language=SQL
    private static final String SQL_INSERT =
            "INSERT INTO product (name, price, avatar, activity) values (?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_SELECT =
            "SELECT * from product join activities_table on product.activity = activities_table.id where id = ?";

    //language=SQL
    private static final String SQL_SELECT_ALL =
            "SELECT * from product join activities_table on product.activity = activities_table.id";

    //language=SQL
    private static final String SQL_DELETE =
            "DELETE from product";

    private static final String SQL_FIND =
            "select * from product join activities_table on product.activity = activities_table.id where (position(upper(?) in upper(product.name))) > 0";


    private RowMapper<ProductActivity> rowMapper = (resultSet, i) -> ProductActivity.builder()
            .avatar(resultSet.getString("avatar"))
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name"))
            .price(resultSet.getString("price"))
            .activity(resultSet.getString("activity_name"))
            .build();


    public void create(Product product){
        jdbcTemplate.update(SQL_INSERT, product.getName(), product.getPrice(), product.getAvatar(), product.getActivity());
    }

    public void delete(){
        jdbcTemplate.update(SQL_DELETE);
    }

    public ProductActivity readOne(Long id){
        return jdbcTemplate.queryForObject(SQL_SELECT, rowMapper, id);
    }

    public List<ProductActivity> readAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @SneakyThrows
    public void batchUpdate(final List<ProductDto> users) {

        jdbcTemplate.batchUpdate(SQL_INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, users.get(i).getName());
                        ps.setInt(2, users.get(i).getPrice());
                        ps.setString(3, users.get(i).getPhotoURL());
                        ps.setLong(4, users.get(i).getActivity());
                    }

                    public int getBatchSize() {
                        return users.size();
                    }
                } );

    }

    public List<ProductActivity> find(String string){
        return jdbcTemplate.query(SQL_FIND, rowMapper, string);
    }







}