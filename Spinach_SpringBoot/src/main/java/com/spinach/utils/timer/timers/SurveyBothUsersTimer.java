package com.spinach.utils.timer.timers;

import com.spinach.dao.AnonimRepository;
import com.spinach.dao.SurveyStateRepository;
import com.spinach.utils.PathUtil;
import com.spinach.utils.SimilaritySingleton;
import com.spinach.utils.timer.tasks.SurveyTimerTask;
import com.spinach.websocket.services.WebSocketService;

import java.util.Timer;

public class SurveyBothUsersTimer implements SurveyTimer {

    private Timer timer;

    private WebSocketService webSocketService;
    private AnonimRepository anonimRepository;
    private SimilaritySingleton similaritySingleton;
    private SurveyStateRepository surveyStateRepository;
    private PathUtil pathUtil;

    public SurveyBothUsersTimer(WebSocketService webSocketService, AnonimRepository anonimRepository, SurveyStateRepository surveyStateRepository, SimilaritySingleton similaritySingleton, PathUtil pathUtil) {
        this.webSocketService = webSocketService;
        this.anonimRepository = anonimRepository;
        this.similaritySingleton = similaritySingleton;
        this.surveyStateRepository = surveyStateRepository;
        this.pathUtil = pathUtil;
    }

    public SurveyBothUsersTimer startTimer(String userEmail, String partnerEmail, long userId, long partnerId){
        timer = new Timer();
        SurveyTimerTask task = new SurveyTimerTask(timer, webSocketService, userEmail, partnerEmail, userId, partnerId, anonimRepository, surveyStateRepository, similaritySingleton);
        timer.schedule(task, Integer.valueOf(pathUtil.getProperty("spinach.search.delay.seconds")) * 1000);
        return this;
    }

    @Override
    public void cancelTimer() {
        timer.cancel();
    }


}
