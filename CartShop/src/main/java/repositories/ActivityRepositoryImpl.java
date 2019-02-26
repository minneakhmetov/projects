package repositories;

import models.Activity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ActivityRepositoryImpl implements ActivityRepository{
    private JdbcTemplate jdbcTemplate;

    public ActivityRepositoryImpl(DriverManagerDataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String SQL_SELECT = "SELECT * FROM activities_table";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM activities_table where id = ?";

    private RowMapper<Activity> rowMapper = new RowMapper<Activity>() {
        @Override
        public Activity mapRow(ResultSet resultSet, int i) throws SQLException {
            return Activity.builder()
                    .id(resultSet.getLong("id"))
                    .activity(resultSet.getString("activity_name"))
                    .build();
        }
    };
    public List<Activity> getAll(){
        return jdbcTemplate.query(SQL_SELECT, rowMapper);
    }
    public Activity getById(Long id){
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id);
    }

}
