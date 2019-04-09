package com.spinach.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class SimilarityModel {
    long userId;
    long partnerId;
}
