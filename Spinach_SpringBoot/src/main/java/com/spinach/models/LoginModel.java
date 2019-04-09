package com.spinach.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginModel {
    long id;
    String email;
    String hashPassword;
    String photo;
    String name;
    String surname;
}
