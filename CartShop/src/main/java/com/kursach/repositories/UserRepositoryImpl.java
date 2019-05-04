/*
 * Developed by Razil Minneakhmetov on 10/24/18 9:49 PM.
 * Last modified 10/24/18 9:49 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.repositories;

import com.kursach.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {

    //language=SQL
    private static final String SQL_INSERT =
            "insert into user_table(name, surname, vk_id, photo_url ) values (?, ?, ?, ?);";

    //language=SQL
    private static final String SQL_SELECT =
            "select * from user_table where vk_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(DriverManagerDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (resultSet, i) -> User.builder()
            .name(resultSet.getString("name"))
            .surname(resultSet.getString("surname"))
            .photoURL(resultSet.getString("photo_url"))
            .vkId(resultSet.getInt("vk_id"))
            .build();

    public void create(User user){
        jdbcTemplate.update(SQL_INSERT, user.getName(), user.getSurname(), user.getVkId(), user.getPhotoURL());
    }


    public User readOne(Integer id){
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT, userRowMapper, id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

}