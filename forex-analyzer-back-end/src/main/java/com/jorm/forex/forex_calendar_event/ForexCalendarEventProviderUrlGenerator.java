package com.jorm.forex.forex_calendar_event;

import java.time.LocalDateTime;

public interface ForexCalendarEventProviderUrlGenerator {

    public String generate(LocalDateTime date);
    
}
