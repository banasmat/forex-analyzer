package com.jorm.forex.forex_calendar_event;

import com.jorm.forex.util.Format;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ForexFactoryUrlGeneratorTest {
    private ForexFactoryUrlGenerator urlGenerator;

    @Before
    public void setUp() {
        urlGenerator = new ForexFactoryUrlGenerator();
    }

    @Test
    public void shouldGenerateUrlForGivenDate(){
        assertEquals("https://www.forexfactory.com/calendar.php?day=aug23.2017", urlGenerator.generate(LocalDateTime.parse("23-08-2017 15:20:00", Format.dateTimeFormatter)));
        assertEquals("https://www.forexfactory.com/calendar.php?day=may9.2000", urlGenerator.generate(LocalDateTime.parse("09-05-2000 00:05:20", Format.dateTimeFormatter)));
        assertEquals("https://www.forexfactory.com/calendar.php?day=dec11.2014", urlGenerator.generate(LocalDateTime.parse("11-12-2014 00:00:00", Format.dateTimeFormatter)));
    }
}