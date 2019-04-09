package com.spinach.utils.timer.tasks;



import com.spinach.dao.AnonimRepository;
import com.spinach.dao.SurveyStateRepository;
import com.spinach.dto.SurveyEnum;
import com.spinach.dto.SurveyNotification;
import com.spinach.models.SurveyStateModel;
import com.spinach.utils.SimilaritySingleton;
import com.spinach.websocket.services.WebSocketService;

import java.util.Timer;
import java.util.TimerTask;

public class SurveyTimerOneUserTask extends TimerTask {

    Timer timer;
    WebSocketService service;
    String userEmail;
    long userId;
    SurveyStateRepository surveyStateRepository;
    AnonimRepository repository;
    SimilaritySingleton similaritySingleton;

    public SurveyTimerOneUserTask(Timer timer, WebSocketService service, String userEmail, long userId, AnonimRepository repository, SurveyStateRepository surveyStateRepository, SimilaritySingleton similaritySingleton) {
        this.timer = timer;
        this.service = service;
        this.userEmail = userEmail;
        this.userId = userId;
        this.repository = repository;
        this.surveyStateRepository = surveyStateRepository;
        this.similaritySingleton = similaritySingleton;
    }

    @Override
    public void run() {
        if(repository.read(userId) != null) {
            service.survey(SurveyNotification.ALERT, userEmail);
            surveyStateRepository.save(new SurveyStateModel(userId, SurveyEnum.MAIN));
        }
        //todo: добавить
        timer.cancel();
        similaritySingleton.removeFromTimer(userId);
    }
}
