/*
 * Developed by Razil Minneakhmetov on 12/26/18 1:01 AM.
 * Last modified 12/26/18 12:50 AM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import models.GroupModel;
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

public class UserGroupsFriendsRepository {

    private JdbcTemplate jdbcTemplate;

    public UserGroupsFriendsRepository(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String SQL_INSERT = "INSERT INTO groups_table (user_vk_id, friend_vk_id, group_id, name, photo_url, activity) VALUES (?, ?,?,?,?,?)";

    private static final String SQL_READ_BY_ID = "SELECT * FROM groups_table WHERE user_vk_id = ?";

    public void saveAll(final List<GroupModelFriends> groupModels) {
        jdbcTemplate.batchUpdate(SQL_INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, groupModels.get(i).getUserVkId());
                        ps.setLong(2, groupModels.get(i).getFriendVkId());
                        ps.setLong(3, groupModels.get(i).getGroupVkId());
                        ps.setString(4, groupModels.get(i).getName());
                        ps.setString(5, groupModels.get(i).getPhotoUrl());
                        ps.setString(6, groupModels.get(i).getActivity());
                    }

                    public int getBatchSize() {
                        return groupModels.size();
                    }
                });
    }

    private RowMapper<GroupModelFriends> rowMapper = new RowMapper<GroupModelFriends>() {
        public GroupModelFriends mapRow(ResultSet resultSet, int i) throws SQLException {
            return GroupModelFriends.builder()
                    .name(resultSet.getString("name"))
                    .groupVkId(resultSet.getLong("group_id"))
                    .userVkId(resultSet.getLong("user_vk_id"))
                    //.id(resultSet.getLong("id"))
                    .friendVkId(resultSet.getLong("friend_vk_id"))
                    .photoUrl(resultSet.getString("photo_url"))
                    .activity(resultSet.getString("activity"))
                    .build();
        }
    };

    public List<GroupModelFriends> readByUserVkId(Long vkId){
        return jdbcTemplate.query(SQL_READ_BY_ID, rowMapper, vkId);
    }


}