package com.kursach.models;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Auth {
    Integer userId;
    String auth;

    public boolean isNotNull(){
        return !(userId == null || auth == null);
    }
}
