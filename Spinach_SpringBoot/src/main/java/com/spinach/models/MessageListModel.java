package com.spinach.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class MessageListModel {
    long id;
    long userId;
    long partnerId;
    String message;
    LocalDateTime time;
    String name;
    String surname;
    String photoUrl;
    long chatId;
}
