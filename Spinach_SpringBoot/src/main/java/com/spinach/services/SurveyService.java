package com.spinach.services;

import com.google.common.collect.Lists;
import com.spinach.dao.*;
import com.spinach.dto.ChatsListDto;
import com.spinach.dto.RelationUserModel;
import com.spinach.dto.SurveyEnum;
import com.spinach.dto.SurveyNotification;
import com.spinach.models.*;
import com.spinach.utils.SimilaritySingleton;
import com.spinach.utils.TimeUtil;
import com.spinach.utils.timer.timers.SurveyBothUsersTimer;
import com.spinach.websocket.services.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private AnonimRepository anonimRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private TimeUtil timeUtil;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private SimilaritySingleton similaritySingleton;

    @Autowired
    private SurveyStateRepository surveyStateRepository;

    @Autowired
    private RelationUserRepository relationUserRepository;

    public ChatsListDto acceptFriendship(long userId) {

        Long partnerId = getPartnerId(userId);
        if(partnerId != null) {
            anonimRepository.delete(createList(userId, partnerId));
            UserShortModel model = usersRepository.readShort(partnerId);
            UserShortModel user = usersRepository.readShort(userId);
            List<ChatsMessageModel> chatsMessageModelList = messagesRepository.getChats(userId);

            ChatsMessageModel chatsMessageModel = null;

            for (ChatsMessageModel chatsMessageModelCandidate : chatsMessageModelList) {
                if ((chatsMessageModelCandidate.getUserId() == userId && chatsMessageModelCandidate.getPartnerId() == partnerId)
                        || (chatsMessageModelCandidate.getPartnerId() == userId && chatsMessageModelCandidate.getUserId() == partnerId))
                    chatsMessageModel = chatsMessageModelCandidate;
            }


            String message = (chatsMessageModel.getUserId() == partnerId) ?
                    model.getFirstName() + ": " + chatsMessageModel.getTextMessage() : "Вы: " + chatsMessageModel.getTextMessage();

            ChatsListDto dtoUser = ChatsListDto.builder()
                    .id(chatsMessageModel.getChatId())
                    .message(message)
                    .time(timeUtil.chatsTime(chatsMessageModel.getLastMessageTime()))
                    .name(user.getFirstName() + " " + user.getLastName()) //todo: переделать
                    .photo(user.getPhotoUrl()) //todo
                    .build();


            ChatsListDto dto = ChatsListDto.builder()
                    .id(chatsMessageModel.getChatId())
                    .message(message)
                    .time(timeUtil.chatsTime(chatsMessageModel.getLastMessageTime()))
                    .name(model.getFirstName() + " " + model.getLastName()) //todo: переделать
                    .photo(model.getPhotoUrl()) //todo
                    .build();

            webSocketService.survey(dtoUser, usersRepository.getEmailById(partnerId));
            surveyStateRepository.remove(userId, partnerId);

            relationUserRepository.save(Arrays.asList(new RelationUserModel(userId, partnerId, true), new RelationUserModel(partnerId, userId, true)));

            similaritySingleton.removeFromTimerRandomUsers(userId);
            similaritySingleton.removeFromTimerRandomUsers(partnerId);
            similaritySingleton.removeFromTimer(userId);
            similaritySingleton.removeFromTimer(partnerId);
            return dto;
        }
        return null;
    }

    public void invite(long userId){ // todo: сделать таймер
        Long partnerId = getPartnerId(userId);
        if(partnerId != null)
        webSocketService.survey(SurveyNotification.ADD_TO_FRIENDS, usersRepository.getEmailById(partnerId));
        surveyStateRepository.remove(userId);
        continueChatting(userId);
    }

    public void notAccept(long userId){ //todo:  когда человек не добавляет, надо заново таймер
        Long partnerId = getPartnerId(userId);
        if(partnerId != null)
        webSocketService.survey(SurveyNotification.NOT_CONFIRM, usersRepository.getEmailById(anonimRepository.read(userId).getPartnerId()));
        continueChatting(userId);
    }

    public void liked(long userId){
        Long partnerId = getPartnerId(userId);
        if(partnerId != null)
            surveyStateRepository.update(new SurveyStateModel(userId, SurveyEnum.YES));

    }

    public void notLiked(long userId){
        Long partnerId = getPartnerId(userId);
        if(partnerId != null)
            surveyStateRepository.update(new SurveyStateModel(userId, SurveyEnum.NO));
    }


    public Long leaveChat(long userId){
        Long partnerId = getPartnerId(userId);
        if(partnerId != null) {
            anonimRepository.delete(createList(userId, partnerId));
            long chatId = messagesRepository.getChatId(userId, partnerId);
            messagesRepository.deleteChat(userId, partnerId);
            webSocketService.survey(SurveyNotification.USER_LEAVED, usersRepository.getEmailById(partnerId));
            surveyStateRepository.remove(userId, partnerId);
            relationUserRepository.save(Arrays.asList(new RelationUserModel(userId, partnerId, false), new RelationUserModel(partnerId, userId, false)));
            similaritySingleton.removeFromTimerRandomUsers(userId);
            similaritySingleton.removeFromTimerRandomUsers(partnerId);
            similaritySingleton.removeFromTimer(userId);
            similaritySingleton.removeFromTimer(partnerId);
            return chatId;
        }
        return null;
    }

    public void continueChatting(long userId){
        Long partnerId = getPartnerId(userId);
        if(partnerId != null) {
            similaritySingleton.addInTimer(usersRepository.getEmailById(userId), userId);
            surveyStateRepository.remove(userId);
        }
    }

    private List<SimilarityModel> createList(long userId, long partnerId){
        List<SimilarityModel> list = new ArrayList<>();
        list.add(SimilarityModel.builder().userId(userId).partnerId(partnerId).build());
        list.add(SimilarityModel.builder().userId(partnerId).partnerId(userId).build());
        return list;
    }

    private Long getPartnerId(long userId){
        try {
            return anonimRepository.read(userId).getPartnerId();
        } catch (NullPointerException e){
            return null;
        }
    }



}
