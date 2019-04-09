package com.spinach.models;

import com.spinach.dto.SurveyEnum;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SurveyStateModel {
    public SurveyStateModel(long userId, SurveyEnum surveyEnum) {
        this.userId = userId;
        this.surveyEnum = surveyEnum;
    }

    long userId;
    SurveyEnum surveyEnum;
    public SurveyStateModel(long userId, String surveyEnum){
        if(surveyEnum.equals(SurveyEnum.MAIN.toString())){
            this.surveyEnum = SurveyEnum.MAIN;
        }
        if(surveyEnum.equals(SurveyEnum.YES.toString())){
            this.surveyEnum = SurveyEnum.YES;
        }
        if(surveyEnum.equals(SurveyEnum.NO.toString())){
            this.surveyEnum = SurveyEnum.NO;
        }
        this.userId = userId;
    }





}
