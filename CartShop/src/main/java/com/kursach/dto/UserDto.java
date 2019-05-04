package com.kursach.dto;

import com.kursach.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

    String name;
    String surname;
    String photoURL;
    Integer vkId;
    String auth;

    public static UserDto from(User user){
        return UserDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .photoURL(user.getPhotoURL())
                .vkId(user.getVkId())
                .build();
    }
    public static UserDto from(User user, String auth){
        return UserDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .photoURL(user.getPhotoURL())
                .auth(auth)
                .vkId(user.getVkId())
                .build();
    }
}
