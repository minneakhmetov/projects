package com.spinach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class MessageTimeDto  {
    long userId;
    long chatId;
    long partnerId;
    String text;
    String time;

    public MessageTimeDto(MessageDto dto, String time){
        userId = dto.getUserId();
        chatId = dto.getChatId();
        partnerId = dto.getPartnerId();
        text = dto.getText();
        this.time = time;
    }

}
