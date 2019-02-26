/*
 * Developed by Razil Minneakhmetov on 10/24/18 10:31 PM.
 * Last modified 10/24/18 10:31 PM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import models.Avatar;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

@Deprecated
public class AvatarRepository implements Repository {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_INSERT =
            "INSERT INTO avatar (url) VALUES (?)";

    //language=SQL
    private static final String SQL_SELECT =
            "SELECT * FROM avatar WHERE id = ?";

    public AvatarRepository(DriverManagerDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Avatar> avatarRowMapper = (resultSet, i) -> Avatar.builder()
            .id(resultSet.getLong("id"))
            .URL(resultSet.getString("url"))
            .build();

    public Long create(String URL){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT, new String[] {"id"});
                    ps.setString(1, URL);
                    return ps;
                }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Avatar readOne(Long id){
        return jdbcTemplate.queryForObject(SQL_SELECT, avatarRowMapper, id);
    }
}