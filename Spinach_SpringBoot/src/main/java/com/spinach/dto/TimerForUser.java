package com.spinach.dto;

import com.spinach.utils.timer.timers.SurveyTimer;
import lombok.*;

import java.util.Timer;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TimerForUser {
    private SurveyTimer timer;
    private long userId;

    @Override
    public boolean equals(Object obj) {
        TimerForUser timerForUser = (TimerForUser) obj;
        return userId == timerForUser.userId;
    }
}
