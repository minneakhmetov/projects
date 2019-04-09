package com.spinach.dao;

import com.spinach.models.SimilarityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
public class AnonimRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AnonimRepository(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String INSERT = "insert into anonymous_chats_table (user_id, partner_id) values (?, ?)";

    private static final String SELECT = "select * from anonymous_chats_table where user_id = ?";

    private static final String SELECT_BOTH_USERS = "select * from anonymous_chats_table where user_id = ? and partner_id = ?";

    private static final String DELETE = "delete from anonymous_chats_table where user_id = ? and partner_id = ?";




    public void save(List<SimilarityModel> similarityModels) throws DuplicateKeyException {
            jdbcTemplate.batchUpdate(INSERT,
                    new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, similarityModels.get(i).getUserId());
                            ps.setLong(2, similarityModels.get(i).getPartnerId());
                        }
                        public int getBatchSize() {
                            return similarityModels.size();
                        }
                    });
    }

    RowMapper<SimilarityModel> rowMapper = new RowMapper<SimilarityModel>() {
        @Override
        public SimilarityModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return SimilarityModel.builder()
                    .partnerId(resultSet.getLong("partner_id"))
                    .userId(resultSet.getLong("user_id"))
                    .build();
        }
    };

    public SimilarityModel read(long userId, long partnerId){
        try {
            return jdbcTemplate.queryForObject(SELECT_BOTH_USERS, rowMapper, userId, partnerId);

        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public SimilarityModel read(long userId){
        try {
            return jdbcTemplate.queryForObject(SELECT, rowMapper, userId);

        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public void delete(List<SimilarityModel> similarityModels){
        jdbcTemplate.batchUpdate(DELETE,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, similarityModels.get(i).getUserId());
                        ps.setLong(2, similarityModels.get(i).getPartnerId());
                    }
                    public int getBatchSize() {
                        return similarityModels.size();
                    }
                });
    }



}
