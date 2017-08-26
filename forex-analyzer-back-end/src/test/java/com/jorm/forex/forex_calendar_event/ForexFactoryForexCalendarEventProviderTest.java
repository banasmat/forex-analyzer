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

public class ForexFactoryForexCalendarEventProviderTest {

    private ForexCalendarEventProvider newsDataProvider;

    @Mock
    private RestClient client;

    @Mock
    private ForexCalendarEventProviderUrlGenerator urlGenerator;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        newsDataProvider = new ForexFactoryForexCalendarEventProvider(client, urlGenerator);
    }

    @Test
    public void shouldFindNewsBetweenTwoDatetimes() throws IOException {

        String responseContent = FileUtils.readFileToString(new FileSystemResource("src/test/resources/daily-fx-news-html-sample.html").getFile());

        LocalDateTime firstDateTime = LocalDateTime.parse("11-12-2014 14:00:00", Format.dateTimeFormatter);
        LocalDateTime lastDateTime = LocalDateTime.parse("12-12-2014 13:00:00", Format.dateTimeFormatter);

        String url1 = "http://some-url-1";
        String url2 = "http://some-url-2";

        List<ForexCalendarEvent> expectedResult = new ArrayList<ForexCalendarEvent>(){
            {
                add(new ForexCalendarEvent("30-y Bond Auction", LocalDateTime.parse("11-12-2016 14:01:00", Format.dateTimeFormatter), ForexFactoryForexCalendarEventProvider.class.toString(), url1 + "#detail=49847", "USD", "2.85|2.8", "3.09|2.3", "", Impact.LOW));
                add(new ForexCalendarEvent("Business NZ Manufacturing Index", LocalDateTime.parse("11-12-2016 17:30:00", Format.dateTimeFormatter), ForexFactoryForexCalendarEventProvider.class.toString(), url1 + "#detail=50513", "NZD", "55.2", "58.9", "", Impact.MEDIUM));
                add(new ForexCalendarEvent("Revised Industrial Production m/m", LocalDateTime.parse("12-12-2016 12:33:00", Format.dateTimeFormatter), ForexFactoryForexCalendarEventProvider.class.toString(), url2 + "#detail=51240", "JPY", "0.4%", "0.2%", "0.2%", Impact.LOW));
            }
        };

        when(urlGenerator.generate(firstDateTime)).thenReturn(url1);
        when(urlGenerator.generate(lastDateTime)).thenReturn(url2);
        when(client.get(url1)).thenReturn(responseContent);

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