package com.spinach.dao;

import com.spinach.exceptions.UserNotFoundException;
import com.spinach.models.CompatibilityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class SimilaritiesRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SimilaritiesRepository(DriverManagerDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String SELECT = "select  compliance_ratio, first_user_id, second_user_id from compatibility_factor_table " +
          //  "join users_table ut on compatibility_factor_table.first_user_id = ut.id " +
           // "join users_table u on compatibility_factor_table.second_user_id = u.id " +
            "where (first_user_id = ? or second_user_id = ?) order by compliance_ratio desc";

    RowMapper<CompatibilityModel> rowMapper = new RowMapper<CompatibilityModel>() {
        @Override
        public CompatibilityModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return CompatibilityModel.builder()
                    .complianceRatio(resultSet.getDouble("compliance_ratio"))
                    .partnerId(resultSet.getLong("second_user_id"))
                    .userId(resultSet.getLong("first_user_id"))
                    .build();
        }
    };

    public List<CompatibilityModel> getSimilarities(long userId) {
        try {
            return jdbcTemplate.query(SELECT, rowMapper, userId, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }



}
