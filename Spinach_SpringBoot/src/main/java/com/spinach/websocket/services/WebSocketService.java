package com.spinach.websocket.services;

import com.google.gson.JsonObject;
import com.spinach.dto.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class for sending notification messages.
 */
@Service
public class WebSocketService {

    // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Send notification to users subscribed on channel "/user/queue/notify".
     *
     * The message will be sent only to the user with the given username.
     *
     * @param notification The notification message.
     * @param username The username for the user to send notification.
     */
    public void notify(MessageTimeDto dto, String username) {
        messagingTemplate.convertAndSendToUser(username,"/queue/notify", dto);
        //return;
    }

    public void addedUser(ChatDto dto, String username) {
        messagingTemplate.convertAndSendToUser(username,"/queue/adduser", dto);
        //return;
    }

    public void survey(SurveyNotification notification, String username) {
        messagingTemplate.convertAndSendToUser(username,"/queue/survey", notification);
        //return;
    }

    public void survey(ChatsListDto dto, String username) {
        messagingTemplate.convertAndSendToUser(username,"/queue/survey", dto);
        //return;
    }

}
