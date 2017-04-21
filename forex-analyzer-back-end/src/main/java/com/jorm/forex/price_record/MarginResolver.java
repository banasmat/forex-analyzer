package com.jorm.forex.price_record;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class MarginResolver {
//    public LocalDateTime applyMinutesMargin(LocalDateTime dateTime, Integer minutesMargin){
//
//    }

    public Integer countMinutesMargin(LocalDateTime start, LocalDateTime end, Integer marginPercent){
        Integer minutesInterval = (int)start.until(end, ChronoUnit.MINUTES);
        return minutesInterval * marginPercent / 100;
    }
}
