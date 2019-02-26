/*
 * Developed by Razil Minneakhmetov on 12/26/18 8:13 PM.
 * Last modified 12/26/18 8:13 PM.
 * Copyright © 2018. All rights reserved.
 */

package repositories;

import models.Similarity;
import models.SimilarityUsers;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SimilarityRepository {
    private JdbcTemplate jdbcTemplate;

    public SimilarityRepository(DriverManagerDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final String SQL_INSERT = "INSERT INTO similarity_table (user_vk_id, friend_vk_id, similarity) values (?, ?, ?)";

    private static final String SQL_READ_BY_ID = "SELECT * FROM similarity_table where user_vk_id = ?";

    private static final String SQL_GET_SIMILARITY_USERS = "select similarity_table.id, friends_table.friend_vk_id, similarity, name, surname, photo_url from similarity_table join friends_table on similarity_table.friend_vk_id = friends_table.friend_vk_id WHERE similarity_table.user_vk_id = ? ORDER BY similarity DESC;";

    public void saveAll(final List<Similarity> similarities){
        jdbcTemplate.batchUpdate(SQL_INSERT,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, similarities.get(i).getUserVkId());
                        ps.setLong(2, similarities.get(i).getFriendVkId());
                        ps.setDouble(3, similarities.get(i).getSimilarity());
                    }
                    public int getBatchSize() {
                        return similarities.size();
                    }
                });
    }

    RowMapper<Similarity> rowMapper = new RowMapper<Similarity>() {
        public Similarity mapRow(ResultSet resultSet, int i) throws SQLException {
            return Similarity.builder()
                    .friendVkId(resultSet.getLong("friend_vk_id"))
                    .userVkId(resultSet.getLong("user_vk_id"))
                    .similarity(resultSet.getDouble("similarity"))
                    .id(resultSet.getLong("id"))
                    .build();
        }
    };

    RowMapper<SimilarityUsers> similarityUsersRowMapper = new RowMapper<SimilarityUsers>() {
        public SimilarityUsers mapRow(ResultSet resultSet, int i) throws SQLException {
            return SimilarityUsers.builder()
                    .id(resultSet.getLong("id"))
                    .vkId(resultSet.getLong("friend_vk_id"))
                    .similarity(resultSet.getDouble("similarity"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .photoUrl(resultSet.getString("photo_url"))
                    .build();
        }
    };

    public List<Similarity> readByUserId(Long vkId){
        return jdbcTemplate.query(SQL_READ_BY_ID, rowMapper, vkId);
    }

    public List<SimilarityUsers> getSimilarityUsers(Long vkId){
        return jdbcTemplate.query(SQL_GET_SIMILARITY_USERS, similarityUsersRowMapper, vkId);
    }


}