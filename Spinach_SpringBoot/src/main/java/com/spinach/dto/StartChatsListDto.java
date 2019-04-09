package com.spinach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class StartChatsListDto {
    boolean isAvailable;
    boolean isSearching;
    List<ChatsListDto> chats;
}
