package com.spinach.utils.timer.timers;

import com.spinach.dao.UsersRepository;
import com.spinach.dto.TimerForUser;
import com.spinach.utils.PathUtil;
import com.spinach.utils.SimilaritySingleton;
import com.spinach.utils.timer.tasks.SurveyTimerCancelTask;
import com.spinach.websocket.services.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.Timer;

public class SurveyCancelTimer implements SurveyTimer {

    private WebSocketService webSocketService;
    private UsersRepository usersRepository;
    private SimilaritySingleton similaritySingleton;
    private PathUtil pathUtil;

    public SurveyCancelTimer(WebSocketService webSocketService, UsersRepository usersRepository, SimilaritySingleton similaritySingleton, PathUtil pathUtil) {
        this.webSocketService = webSocketService;
        this.usersRepository = usersRepository;
        this.similaritySingleton = similaritySingleton;
        this.pathUtil = pathUtil;
    }

    private Timer timer;


    public SurveyCancelTimer startTimer(long userId) {
        timer = new Timer();
        SurveyTimerCancelTask task = new SurveyTimerCancelTask(timer, webSocketService, usersRepository.getEmailById(userId), userId, similaritySingleton);
        timer.schedule(task, Integer.valueOf(pathUtil.getProperty("spinach.search.max.timeout.seconds")) * 1000);
        return this;
    }

    @Override
    public void cancelTimer() {
        timer.cancel();
    }
}
