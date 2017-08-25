package com.jorm.forex.forex_calendar_event;

import com.jorm.forex.http.RestClient;
import com.jorm.forex.model.ForexCalendarEvent;
import com.jorm.forex.util.Format;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ForexCalendarEventProviderTest {

    private ForexCalendarEventProvider newsDataProvider;

    @Mock
    private RestClient client;

    @Mock
    private DailyFXUrlGenerator urlGenerator;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        newsDataProvider = new DailyFXForexCalendarEventProvider(client, urlGenerator);
    }

    @Test
    public void shouldFindNewsBetweenTwoDatetimes() throws IOException {

        String responseContent = FileUtils.readFileToString(new FileSystemResource("src/test/resources/daily-fx-news-html-sample.html").getFile());

        LocalDateTime firstDateTime = LocalDateTime.parse("06-09-2016 06:00:00", Format.dateTimeFormatter);
        LocalDateTime lastDateTime = LocalDateTime.parse("06-09-2016 07:15:00", Format.dateTimeFormatter);

        String url = "http://some-url";

        List<ForexCalendarEvent> expectedResult = new ArrayList<ForexCalendarEvent>(){
            {
                add(new ForexCalendarEvent("EUR German Factory Orders s.a. (MoM) (JUL)", firstDateTime, DailyFXForexCalendarEventProvider.class.toString(), url));
                add(new ForexCalendarEvent("EUR German Factory Orders n.s.a. (YoY) (JUL)", LocalDateTime.parse("06-09-2016 06:00:00", Format.dateTimeFormatter), DailyFXForexCalendarEventProvider.class.toString(), url));
                add(new ForexCalendarEvent("CHF Consumer Price Index (MoM) (AUG)", LocalDateTime.parse("06-09-2016 07:15:00", Format.dateTimeFormatter), DailyFXForexCalendarEventProvider.class.toString(), url));
                add(new ForexCalendarEvent("CHF Consumer Price Index (YoY) (AUG)", LocalDateTime.parse("06-09-2016 07:15:00", Format.dateTimeFormatter), DailyFXForexCalendarEventProvider.class.toString(), url));
                add(new ForexCalendarEvent("CHF CPI EU Harmonized (MoM) (AUG)", LocalDateTime.parse("06-09-2016 07:15:00", Format.dateTimeFormatter), DailyFXForexCalendarEventProvider.class.toString(), url));
                add(new ForexCalendarEvent("CHF CPI EU Harmonized (YoY) (AUG)", lastDateTime, DailyFXForexCalendarEventProvider.class.toString(), url));
            }
        };

        when(urlGenerator.generate(firstDateTime)).thenReturn(url);
        //when(urlGenerator.generate(lastDateTime)).thenReturn(url); FIXME range
        when(client.get(url)).thenReturn(responseContent);

        List<ForexCalendarEvent> result = newsDataProvider.getNewsInDateTimeRange(firstDateTime, lastDateTime);

        assertTrue(result.size() == expectedResult.size());

        for(int i = 0; i < result.size(); i++){
            assertEquals(expectedResult.get(i).getTitle(), result.get(i).getTitle());
            assertEquals(expectedResult.get(i).getDateTime(), result.get(i).getDateTime());
            assertEquals(expectedResult.get(i).getDataProviderClass(), result.get(i).getDataProviderClass());
            assertEquals(expectedResult.get(i).getUrl(), result.get(i).getUrl());
        }
    }
}