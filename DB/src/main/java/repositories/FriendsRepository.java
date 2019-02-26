/*
 * Developed by Razil Minneakhmetov on 12/26/18 6:30 PM.
 * Last modified 12/26/18 6:29 PM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import dto.VKUser;
import models.GroupModelFriends;
import models.User;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FriendsRepository {
    private JdbcTemplate jdbcTemplate;

    public FriendsRepository(DriverManagerDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String SQL_INSERT = "INSERT INTO friends_table (user_vk_id, friend_vk_id, name, surname, photo_url) VALUES (?,?,?,?,?)";

    private static final String SQL_READ_BY_ID = "SELECT * FROM friends_table WHERE user_vk_id = ?";

    public void saveAll(final List<User> users, final long vkId) {
        jdbcTemplate.batchUpdate(SQL_INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, vkId);
                        ps.setLong(2, users.get(i).getVkId());
                        ps.setString(3, users.get(i).getName());
                        ps.setString(4, users.get(i).getSurname());
                        ps.setString(5, users.get(i).getPhotoUrl());

                    }
                    public int getBatchSize() {
                        return users.size();
                    }
                });
    }

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return User.builder()
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .vkId(resultSet.getLong("friend_vk_id"))
                   // .id(resultSet.getLong("id"))
                    .photoUrl(resultSet.getString("photo_url"))
                    .build();
        }
    };

    public List<User> readFriendsByUserVkId(Long vkId){
        return jdbcTemplate.query(SQL_READ_BY_ID, userRowMapper, vkId);
    }
}