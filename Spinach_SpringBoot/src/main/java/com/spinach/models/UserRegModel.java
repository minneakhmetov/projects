package com.spinach.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegModel {
    long id;
    String birthday;
    String city;
    String firstName;
    String lastName;
    String email;
    String hashPassword;
}
