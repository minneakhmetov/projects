package com.spinach.dao;

import com.spinach.models.ChatModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class ChatsRepository {
    private JdbcTemplate jdbcTemplate;

    public ChatsRepository(DriverManagerDataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String INSERT_CHAT = "INSERT INTO chats_table (users) VALUES (?)";

   // private static final String SELECT_ALL_CHATS = "SELECT * FROM chats_table WHERE user_id = ?";

    //private static final String SELECT_CHAT = "SELECT * FROM chats_table where user_id = ? and id = ?";

    //private static final String SELECT_ALL_CHATS_PHOTO = "select "

    private RowMapper<ChatModel> rowMapper = new RowMapper<ChatModel>() {
        @Override
        public ChatModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return ChatModel.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .partnerId(resultSet.getLong("partner_id"))
                    .chatTime(resultSet.getTimestamp("chat_time").toLocalDateTime())
                    .build();
        }
    };

    public long save(String users){ //todo: переделать на массивы
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_CHAT, new String[] {"id"});
                    ps.setString(1, users);

                    return ps;
                }, keyHolder);
        return keyHolder.getKey().longValue();
        //jdbcTemplate.update(INSERT_CHAT, model.getId(), model.getUserId(), model.getPartnerId(), Timestamp.valueOf(model.getChatTime()));
    }

//    public List<ChatModel> readAll(long userId){
//        return jdbcTemplate.query(SELECT_ALL_CHATS, rowMapper, userId);
//    }
//
//    public ChatModel read(long userId, long chatId){
//        return jdbcTemplate.queryForObject(SELECT_CHAT, rowMapper, userId, chatId);
//    }
}
