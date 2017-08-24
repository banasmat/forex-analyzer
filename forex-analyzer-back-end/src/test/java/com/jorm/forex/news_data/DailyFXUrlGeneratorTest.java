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
        assertEquals("https://www.dailyfx.com/calendar?previous=true&week=2016/0766", urlGenerator.generate(LocalDateTime.parse("06-09-2016 00:00:00", Format.dateTimeFormatter)));
    }
}