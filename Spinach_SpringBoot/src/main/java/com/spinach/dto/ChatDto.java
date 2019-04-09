package com.spinach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ChatDto {
    long chatId;
    long partnerId;
    long userId;
}
