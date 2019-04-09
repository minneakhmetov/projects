package com.spinach.dao;

import com.spinach.models.GroupsModel;
import com.spinach.models.GroupsShortModel;
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
public class GroupsRepository {
    private JdbcTemplate jdbcTemplate;

    public GroupsRepository(DriverManagerDataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final String SQL_INSERT = "insert into users_groups_table (user_id, user_vk_id, group_vk_id, " +
            "name, is_closed, deactivated, type, photo_50, photo_100, photo_200, activity, city, country, description, " +
            "status, site, is_favourite) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_READ_ALL = "select * from users_groups_table where user_id = ?";
    private static final String SQL_READ_SHORT = "select id, name, activity, photo_50 from users_groups_table where user_id = ?";
    public void saveAll(List<GroupsModel> groupsModels){
        jdbcTemplate.batchUpdate(SQL_INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, groupsModels.get(i).getUserId());
                        ps.setLong(2, groupsModels.get(i).getUserVkId());
                        ps.setLong(3, groupsModels.get(i).getGroupVkId());
                        ps.setString(4, groupsModels.get(i).getName());
                        if(groupsModels.get(i).getIsClosed() == null)
                            ps.setNull(5, Types.SMALLINT);
                        else ps.setShort(5, groupsModels.get(i).getIsClosed());
                        ps.setString(6, groupsModels.get(i).getDeactivated());
                        ps.setString(7, groupsModels.get(i).getType());
                        ps.setString(8, groupsModels.get(i).getPhotoUrl50());
                        ps.setString(9, groupsModels.get(i).getPhotoUrl100());
                        ps.setString(10, groupsModels.get(i).getPhotoUrl200());
                        ps.setString(11, groupsModels.get(i).getActivity());
                        ps.setString(12, groupsModels.get(i).getCity());
                        ps.setString(13, groupsModels.get(i).getCountry());
                        ps.setString(14, groupsModels.get(i).getDescription());
                        ps.setString(15, groupsModels.get(i).getStatus());
                        ps.setString(16, groupsModels.get(i).getSite());
                        if(groupsModels.get(i).getIsFavourite() == null)
                            ps.setNull(17, Types.BOOLEAN);
                        else ps.setBoolean(17, groupsModels.get(i).getIsFavourite());
                    }
                    public int getBatchSize() {
                        return groupsModels.size();
                    }
                });
    }

    private RowMapper<GroupsModel> rowMapper = new RowMapper<GroupsModel>() {
        @Override
        public GroupsModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return GroupsModel.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .userVkId(resultSet.getLong("user_vk_id"))
                    .groupVkId(resultSet.getLong("group_vk_id"))
                    .name(resultSet.getString("is_closed"))
                    .deactivated(resultSet.getString("deactivated"))
                    .type(resultSet.getString("type"))
                    .photoUrl50(resultSet.getString("photo_50"))
                    .photoUrl100(resultSet.getString("photo_100"))
                    .photoUrl200(resultSet.getString("photo_200"))
                    .activity(resultSet.getString("activity"))
                    .city(resultSet.getString("city"))
                    .country(resultSet.getString("country"))
                    .description(resultSet.getString("description"))
                    .status(resultSet.getString("status"))
                    .site(resultSet.getString("site"))
                    .isFavourite(resultSet.getBoolean("is_favourite"))
                    .build();
        }
    };

    private RowMapper<GroupsShortModel> rowShortMapper = new RowMapper<GroupsShortModel>() {
        @Override
        public GroupsShortModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return GroupsShortModel.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .activity(resultSet.getString("activity"))
                    .photoUrl50(resultSet.getString("photo_50"))
                    .build();
        }
    };

    public List<GroupsModel> readAllByUserId(long id){
        return jdbcTemplate.query(SQL_READ_ALL, rowMapper, id);
    }

    public List<GroupsShortModel> readShortByUserId(long id){
        return jdbcTemplate.query(SQL_READ_SHORT, rowShortMapper, id);
    }
}
