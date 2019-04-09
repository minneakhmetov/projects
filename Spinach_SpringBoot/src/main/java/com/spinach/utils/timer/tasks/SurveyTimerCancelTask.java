package com.spinach.utils.timer.tasks;

import com.spinach.dto.ChatDto;
import com.spinach.utils.SimilaritySingleton;
import com.spinach.websocket.services.WebSocketService;

import java.util.Timer;
import java.util.TimerTask;

public class SurveyTimerCancelTask extends TimerTask {

    Timer timer;
    WebSocketService service;
    String userEmail;
    long userId;
    SimilaritySingleton similaritySingleton;

    public SurveyTimerCancelTask(Timer timer, WebSocketService service, String userEmail, long userId, SimilaritySingleton similaritySingleton) {
        this.timer = timer;
        this.service = service;
        this.userEmail = userEmail;
        this.userId = userId;
        this.similaritySingleton = similaritySingleton;
    }

    @Override
    public void run() {
        timer.cancel();
        similaritySingleton.removeFromTimerRandomUsersWithOutTimer(userId);
        service.addedUser(ChatDto.builder().build(), userEmail);
    }
}
