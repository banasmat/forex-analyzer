package com.jorm.forex.forex_calendar_event;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class ForexFactoryUrlGenerator implements ForexCalendarEventProviderUrlGenerator{

    private String baseUrl = "https://www.forexfactory.com/calendar.php?day=";

    public String generate(LocalDateTime date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMd.YYYY", Locale.UK);
        String dateString = date.format(formatter).toLowerCase();
        return baseUrl + dateString;
    }

}
