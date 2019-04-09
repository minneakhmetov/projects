package com.spinach.services;

import com.spinach.dao.*;
import com.spinach.dto.*;
import com.spinach.models.*;
import com.spinach.utils.PathUtil;
import com.spinach.utils.SimilaritySingleton;
import com.spinach.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatsService {
    private UsersRepository usersRepository;
    private MessagesRepository messagesRepository;


    @Autowired
    public ChatsService(UsersRepository usersRepository, MessagesRepository messagesRepository) {
        this.usersRepository = usersRepository;
        this.messagesRepository = messagesRepository;

    }

    @Autowired
    private TimeUtil timeUtil;

    @Autowired
    private AnonimRepository anonimRepository;

    @Autowired
    private SurveyStateRepository surveyStateRepository;

    @Autowired
    private SimilaritySingleton similaritySingleton;

    public StartChatsListDto getChats(long userId) {

        List<ChatsMessageModel> chatMessageModels = messagesRepository.getChats(userId);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chatMessageModels.size(); i++) {
            if (chatMessageModels.get(i).getUserId() == userId)
                builder.append(chatMessageModels.get(i).getPartnerId());
            else
                builder.append(chatMessageModels.get(i).getUserId());
            if (i != chatMessageModels.size() - 1) {
                builder.append(", ");
            }
        }

        boolean isAvailable = true;
        List<ChatsListDto> chatsListDtos = null;

        if (!builder.toString().equals("")) {
            List<UserShortModel> userShortModels = usersRepository.getUsers(builder.toString()); //todo: если нет сообщений
            SimilarityModel model = anonimRepository.read(userId);

            chatsListDtos = new ArrayList<>();

            isAvailable = false;

            for (ChatsMessageModel chatMessageModel : chatMessageModels) {

                long partnerId = (chatMessageModel.getUserId() == userId) ?
                        chatMessageModel.getPartnerId() : chatMessageModel.getUserId();

                if (model == null) {
                    chatsListDtos.add(createDto(partnerId, userShortModels, chatMessageModel));
                    isAvailable = true;

                } else {
                    isAvailable = false;
                    if ((partnerId == model.getPartnerId() && userId == model.getUserId())
                            || (partnerId == model.getUserId() && userId == model.getPartnerId())) {
                        //todo: локали
                        String message = (chatMessageModel.getUserId() == partnerId) ?
                                "Аноним: " + chatMessageModel.getTextMessage() : "Вы: " + chatMessageModel.getTextMessage();

                        ChatsListDto dto = ChatsListDto.builder()
                                .id(chatMessageModel.getChatId())
                                .message(message)
                                .time(timeUtil.chatsTime(chatMessageModel.getLastMessageTime()))
                                .name("Анонимный пользователь") //todo: переделать
                                .photo("/usersPhotos/default/anonim_100.jpg") //todo
                                .build();
                        chatsListDtos.add(dto);
                        //model = null;
                    } else {
                        chatsListDtos.add(createDto(partnerId, userShortModels, chatMessageModel));
                    }
                }
            }

        }

        return StartChatsListDto.builder()
                .chats(chatsListDtos)
                .isSearching(similaritySingleton.containsInTimerRandomUsers(userId) ) //todo :додлеать для нейронки
                .isAvailable(isAvailable)
                .build();
    }

    public MessagePartnerIdListDto getChat(long chatId, long userId) {
        List<MessageListModel> models = messagesRepository.readByChatId(chatId);
        List<MessageListDto> dtos = new ArrayList<>();

        SimilarityModel similarityModel = anonimRepository.read(userId);

        String photo = null;
        long partnerId = 0;
        for (MessageListModel model : models) {
            if (model.getPartnerId() != userId) {
                partnerId = model.getPartnerId();
                photo = model.getPhotoUrl();
                break;
            } else {
                partnerId = model.getUserId();
            }
        }
        SurveyStateModel surveyStateModel = surveyStateRepository.read(userId);
        SurveyEnum surveyEnum = null;


        boolean isOpenedChatWithAnonim = false;

        for (MessageListModel model : models) {
            if (similarityModel != null) {
                if (model.getUserId() == similarityModel.getUserId() & model.getPartnerId() == similarityModel.getPartnerId() || model.getPartnerId() == similarityModel.getUserId() & model.getUserId() == similarityModel.getPartnerId()){
                    photo = "/usersPhotos/default/anonim_100.jpg";
                    isOpenedChatWithAnonim = true;
                }

            }
                MessageListDto dto = MessageListDto.builder()
                        .id(model.getId())
                        .isOwner(model.getUserId() == userId)
                        .photo(photo)
                        .text(model.getMessage())
                        .time(timeUtil.messageTime(model.getTime()))
                        //.partnerId(partnerId)
                        .build();
                dtos.add(dto);


        }
        if (surveyStateModel != null && isOpenedChatWithAnonim) {
            surveyEnum = surveyStateModel.getSurveyEnum();
        } else {
            if (similarityModel != null && !similaritySingleton.containsInTimer(userId) && isOpenedChatWithAnonim)
                surveyEnum = SurveyEnum.MAIN;
        }
        return MessagePartnerIdListDto.builder()
                .dtoList(dtos)
                .photo(photo)
                .surveyEnum(surveyEnum)
                //.chatId(chatId)
                .partnerId(partnerId)
                .build();

    }

    public TimeAndEmailDto sendMessage(MessageDto dto) {
        if(dto.getText().equals(""))
            return null;
        LocalDateTime dateTime = LocalDateTime.now();
        MessageModel model = MessageModel.builder()
                .chatId(dto.getChatId())
                .messageTime(dateTime)
                .partnerId(dto.getPartnerId())
                .textMessage(dto.getText())
                .userId(dto.getUserId())
                .build();
        messagesRepository.save(model);
        return TimeAndEmailDto.builder()
                .email(usersRepository.getEmailById(dto.getPartnerId()))
                .time(timeUtil.chatsTime(dateTime))
                .build();
    }


    private ChatsListDto createDto(long partnerId, List<UserShortModel> userShortModels, ChatsMessageModel chatMessageModel) {
        UserShortModel userShortModel = null;
        for (UserShortModel userShortModelCandidate : userShortModels) {
            if (userShortModelCandidate.getId() == partnerId) {
                userShortModel = userShortModelCandidate;
                break;
            }
        }

        String message = (chatMessageModel.getUserId() == partnerId) ?
                userShortModel.getFirstName() + ": " + chatMessageModel.getTextMessage() : "Вы: " + chatMessageModel.getTextMessage(); //todo: локали

        return ChatsListDto.builder()
                .id(chatMessageModel.getChatId())
                .photo(userShortModel.getPhotoUrl())
                .name(userShortModel.getFirstName() + " " + userShortModel.getLastName())
                .time(timeUtil.chatsTime(chatMessageModel.getLastMessageTime()))
                .message(message)
                .build();

    }

    public long getChatId(long userId, long partnerId) {
        return messagesRepository.getChatId(userId, partnerId);
    }
}
