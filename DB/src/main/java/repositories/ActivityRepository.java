/*
 * Developed by Razil Minneakhmetov on 12/25/18 11:28 PM.
 * Last modified 12/25/18 11:28 PM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import lombok.SneakyThrows;
import org.postgresql.util.PSQLException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.ejb.DuplicateKeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class ActivityRepository {
    private JdbcTemplate jdbcTemplate;
    Connection connection;

    @SneakyThrows
    public ActivityRepository(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        connection = dataSource.getConnection();
    }

    private static final String SQL_INSERT = "INSERT INTO activity_table (value) values (?)";

    private static final String SQL = "ALTER TABLE groups_table ADD CONSTRAINT distfk FOREIGN KEY (activity) REFERENCES activity_table (value);";
    private static final String SQL1 = "ALTER TABLE users_groups_table ADD CONSTRAINT distfk1 FOREIGN KEY (activity) REFERENCES activity_table (value);";

    public void loadData(final List<String> data){
        jdbcTemplate.batchUpdate(SQL_INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, data.get(i));
                    }

                    public int getBatchSize() {
                        return data.size();
                    }
                } );

    }

    public void alters(){

        while (true) {
            try {
                Statement statement = connection.createStatement();
                statement.execute(SQL1);
                break;
            } catch (SQLException e) {
                String string = e.getMessage().split("activity")[1].split("отсутствует")[0];
                String string1 = string.substring(3, string.length() - 2);
                System.out.println(string1);
                jdbcTemplate.update(SQL_INSERT, string1);
            }
        }
    }
}