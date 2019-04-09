package com.spinach.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class ChatsMessageModel {
    long chatId;
    long userId;
    long partnerId;
    LocalDateTime lastMessageTime;
    String textMessage;
}
