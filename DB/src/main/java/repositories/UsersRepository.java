/*
 * Developed by Razil Minneakhmetov on 12/26/18 12:07 AM.
 * Last modified 12/26/18 12:07 AM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersRepository {
    private JdbcTemplate jdbcTemplate;

    public UsersRepository(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String SQL_INSERT = "INSERT INTO users_table (vk_id, name, surname, photo_url) VALUES (?, ?, ?, ?)";

    private static final String SQL_SELECT = "SELECT * FROM users_table WHERE vk_id = ?";

    public void save(User user){
        jdbcTemplate.update(SQL_INSERT, user.getVkId(), user.getName(), user.getSurname(), user.getPhotoUrl());
    }

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return User.builder()
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .vkId(resultSet.getLong("vk_id"))
                    .id(resultSet.getLong("id"))
                    .photoUrl(resultSet.getString("photo_url"))
                    .build();
        }
    };

    public User readUser(long vkId){
        return jdbcTemplate.queryForObject(SQL_SELECT, userRowMapper, vkId);
    }


}