package com.spinach.dao;

import com.spinach.dto.RelationUserModel;
import com.spinach.models.SurveyStateModel;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class RelationUserRepository {

    private JdbcTemplate jdbcTemplate;

    public RelationUserRepository(DriverManagerDataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String INSERT = "INSERT INTO relation_user_table (user_id, partner_id, is_friend) values (?, ?, ?)";

    public void save(RelationUserModel relationUserModel){
        jdbcTemplate.update(INSERT, relationUserModel.getUserId(), relationUserModel.getPartnerId(), relationUserModel.isFriend());
    }

    public void save(List<RelationUserModel> models){
        jdbcTemplate.batchUpdate(INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, models.get(i).getUserId());
                        ps.setLong(2, models.get(i).getPartnerId());
                        ps.setBoolean(3, models.get(i).isFriend());
                    }
                    public int getBatchSize() {
                        return models.size();
                    }
                });
    }

}
