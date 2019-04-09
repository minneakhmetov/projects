package com.spinach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class SimilarityDto {
    private long userId;
    private long partnerId;
}
