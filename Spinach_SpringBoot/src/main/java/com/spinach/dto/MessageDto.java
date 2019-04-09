package com.spinach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class MessageDto {
    long userId;
    long chatId;
    long partnerId;
    String text;
}
