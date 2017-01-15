package com.jorm.forex.trend;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import com.jorm.forex.model.PriceRecord;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.jorm.forex.util.Format;

import com.jorm.forex.model.Trend;

public class TrendExtractorTest {

    private static DateTimeFormatter dateFormat = Format.dateTimeFormat;

    private TrendExtractor trendExtractor;

    @Mock
    private TrendFinder trendFinder;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        trendExtractor = new TrendExtractor(trendFinder);
    }

    @Test
    public void shouldExtractTrendsFromGivenData() throws ParseException{

        PriceRecord somePrices = new PriceRecord(1D, 1D, 1D, 1D);

        SortedMap<LocalDateTime, PriceRecord> data = new TreeMap<LocalDateTime, PriceRecord>(){
            {
                put(LocalDateTime.parse("01-04-1234 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("02-04-1234 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("03-04-1234 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("04-04-1234 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("06-08-1234 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("06-07-1234 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("09-09-1234 00:00:00", dateFormat), somePrices);

                put(LocalDateTime.parse("03-05-1235 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("04-05-1235 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("05-05-1235 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("08-07-1235 00:00:00", dateFormat), somePrices);

                put(LocalDateTime.parse("02-04-1236 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("03-04-1236 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("04-04-1236 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("05-04-1236 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("06-04-1236 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("07-04-1236 00:00:00", dateFormat), somePrices);
                put(LocalDateTime.parse("02-06-1236 00:00:00", dateFormat), somePrices);
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
        when(trendFinder.findTrendStart(data))
            .thenReturn(startDates[0])
            .thenReturn(startDates[1])
            .thenReturn(startDates[2])
            .thenReturn(null);
        when(trendFinder.findTrendEnd(data))
            .thenReturn(endDates[0])
            .thenReturn(endDates[1])
            .thenReturn(endDates[2])
            .thenReturn(null);

        ArrayList<Trend> resultTrends = trendExtractor.extractTrends(data);

        for(int i = 0; i < startDates.length; i++){
            assertEquals(startDates[i], resultTrends.get(i).start);
            assertEquals(endDates[i], resultTrends.get(i).end);
        }

    }
}
