package com.spinach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ChatsListDto {
    long id;
    String message;
    String photo;
    String name;
    String time;
}
