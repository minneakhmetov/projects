/*
 * Developed by Razil Minneakhmetov on 10/24/18 9:49 PM.
 * Last modified 10/24/18 9:49 PM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


public class UserRepositoryImpl implements UserRepository, Repository {

    //language=SQL
    private static final String SQL_INSERT =
            "insert into user_table(username, vk_id, photo_url ) values (?, ?, ?);";

    //language=SQL
    private static final String SQL_SELECT =
            "select * from user_table where vk_id = ?";



    private JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DriverManagerDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (resultSet, i) -> User.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("username"))
            .photoURL(resultSet.getString("photo_url"))
            .vkId(resultSet.getLong("vk_id"))
            .build();

    public void create(User user){
        jdbcTemplate.update(SQL_INSERT, user.getName(), user.getVkId(), user.getPhotoURL());
    }


    public User readOne(Long id){
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT, userRowMapper, id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

}