package com.spinach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RelationUserModel {
    long userId;
    long partnerId;
    boolean isFriend;
}
