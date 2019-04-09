package com.spinach.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Deprecated
@Getter
@Setter
@Builder
public class AuthModel {
    long id;
    String cookieValue;
}
