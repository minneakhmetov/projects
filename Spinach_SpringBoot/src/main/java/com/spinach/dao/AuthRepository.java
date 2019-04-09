package com.spinach.dao;

import com.spinach.exceptions.UserNotFoundException;
import com.spinach.models.AuthModel;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Deprecated
@Component
public class AuthRepository {
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_ID = "SELECT * FROM auth_table where user_id = ?";

    private static final String INSERT = "INSERT INTO auth_table (user_id, cookie_value) values (?,?);";

    public AuthRepository(DriverManagerDataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(AuthModel auth){
        jdbcTemplate.update(INSERT, auth.getId(), auth.getCookieValue());
    }

    private RowMapper<AuthModel> rowMapper = new RowMapper<AuthModel>() {
        @Override
        public AuthModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return AuthModel.builder()
                    .cookieValue(resultSet.getString("cookie_value"))
                    .id(resultSet.getLong("user_id"))
                    .build();
        }
    };

    public AuthModel readById(long id) throws UserNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, rowMapper, id);
        } catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException();
        }
    }
}
