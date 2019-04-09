package com.spinach.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class IdEmailModel {
    long id;
    String email;
}
