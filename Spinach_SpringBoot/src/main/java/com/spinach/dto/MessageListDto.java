package com.spinach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageListDto {
    long id;
    boolean isOwner;
    String photo;
    String time;
    String text;
    //long partnerId;
}
