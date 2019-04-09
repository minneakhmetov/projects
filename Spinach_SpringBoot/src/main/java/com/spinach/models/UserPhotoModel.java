package com.spinach.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPhotoModel {
    String photo50;
    String photo100;
    String photo200;
    String photo400;
}
