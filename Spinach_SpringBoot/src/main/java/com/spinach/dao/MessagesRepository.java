package com.spinach.dao;

import com.spinach.models.ChatPhotoModel;
import com.spinach.models.ChatsMessageModel;
import com.spinach.models.MessageListModel;
import com.spinach.models.MessageModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class MessagesRepository {
    JdbcTemplate jdbcTemplate;

    private MessagesRepository(DriverManagerDataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String INSERT_NEW_MESSAGE = "INSERT INTO message_table (chat_id, user_id, partner_id, text_message, message_time) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_MESSAGES = "SELECT * FROM message_table where user_id = ?";

    private static final String SELECT_ALL_MESSAGES_IN_CHAT_ID = "select user_id, chat_id, message_table.id, message_time, text_message, partner_id, photo_100, first_name, last_name from message_table join users_table on message_table.partner_id = users_table.id where chat_id = ? order by message_time;";

    private static final String SELECT_ALL_CHATS_BY_USER_ID = "WITH ids as (" +
            "       SELECT max(message_time) as last_message_time, chat_id from message_table group by chat_id " +
            "     )" +
            "select user_id, partner_id, ids.chat_id, last_message_time, text_message " +
            "        from message_table right join ids on ids.chat_id = message_table.chat_id AND message_time = ids.last_message_time " +
            "        join users_table ut on message_table.partner_id = ut.id where user_id = ? or partner_id = ? order by last_message_time desc;";

    private static final String SELECT_CHAT_ID = "select distinct chat_id from message_table where (user_id = ? and partner_id = ?) or (user_id = ? and partner_id = ?);";

    private static final String DELETE_CHAT = "delete from message_table where (user_id = ? and partner_id = ?) or (user_id = ? and partner_id = ?); ";

    public void save(MessageModel model){
        jdbcTemplate.update(INSERT_NEW_MESSAGE, model.getChatId(), model.getUserId(), model.getPartnerId(),model.getTextMessage(), Timestamp.valueOf(model.getMessageTime()));
    }

    public MessageModel read(long userId){
        return jdbcTemplate.queryForObject(SELECT_ALL_MESSAGES, rowMapper, userId);
    }

    public List<MessageListModel> readByChatId(long chatId){
        return jdbcTemplate.query(SELECT_ALL_MESSAGES_IN_CHAT_ID, rowMessagesListMapper, chatId);
    }

    private RowMapper<MessageModel> rowMapper = new RowMapper<MessageModel>() {
        @Override
        public MessageModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return MessageModel.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .partnerId(resultSet.getLong("partner_id"))
                    .textMessage(resultSet.getString("text_message"))
                    .chatId(resultSet.getLong("chat_id"))
                    .messageTime(resultSet.getTimestamp("message_time").toLocalDateTime())
                    .build();
        }
    };

    private RowMapper<MessageListModel> rowMessagesListMapper = new RowMapper<MessageListModel>() {
        @Override
        public MessageListModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return MessageListModel.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .partnerId(resultSet.getLong("partner_id"))
                    .message(resultSet.getString("text_message"))
                    .chatId(resultSet.getLong("chat_id"))
                    .time(resultSet.getTimestamp("message_time").toLocalDateTime())
                    .name(resultSet.getString("first_name"))
                    .surname(resultSet.getString("last_name"))
                    .photoUrl(resultSet.getString("photo_100"))
                    .build();
        }
    };

    private RowMapper<Long> longRowMapper = new RowMapper<Long>() {
        @Override
        public Long mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getLong("chat_id");
        }
    };

    public Long getChatId(long userId, long partnerId){
        return jdbcTemplate.queryForObject(SELECT_CHAT_ID, longRowMapper, userId, partnerId, partnerId, userId);
    }


    private RowMapper<ChatsMessageModel> rowPhotoModel = new RowMapper<ChatsMessageModel>() { //todo: поменять photo100
        @Override
        public ChatsMessageModel mapRow(ResultSet resultSet, int i) throws SQLException {
            return ChatsMessageModel.builder()
                    .chatId(resultSet.getLong("chat_id"))
                    .userId(resultSet.getLong("user_id"))
                    .partnerId(resultSet.getLong("partner_id"))
                    .lastMessageTime(resultSet.getTimestamp("last_message_time").toLocalDateTime())
                    .textMessage(resultSet.getString("text_message"))
                    //.photoUrl(resultSet.getString("photo_100"))
                    //.name(resultSet.getString("first_name"))
                    //.surname(resultSet.getString("last_name"))
                    .build();
        }
    };

    public List<ChatsMessageModel> getChats(long userId){
        return jdbcTemplate.query(SELECT_ALL_CHATS_BY_USER_ID, rowPhotoModel, userId, userId);
    }

    public void deleteChat(long userId, long partnerId){
        jdbcTemplate.update(DELETE_CHAT, userId, partnerId, partnerId, userId);
    }


}
