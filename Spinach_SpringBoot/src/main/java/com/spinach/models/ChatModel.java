package com.spinach.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ChatModel {
    long id;
    long userId;
    long partnerId;
    LocalDateTime chatTime;
}
