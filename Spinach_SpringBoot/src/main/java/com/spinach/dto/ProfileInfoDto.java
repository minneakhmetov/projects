package com.spinach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProfileInfoDto {
    long id;
    String name;
    String photo;


}
