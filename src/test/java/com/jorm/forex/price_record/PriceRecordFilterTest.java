package com.jorm.forex.price_record;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.util.Format;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PriceRecordFilterTest {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormat;

    private PriceRecordFilter priceRecordFilter;

    @Before
    public void setUp() {
        priceRecordFilter = new PriceRecordFilter();
    }

    //TODO might separate all test names with underscore to increase readability
    @Test
    public void shouldGetOpenPriceFromFirstClosePriceFromLastAndHighLowFromHighestLowestForGivenInterval(){

        List<PriceRecord> data = new ArrayList<PriceRecord>(){
            {
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:01:00", dateFormat), 1.5D, 1D, 1D, 0.6));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:02:00", dateFormat), 1D, 3D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:03:00", dateFormat), 1D, 1.2, 0.5, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:04:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:05:00", dateFormat), 1D, 1D, 1D, 2D));

                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:06:00", dateFormat), 2.5D, 4D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:07:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:08:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:09:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:10:00", dateFormat), 1D, 1D, 0.4, 1D));

                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:11:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:12:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:13:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:14:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:15:00", dateFormat), 1D, 1D, 1D, 1D));

                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:16:00", dateFormat), 0.9, 4D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:17:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:18:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:19:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:20:00", dateFormat), 1D, 1D, 0.3, 2.4));

                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:21:00", dateFormat), 3D, 6D, 1D, 4D));
            }
        };

        List<PriceRecord> expectedResult = new ArrayList<PriceRecord>(){
            {
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:05:00", dateFormat), 1.5D, 3D, 0.5, 2D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:10:00", dateFormat), 2.5D, 4D, 0.4, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:15:00", dateFormat), 1D, 1D, 1D, 1D));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:20:00", dateFormat), 0.9, 4D, 0.3, 2.4));
                add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:21:00", dateFormat), 3D, 6D, 1D, 4D));
            }
        };

        List<PriceRecord> result = priceRecordFilter.filter(data, 5);

        assertEquals(expectedResult.size(), result.size());

        for(int i = 0; i < expectedResult.size(); i++){
            assertEquals(expectedResult.get(i).getDateTime(), result.get(i).getDateTime());
            assertEquals(expectedResult.get(i).getOpen(), result.get(i).getOpen());
            assertEquals(expectedResult.get(i).getHigh(), result.get(i).getHigh());
            assertEquals(expectedResult.get(i).getLow(), result.get(i).getLow());
            assertEquals(expectedResult.get(i).getClose(), result.get(i).getClose());
        }
    }
}