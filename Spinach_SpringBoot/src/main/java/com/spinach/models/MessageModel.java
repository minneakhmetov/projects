package com.spinach.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
public class MessageModel {
    long id;
    long chatId;
    long userId;
    long partnerId;
    String textMessage;
    LocalDateTime messageTime;
}
