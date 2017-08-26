package com.jorm.forex.forex_calendar_event;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DailyFXUrlGenerator implements ForexCalendarEventProviderUrlGenerator {

    public String generate(LocalDateTime date){
        return "";
    }

}
