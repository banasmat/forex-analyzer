package com.jorm.forex.price_record;

import com.jorm.forex.util.Format;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class MarginResolverTest {

    private static DateTimeFormatter dateFormat = Format.dateTimeFormatter;

    MarginResolver resolver;

    @Before
    public void setUp(){
        resolver = new MarginResolver();
    }

    @Test
    public void shouldReturnPercentageOfMinutesBetweenDates(){
        LocalDateTime start = LocalDateTime.parse("03-01-2016 17:00:00", dateFormat);
        LocalDateTime end = LocalDateTime.parse("03-01-2016 18:00:00", dateFormat);

        int result = resolver.countMinutesMargin(start, end, 10);

        assertEquals(6, result);
    }
}