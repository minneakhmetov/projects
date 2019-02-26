/*
 * Developed by Razil Minneakhmetov on 12/27/18 12:42 AM.
 * Last modified 12/27/18 12:42 AM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import models.Auth;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthRepository {
    private JdbcTemplate jdbcTemplate;
    private static final String SQL_INSERT = "INSERT INTO auth_table (user_vk_id, auth) values (?,?)";
    private static final String SQL_READ_BY_USER_ID = "SELECT * FROM auth_table where user_vk_id = ?";
    private static final String SQL_READ_BY_USER_AUTH = "SELECT * FROM auth_table where auth = ?";

    public AuthRepository(DriverManagerDataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    RowMapper<Auth> rowMapper = new RowMapper<Auth>() {
        public Auth mapRow(ResultSet resultSet, int i) throws SQLException {
            return Auth.builder()
                    .auth(resultSet.getString("auth"))
                    .vkId(resultSet.getLong("user_vk_id"))
                    .build();
        }
    };

    public void save(Auth auth){
        jdbcTemplate.update(SQL_INSERT, auth.getVkId(), auth.getAuth());
    }
    public Auth read(Long vkId) throws EmptyResultDataAccessException{
        return jdbcTemplate.queryForObject(SQL_READ_BY_USER_ID, rowMapper, vkId);
    }
    public Auth read(String auth) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(SQL_READ_BY_USER_AUTH, rowMapper, auth);
    }

}