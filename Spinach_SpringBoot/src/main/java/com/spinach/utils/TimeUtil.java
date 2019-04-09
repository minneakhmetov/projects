package com.spinach.utils;

import org.springframework.stereotype.Service;
import ru.qatools.properties.Property;
import ru.qatools.properties.Resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
//@Resource.File("C:\\Users\\razil\\Desktop\\spinach_gitlab\\Spinach_SpringBoot\\src\\main\\resources\\application.properties")
public class TimeUtil {



    private static final LocalDateTime now = LocalDateTime.now();
    private static final String TODAY = "Сегодня"; //todo: переделать на локали
    private static final String YESTERDAY = "Вчера";
    private static final String TOMORROW = "Завтра";

    public String chatsTime(LocalDateTime time) {
        //System.out.println(string);
        if(time.toLocalDate().equals(now.toLocalDate())){
            return TODAY;
        } else if (time.toLocalDate().equals(now.minusDays(1).toLocalDate())){
            return YESTERDAY;
        } else if (time.toLocalDate().equals(now.plusDays(1).toLocalDate())){
            return TOMORROW;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
            String timeString = time.format(formatter).replace(".", "");
            return String.valueOf(timeString.charAt(0)).toUpperCase() + timeString.substring(1);
        }

    }

    public String messageTime(LocalDateTime time){
        if(time.toLocalDate().equals(now.toLocalDate())){
            return TODAY;
        } else if (time.toLocalDate().equals(now.minusDays(1).toLocalDate())){
            return YESTERDAY;
        } else if (time.toLocalDate().equals(now.plusDays(1).toLocalDate())){
            return TOMORROW;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
            DateTimeFormatter timeHourMinute = DateTimeFormatter.ofPattern("HH:mm");
            String timeString = time.format(formatter).replace(".", "");
            timeString = String.valueOf(timeString.charAt(0)).toUpperCase() + timeString.substring(1) +
                    " | " + time.format(timeHourMinute);
            return timeString;
        }
    }
}
