package com.spinach.dao;

import com.spinach.models.SurveyStateModel;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class SurveyStateRepository {

    private JdbcTemplate jdbcTemplate;

    SurveyStateRepository(DriverManagerDataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String INSERT = "INSERT INTO survey_state_table (user_id, state) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM survey_state_table where user_id = ?";
    private static final String DELETE_IN_TWO_USERS = "DELETE FROM survey_state_table where user_id in(?, ?)";
    private static final String UPDATE = "UPDATE survey_state_table SET state = ? where user_id = ?";
    private static final String SELECT = "SELECT * FROM survey_state_table WHERE user_id = ?";

    private RowMapper<SurveyStateModel> rowMapper = new RowMapper<SurveyStateModel>() {
        @Override
        public SurveyStateModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return new SurveyStateModel(resultSet.getLong("user_id"), resultSet.getString("state"));
        }
    };

    public SurveyStateModel read(long userId){
        try {
            return jdbcTemplate.queryForObject(SELECT, rowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(SurveyStateModel model){
        jdbcTemplate.update(UPDATE, model.getSurveyEnum().toString(), model.getUserId());
    }

    public void remove(long userId){
        jdbcTemplate.update(DELETE, userId);
    }

    public void remove(long userId, long partnerId){
        jdbcTemplate.update(DELETE_IN_TWO_USERS, userId, partnerId);
    }

    public void save(SurveyStateModel model){
        jdbcTemplate.update(INSERT, model.getUserId(), model.getSurveyEnum().toString());
    }

    public void save(List<SurveyStateModel> models){
        jdbcTemplate.batchUpdate(INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, models.get(i).getUserId());
                        ps.setString(2, models.get(i).getSurveyEnum().toString());
                    }
                    public int getBatchSize() {
                        return models.size();
                    }
                });
    }


}
