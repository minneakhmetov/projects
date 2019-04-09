package com.spinach.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatPhotoModel {
    long id;
    long userId;
    long partnerId;
    String name;
    String surname;
    String photoUrl;
    String lastMessage;
    LocalDateTime chatTime;
}
