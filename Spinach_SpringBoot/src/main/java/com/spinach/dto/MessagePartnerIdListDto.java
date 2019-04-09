package com.spinach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class MessagePartnerIdListDto {
    long partnerId;
    List<MessageListDto> dtoList;
    String photo;
    SurveyEnum surveyEnum;
}
