package com.spinach.utils.timer.timers;

import com.spinach.dao.AnonimRepository;
import com.spinach.dao.SurveyStateRepository;
import com.spinach.utils.PathUtil;
import com.spinach.utils.SimilaritySingleton;
import com.spinach.utils.timer.tasks.SurveyTimerOneUserTask;
import com.spinach.websocket.services.WebSocketService;

import java.util.Timer;

public class SurveyOneUserTimer implements SurveyTimer {

    private Timer timer;

    private WebSocketService webSocketService;
    private AnonimRepository anonimRepository;
    private SimilaritySingleton similaritySingleton;
    private SurveyStateRepository surveyStateRepository;
    private PathUtil pathUtil;

    public SurveyOneUserTimer(WebSocketService webSocketService, AnonimRepository anonimRepository, SurveyStateRepository surveyStateRepository, SimilaritySingleton similaritySingleton, PathUtil pathUtil) {
        this.webSocketService = webSocketService;
        this.anonimRepository = anonimRepository;
        this.similaritySingleton = similaritySingleton;
        this.surveyStateRepository = surveyStateRepository;
        this.pathUtil = pathUtil;
    }

    public SurveyOneUserTimer startTimer(String userEmail, long userId) {
        timer = new Timer();
        SurveyTimerOneUserTask task = new SurveyTimerOneUserTask(timer, webSocketService, userEmail, userId, anonimRepository, surveyStateRepository, similaritySingleton);
        timer.schedule(task, Integer.valueOf(pathUtil.getProperty("spinach.search.delay.seconds")) * 1000);
        return this;
    }

    @Override
    public void cancelTimer() {
        timer.cancel();
    }
}
