package com.spinach.utils.timer.tasks;

import com.spinach.dao.AnonimRepository;
import com.spinach.dao.SurveyStateRepository;
import com.spinach.dto.SurveyEnum;
import com.spinach.dto.SurveyNotification;
import com.spinach.models.SurveyStateModel;
import com.spinach.utils.SimilaritySingleton;
import com.spinach.websocket.services.WebSocketService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SurveyTimerTask extends TimerTask {

    public SurveyTimerTask(Timer timer, WebSocketService service, String userEmail, String partnerEmail, long userId, long parnterId, AnonimRepository repository, SurveyStateRepository surveyStateRepository, SimilaritySingleton similaritySingleton) {
        this.timer = timer;
        this.service = service;
        this.userEmail = userEmail;
        this.partnerEmail = partnerEmail;
        this.userId = userId;
        this.parnterId = parnterId;
        this.repository = repository;
        this.surveyStateRepository = surveyStateRepository;
        this.similaritySingleton = similaritySingleton;
    }

    Timer timer;
    WebSocketService service;
    String userEmail;
    String partnerEmail;
    long userId;
    long parnterId;
    AnonimRepository repository;
    SurveyStateRepository surveyStateRepository;
    SimilaritySingleton similaritySingleton;


    @Override
    public void run() {

        if(repository.read(userId, parnterId) != null) {
            service.survey(SurveyNotification.ALERT, userEmail);
            service.survey(SurveyNotification.ALERT, partnerEmail);
            List<SurveyStateModel> models = new ArrayList<>();
            models.add(new SurveyStateModel(userId, SurveyEnum.MAIN));
            models.add(new SurveyStateModel(parnterId, SurveyEnum.MAIN));
            surveyStateRepository.save(models);
        }


        //todo: добавить
        timer.cancel();
        similaritySingleton.removeFromTimer(userId);
        similaritySingleton.removeFromTimer(parnterId);
    }
}
