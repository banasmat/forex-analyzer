package com.jorm.forex.trend;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.jorm.forex.model.PriceRecord;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.jorm.forex.util.Format;

import com.jorm.forex.model.Trend;

public class TrendFinderProcessorTest {

    private static DateTimeFormatter dateFormat = Format.dateTimeFormat;

    private TrendFinderProcessor trendFinderProcessor;

    @Mock
    private TrendFinderStrategy trendFinderStrategy;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        trendFinderProcessor = new TrendFinderProcessor();
        trendFinderProcessor.setTrendFinderStrategy(trendFinderStrategy);
    }

    @Test
    public void shouldFindTrendsInGivenData() throws ParseException{

        List<PriceRecord> data = new ArrayList<PriceRecord>(){
            {
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("02-04-1234 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("03-04-1234 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("04-04-1234 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("06-07-1234 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("06-08-1234 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("09-09-1234 00:00:00", dateFormat), 1D, 1D, 1D, 1D));

                add(new PriceRecord(LocalDateTime.parse("03-05-1235 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("04-05-1235 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("05-05-1235 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("08-07-1235 00:00:00", dateFormat), 1D, 1D, 1D, 1D));

                add(new PriceRecord(LocalDateTime.parse("02-04-1236 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("03-04-1236 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("04-04-1236 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("05-04-1236 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("06-04-1236 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("07-04-1236 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("02-06-1236 00:00:00", dateFormat), 1D, 1D, 1D, 1D));
            }
        };


        LocalDateTime[] startDates = new LocalDateTime[]{
            LocalDateTime.parse("02-04-1234 00:00:00", dateFormat),
            LocalDateTime.parse("03-05-1235 00:00:00", dateFormat),
            LocalDateTime.parse("02-04-1236 00:00:00", dateFormat)
        };

        LocalDateTime[] endDates = new LocalDateTime[]{
            LocalDateTime.parse("06-08-1234 00:00:00", dateFormat),
            LocalDateTime.parse("08-07-1235 00:00:00", dateFormat),
            LocalDateTime.parse("02-06-1236 00:00:00", dateFormat)
        };

        //TODO might look for a way to put in a loop
        when(trendFinderStrategy.findTrendStart(data))
            .thenReturn(new PriceRecord(startDates[0], 1D, 1D, 1D, 1D))
            .thenReturn(new PriceRecord(startDates[1], 1D, 1D, 1D, 1D))
            .thenReturn(new PriceRecord(startDates[2], 1D, 1D, 1D, 1D))
            .thenReturn(null);
        when(trendFinderStrategy.findTrendEnd(data))
            .thenReturn(new PriceRecord(endDates[0], 1D, 1D, 1D, 1D))
            .thenReturn(new PriceRecord(endDates[1], 1D, 1D, 1D, 1D))
            .thenReturn(new PriceRecord(endDates[2], 1D, 1D, 1D, 1D))
            .thenReturn(null);

        List<Trend> resultTrends = trendFinderProcessor.findTrendsInData(data);

        for(int i = 0; i < startDates.length; i++){
            assertEquals(startDates[i], resultTrends.get(i).priceRecords.get(0).dateTime);
            assertEquals(endDates[i], resultTrends.get(i).priceRecords.get(resultTrends.get(i).priceRecords.size() - 1).dateTime);
        }

    }
}
