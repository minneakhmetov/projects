package com.spinach.utils;

import com.spinach.dao.AnonimRepository;
import com.spinach.dao.SurveyStateRepository;
import com.spinach.dao.UsersRepository;
import com.spinach.dto.SimilarityDto;
import com.spinach.dto.TimerForUser;
import com.spinach.models.CompatibilityModel;
import com.spinach.utils.timer.timers.SurveyBothUsersTimer;
import com.spinach.utils.timer.timers.SurveyCancelTimer;
import com.spinach.utils.timer.timers.SurveyOneUserTimer;
import com.spinach.utils.timer.timers.SurveyTimer;
import com.spinach.websocket.services.WebSocketService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimilaritySingleton {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private SurveyStateRepository surveyStateRepository;

    @Autowired
    private PathUtil pathUtil;

    @Autowired
    private AnonimRepository anonimRepository;

    @Autowired
    private UsersRepository usersRepository;

    private List<SimilarityDto> dtos = new ArrayList<SimilarityDto>();
    @Getter
    private Set<Long> queue = new HashSet<Long>();
    private Set<Long> inProcessing = new HashSet<Long>();
    //private Set<Long> timers = new HashSet<Long>();

    private Set<TimerForUser> timerForUsers = new HashSet<TimerForUser>();

    private Set<TimerForUser> timerRandomUsers = new HashSet<TimerForUser>();

    public boolean containsInTimer(long userId) {
        for (TimerForUser timerForUser: timerForUsers)
            if (timerForUser.equals(emptyTimerUserCreator(userId)))
                return true;
        return false;

    }

    public void addInTimer(String email, long userId) {
        timerForUsers.add(new TimerForUser(new SurveyOneUserTimer(webSocketService, anonimRepository, surveyStateRepository, this, pathUtil).startTimer(email, userId), userId));
    }

    public void addInTimerBothUsers(String userEmail, String partnerEmail, long userId, long partnerId) {
        SurveyTimer timer = new SurveyBothUsersTimer(webSocketService, anonimRepository, surveyStateRepository, this, pathUtil).startTimer(userEmail, partnerEmail, userId, partnerId);
        timerForUsers.add(new TimerForUser(timer, userId));
        timerForUsers.add(new TimerForUser(timer, partnerId));
    }

    public void removeFromTimer(long userId) {
        for (TimerForUser timerForUser: timerForUsers)
            if (timerForUser.equals(emptyTimerUserCreator(userId))){
                timerForUsers.remove(timerForUser);
                return;
            }
    }

    public boolean containsInTimerRandomUsers(long userId) {
        for (TimerForUser timerForUser: timerRandomUsers)
            if (timerForUser.equals(emptyTimerUserCreator(userId)))
                return true;
        return false;
    }

    public void addInTimerRandomUsers(long userId) {
        timerRandomUsers.add(new TimerForUser(new SurveyCancelTimer(webSocketService, usersRepository, this, pathUtil).startTimer(userId), userId));
    }

    public void removeFromTimerRandomUsers(long userId) {
        for (TimerForUser timer : timerRandomUsers) {
            if (timer.equals(emptyTimerUserCreator(userId))) {
                timer.getTimer().cancelTimer();
                removeFromTimerRandomUsersWithOutTimer(userId);
                return;
            }
        }
    }

    public void removeFromTimerRandomUsersWithOutTimer(long userId) {
        for (TimerForUser timerForUser: timerRandomUsers)
            if (timerForUser.equals(emptyTimerUserCreator(userId))){
                timerRandomUsers.remove(timerForUser);
                return;
            }
    }

    public boolean existsRandomUsers(long userId) {
        int i = 0;
        for (TimerForUser randomUser : timerRandomUsers)
            if (randomUser.getUserId() != userId)
                i++;
        return i != 0;
    }

    public List<Long> getAllRandomUsers() {
        List<Long> result = new ArrayList<>();
        for(TimerForUser user : timerRandomUsers)
            result.add(user.getUserId());
        return result;
    }

//    public boolean containsInTimer(long userId) {
//        return timers.contains(userId);
//    }
//
//    public void addInTimer(long userId) {
//        timers.add(userId);
//    }
//
//    public void removeFromTimer(long userId) {
//        timers.remove(userId);
//    }

    public void add(SimilarityDto dto) {
        dtos.add(dto);
    }


    public boolean isInProcessing(long userId) {
        return inProcessing.contains(userId);
    }

    public void addToProcessing(long userId) {
        inProcessing.add(userId);
    }

    public void removeFromProcessing(long userId) {
        inProcessing.remove(userId);
    }

    public SimilarityDto existUser(long userId) {
        for (int i = 0; i < dtos.size(); i++) {
            if (dtos.get(i).getPartnerId() == userId) {
                return dtos.get(i);
            }
        }
        return null;
    }

    public boolean containsInDtos(long userId){
        for (SimilarityDto dto : dtos)
            if (dto.getUserId() == userId)
                return true;
        return false;
    }

    public void deleteByPartnerId(long userId) {
        for (int i = 0; i < dtos.size(); i++) {
            if (dtos.get(i).getPartnerId() == userId)
                dtos.remove(i);
        }
    }

    public void deleteByUserId(long userId) {
        for (int i = 0; i < dtos.size(); i++) {
            if (dtos.get(i).getUserId() == userId)
                dtos.remove(i);
        }
    }

    private void printer() {
        for (int i = 0; i < dtos.size(); i++) {
            System.out.println(dtos.get(i).toString());
        }
    }

    public void addToQueue(long id) {
        queue.add(id);
    }

    public void deleteFromQueue(long id) {
        queue.remove(id);
    }

    public Long computeMaxSimilarity(List<CompatibilityModel> models, long userId) {
        for (CompatibilityModel model : models) {
            if (queue.contains(model.getUserId()) && model.getUserId() != userId)
                return model.getUserId();
            if (queue.contains(model.getPartnerId()) && model.getPartnerId() != userId)
                return model.getPartnerId();
        }
        return null;
    }

    public TimerForUser emptyTimerUserCreator(long userId) {
        return new TimerForUser(null, userId);
    }


}
