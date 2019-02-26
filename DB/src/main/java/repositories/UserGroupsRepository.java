/*
 * Developed by Razil Minneakhmetov on 12/26/18 12:39 AM.
 * Last modified 12/26/18 12:39 AM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import models.GroupModel;
import models.GroupModelFriends;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserGroupsRepository {

    private JdbcTemplate jdbcTemplate;

    public UserGroupsRepository(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String SQL_READ_BY_USER_ID = "SELECT * from users_groups_table where user_vk_id = ?";

    private static final String SQL_INSERT = "INSERT INTO users_groups_table (user_vk_id, group_id, name, photo_url, activity) VALUES (?,?,?,?,?)";

    public void saveAll(final List<GroupModel> groupModels) {
        jdbcTemplate.batchUpdate(SQL_INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, groupModels.get(i).getUserVkId());
                        ps.setLong(2, groupModels.get(i).getGroupVkId());
                        ps.setString(3, groupModels.get(i).getName());
                        ps.setString(4, groupModels.get(i).getPhotoUrl());
                        ps.setString(5, groupModels.get(i).getActivity());
                    }

                    public int getBatchSize() {
                        return groupModels.size();
                    }
                });
    }

    private RowMapper<GroupModel> rowMapper = new RowMapper<GroupModel>() {
        public GroupModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return GroupModel.builder()
                    .name(resultSet.getString("name"))
                    .groupVkId(resultSet.getLong("group_id"))
                    .userVkId(resultSet.getLong("user_vk_id"))
                    //.id(resultSet.getLong("id"))
                    .photoUrl(resultSet.getString("photo_url"))
                    .activity(resultSet.getString("activity"))
                    .build();
        }
    };

    public List<GroupModel> readByUserId(Long vkId){
        return jdbcTemplate.query(SQL_READ_BY_USER_ID, rowMapper, vkId);
    }


}