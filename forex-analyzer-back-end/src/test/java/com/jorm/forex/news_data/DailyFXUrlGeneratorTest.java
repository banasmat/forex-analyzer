package com.jorm.forex.news_data;

import com.jorm.forex.util.Format;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class DailyFXUrlGeneratorTest {

    private DailyFXUrlGenerator urlGenerator;

    @Before
    public void setUp() {
        urlGenerator = new DailyFXUrlGenerator();
    }

    @Test
    public void shouldGenerateUrlForGivenDate(){
        //FIXME it seems that intervals between xxx in 2016/xxx are not equal (7 or multiple of 7 (?))

        assertEquals("https://www.dailyfx.com/calendar?previous=true&week=2016/0766", urlGenerator.generate(LocalDateTime.parse("06-09-2016 00:00:00", Format.dateTimeFormatter)));
        assertEquals("https://www.dailyfx.com/calendar?previous=true&week=2016/1030", urlGenerator.generate(LocalDateTime.parse("04-11-2016 00:00:00", Format.dateTimeFormatter)));
        assertEquals("https://www.dailyfx.com/calendar?previous=true&week=2016/1106", urlGenerator.generate(LocalDateTime.parse("06-11-2016 00:00:00", Format.dateTimeFormatter)));
        assertEquals("https://www.dailyfx.com/calendar?previous=true&week=2016/1225", urlGenerator.generate(LocalDateTime.parse("30-12-2016 00:00:00", Format.dateTimeFormatter)));
        assertEquals("https://www.dailyfx.com/calendar?previous=true&week=2017/0101", urlGenerator.generate(LocalDateTime.parse("01-01-2017 00:00:00", Format.dateTimeFormatter)));
        assertEquals("https://www.dailyfx.com/calendar?previous=true&week=2017/0813", urlGenerator.generate(LocalDateTime.parse("15-08-2017 00:00:00", Format.dateTimeFormatter)));
    }
}