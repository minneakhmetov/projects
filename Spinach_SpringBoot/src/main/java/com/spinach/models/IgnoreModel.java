package com.spinach.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class IgnoreModel {
    private long userId;
    private long partnerId;

}
