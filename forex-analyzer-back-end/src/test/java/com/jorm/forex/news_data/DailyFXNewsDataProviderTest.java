package com.jorm.forex.news_data;

import com.jorm.forex.model.News;
import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.util.Format;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DailyFXNewsDataProviderTest {

    private NewsDataProvider newsDataProvider;

    @Before
    public void setUp() throws Exception {
        newsDataProvider = new DailyFXNewsDataProvider();
    }

    @Test
    public void shouldFindNewsBetweenTwoDatetimes(){

        List<News> expectedResult = new ArrayList<News>(){
            {
                add(new News("EUR German Factory Orders s.a. (MoM) (JUL)", LocalDateTime.parse("06-09-2016 06:00:00", Format.dateTimeFormatter), DailyFXNewsDataProvider.class.toString(), "", new Symbol("EUR")));
                add(new News("EUR German Factory Orders n.s.a. (YoY) (JUL)", LocalDateTime.parse("06-09-2016 06:00:00", Format.dateTimeFormatter), DailyFXNewsDataProvider.class.toString(), "", new Symbol("EUR")));
                add(new News("CHF Consumer Price Index (MoM) (AUG)", LocalDateTime.parse("06-09-2016 07:15:00", Format.dateTimeFormatter), DailyFXNewsDataProvider.class.toString(), "", new Symbol("CHF")));
                add(new News("CHF Consumer Price Index (YoY) (AUG)", LocalDateTime.parse("06-09-2016 07:15:00", Format.dateTimeFormatter), DailyFXNewsDataProvider.class.toString(), "", new Symbol("CHF")));
                add(new News("CHF Consumer Price Index (YoY) (AUG)", LocalDateTime.parse("06-09-2016 07:15:00", Format.dateTimeFormatter), DailyFXNewsDataProvider.class.toString(), "", new Symbol("CHF")));
                add(new News("CHF CPI EU Harmonized (MoM) (AUG)", LocalDateTime.parse("06-09-2016 07:15:00", Format.dateTimeFormatter), DailyFXNewsDataProvider.class.toString(), "", new Symbol("CHF")));
                add(new News("CHF CPI EU Harmonized (YoY) (AUG)", LocalDateTime.parse("06-09-2016 07:15:00", Format.dateTimeFormatter), DailyFXNewsDataProvider.class.toString(), "", new Symbol("CHF")));
            }
        };

//TODO mocks

        List<News> result = newsDataProvider.getNewsInDateTimeRange(LocalDateTime.parse("06-09-2016 06:00:00", Format.dateTimeFormatter), LocalDateTime.parse("06-09-2016 08:00:00", Format.dateTimeFormatter));

        assertTrue(result.size() == expectedResult.size());

        for(int i = 0; i < result.size(); i++){
            assertEquals(expectedResult.get(i).getTitle(), result.get(i).getTitle());
            assertEquals(expectedResult.get(i).getDateTime(), result.get(i).getDateTime());
            assertEquals(expectedResult.get(i).getDataProviderClass(), result.get(i).getDataProviderClass());
            assertEquals(expectedResult.get(i).getUrl(), result.get(i).getUrl());
            assertEquals(expectedResult.get(i).getSymbol().getId(), result.get(i).getSymbol().getId());
        }
    }
}