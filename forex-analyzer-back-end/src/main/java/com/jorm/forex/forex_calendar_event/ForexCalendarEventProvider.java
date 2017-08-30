package com.jorm.forex.forex_calendar_event;

import com.jorm.forex.model.ForexCalendarEvent;

import java.time.LocalDateTime;
import java.util.List;

public interface ForexCalendarEventProvider {

    List<ForexCalendarEvent> getNewsInDateTimeRange(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);

    String getName();
}
