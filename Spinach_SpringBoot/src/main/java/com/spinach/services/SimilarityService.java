package com.spinach.services;

import com.spinach.dao.AnonimRepository;
import com.spinach.dao.ChatsRepository;
import com.spinach.dao.SimilaritiesRepository;
import com.spinach.dao.UsersRepository;
import com.spinach.dto.ChatDto;
import com.spinach.dto.MessageDto;
import com.spinach.dto.SimilarityDto;
import com.spinach.exceptions.UserNotFoundException;
import com.spinach.models.*;
import com.spinach.utils.SimilaritySingleton;
import com.spinach.websocket.services.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimilarityService {

    @Autowired
    private SimilaritiesRepository similaritiesRepository;

    @Autowired
    private SimilaritySingleton similaritySingleton;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AnonimRepository anonimRepository;

    @Autowired
    private ChatsRepository chatsRepository;

    @Autowired
    private ChatsService chatsService; //todo: переделать

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private IgnoreService ignoreService; //todo: ???

    private ChatDto compute(long userId) throws UserNotFoundException {
        if (usersRepository.isRandom(userId)) {
            if (similaritySingleton.existsRandomUsers(userId)) {
                Long partnerId = exceptIgnoreList(userId);
                if(partnerId == null){
                    similaritySingleton.addInTimerRandomUsers(userId);
                    similaritySingleton.removeFromProcessing(userId);
                    throw new UserNotFoundException();
                } else {
                    similaritySingleton.removeFromTimerRandomUsers(partnerId);
                    return createDto(partnerId, userId);
                }
            } else {
                similaritySingleton.addInTimerRandomUsers(userId);
                similaritySingleton.removeFromProcessing(userId);
                throw new UserNotFoundException();
            }
            //if not random
        } else {

            //todo: для нейронки


           // similaritySingleton.addToProcessing(userId);
            SimilarityDto dto = similaritySingleton.existUser(userId);

            if (dto != null) {
                similaritySingleton.deleteByPartnerId(userId);
                return createDto(dto.getPartnerId(), dto.getUserId());
            } else {
                List<CompatibilityModel> models = similaritiesRepository.getSimilarities(userId);
                if (models.size() != 0) {
                    Long partnerId = similaritySingleton.computeMaxSimilarity(models, userId);
                    if (partnerId != null) {
                        similaritySingleton.add(SimilarityDto.builder().userId(userId).partnerId(partnerId).build());
                        similaritySingleton.deleteFromQueue(partnerId);
                    } else {
                        similaritySingleton.addToQueue(userId);
                    }
                }
                similaritySingleton.removeFromProcessing(userId);
                throw new UserNotFoundException();
            }
        }
    }


    public ChatDto createChat(long userId) throws UserNotFoundException {

        if (anonimRepository.read(userId) != null || similaritySingleton.isInProcessing(userId))
            return null;


        similaritySingleton.addToProcessing(userId);
        ChatDto chatDto = compute(userId);
        MessageDto dto = MessageDto.builder()
                .chatId(chatDto.getChatId())
                .partnerId(chatDto.getPartnerId())
                .userId(userId)
                .text("Привет, я твой новый собеседник!") //todo: вывести наружу и локали
                .build();
        chatsService.sendMessage(dto);
        similaritySingleton.removeFromProcessing(userId);
        return ChatDto.builder()
                .chatId(chatDto.getChatId())
                .partnerId(chatDto.getPartnerId())
                .userId(userId)
                .build();
    }


    private ChatDto createDto(long partnerId, long userId) {
        anonimRepository.save(createList(userId, partnerId));
        long chatId = chatsRepository.save("{" + userId + ", " + partnerId + "}");
        List<IdEmailModel> idEmailModels = usersRepository.getBothEmailsByIds(userId, partnerId);
        String userEmail = null;
        String partnerEmail = null;
        for (int i = 0; i < idEmailModels.size(); i++){
            if (idEmailModels.get(i).getId() == userId)
                userEmail = idEmailModels.get(i).getEmail();
            if (idEmailModels.get(i).getId() == partnerId)
                partnerEmail = idEmailModels.get(i).getEmail();
        }
        ChatDto dto = ChatDto.builder()
                .userId(userId)
                .partnerId(partnerId)
                .chatId(chatId)
                .build();
        webSocketService.addedUser(dto, partnerEmail);
        ignoreService.saveToBothUsers(userId, partnerId);
        similaritySingleton.addInTimerBothUsers(userEmail, partnerEmail, userId, partnerId);
        return dto;
    }

    private List<SimilarityModel> createList(long userId, long partnerId){
        List<SimilarityModel> list = new ArrayList<>();
        list.add(SimilarityModel.builder().userId(userId).partnerId(partnerId).build());
        list.add(SimilarityModel.builder().userId(partnerId).partnerId(userId).build());
        return list;
    }

    private Long exceptIgnoreList(long userId){

        List<IgnoreModel> models = ignoreService.read(userId);
        printer(models);


        for (Long randomUser : similaritySingleton.getAllRandomUsers()){
            boolean flag = true;
            for (IgnoreModel model : models){
                if(!(randomUser != model.getPartnerId() && randomUser != userId))
                    flag = false;
            }
            if(flag)
                return randomUser;
        }

        if(models.size() == 0){
            for (Long randomUser : similaritySingleton.getAllRandomUsers()){
                if(randomUser != userId)
                    return randomUser;
            }
        }

        return null;
    }

    public boolean cancelSearching(long userId){
        if(similaritySingleton.containsInDtos(userId)){
            similaritySingleton.deleteByUserId(userId);
            return true;
        }
        else if(similaritySingleton.containsInTimerRandomUsers(userId)){
            similaritySingleton.removeFromTimerRandomUsers(userId);
            return true;
        }
        return false;
    }

    private void printer(List list){
        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
    }
}
