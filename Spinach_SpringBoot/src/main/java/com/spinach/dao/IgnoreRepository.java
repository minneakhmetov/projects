package com.spinach.dao;

import com.spinach.models.GroupsModel;
import com.spinach.models.IgnoreModel;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Component
public class IgnoreRepository {

    private JdbcTemplate jdbcTemplate;

    public IgnoreRepository(DriverManagerDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String INSERT = "INSERT INTO ignore_list_id (user_id, partner_id) VALUES (?, ?)";

    private static final String SELECT = "SELECT * FROM ignore_list_id where user_id = ? and partner_id = ?";
    private static final String SELECT_BY_ONE_USER = "SELECT * FROM ignore_list_id where user_id = ?";

    public void addOneUser(long userId, long partnerId){
        jdbcTemplate.update(INSERT, userId, partnerId);
    }

    public void saveAll(List<IgnoreModel> models){
        jdbcTemplate.batchUpdate(INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, models.get(i).getUserId());
                        ps.setLong(2, models.get(i).getPartnerId());
                    }
                    public int getBatchSize() {
                        return models.size();
                    }
                });
    }

    RowMapper<IgnoreModel> rowMapper = new RowMapper<IgnoreModel>() {
        @Override
        public IgnoreModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return IgnoreModel.builder()
                    .userId(resultSet.getLong("user_id"))
                    .partnerId(resultSet.getLong("partner_id"))
                    .build();
        }
    };

    public IgnoreModel read(Long userId, Long partnerId){
        try {
            return jdbcTemplate.queryForObject(SELECT, rowMapper, userId, partnerId);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public List<IgnoreModel> read(long userId){
        try {
            return jdbcTemplate.query(SELECT_BY_ONE_USER, rowMapper, userId);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
