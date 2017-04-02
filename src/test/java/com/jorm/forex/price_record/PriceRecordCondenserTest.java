package com.jorm.forex.price_record;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.util.Format;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)
public class PriceRecordCondenserTest {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormat;

    private PriceRecordCondenser priceRecordCondenser;

    @Before
    public void setUp() {
        priceRecordCondenser = new PriceRecordCondenser();
    }

    //TODO might separate all test names with underscore to increase readability
    @Test
    @UseDataProvider("dataProviderCondense")
    public void shouldCondensePriceRecordsForIntervalGivenThatSomeMinuteRecordsAreMissing(List<PriceRecord> inputData, List<PriceRecord> expectedResult, Interval interval){

        //FIXME improve test, test different intervals

        List<PriceRecord> result = priceRecordCondenser.condense(inputData, interval);

        assertEquals(expectedResult.size(), result.size());

        for(int i = 0; i < expectedResult.size(); i++){
            assertEquals(expectedResult.get(i).getDateTime(), result.get(i).getDateTime());
            assertEquals(expectedResult.get(i).getOpen(), result.get(i).getOpen());
            assertEquals(expectedResult.get(i).getHigh(), result.get(i).getHigh());
            assertEquals(expectedResult.get(i).getLow(), result.get(i).getLow());
            assertEquals(expectedResult.get(i).getClose(), result.get(i).getClose());
        }
    }

    @DataProvider
    public static Object[][] dataProviderCondense() throws ParseException {
        return new Object[][]{
            {
                new ArrayList<PriceRecord>(){
                    {
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:01:00", dateFormat), 1.5D, 1D, 1D, 0.6));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:02:00", dateFormat), 1D, 3D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:03:00", dateFormat), 1D, 1.2, 0.5, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:04:00", dateFormat), 1D, 1D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:05:00", dateFormat), 1D, 1D, 1D, 2D));

                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:06:00", dateFormat), 2.5D, 4D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:07:00", dateFormat), 1D, 1D, 1D, 1D));
                        //add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:08:00", dateFormat), 1D, 1D, 1D, 1D)); Some 'minutes' might be missing
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:09:00", dateFormat), 1D, 1D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:10:00", dateFormat), 1D, 1D, 0.4, 1D));

                        //add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:11:00", dateFormat), 1D, 1D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:12:00", dateFormat), 1D, 1D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:13:00", dateFormat), 1D, 1D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:14:00", dateFormat), 1D, 1D, 1D, 1D));
                        //add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:15:00", dateFormat), 1D, 1D, 1D, 1D));

                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:16:00", dateFormat), 0.9, 4D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:17:00", dateFormat), 1D, 1D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:18:00", dateFormat), 1D, 1D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:19:00", dateFormat), 1D, 1D, 0.3, 2.4));
                        //add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:20:00", dateFormat), 1D, 1D, 1D, 1D));

                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:21:00", dateFormat), 3D, 6D, 1D, 4D));
                        //add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:22:00", dateFormat), 3D, 6D, 1D, 4D));
                    }
                },
                new ArrayList<PriceRecord>() {
                    {
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:05:00", dateFormat), 1.5D, 3D, 0.5, 2D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:10:00", dateFormat), 2.5D, 4D, 0.4, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:15:00", dateFormat), 1D, 1D, 1D, 1D));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:20:00", dateFormat), 0.9, 4D, 0.3, 2.4));
                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:25:00", dateFormat), 3D, 6D, 1D, 4D));
                    }
                },
                Interval.FIVE_MINUTES
            },
//            {
//                new ArrayList<PriceRecord>(){
//                    {
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 02:06:00", dateFormat), 1.5D, 1D, 1D, 0.6));
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 08:24:00", dateFormat), 1D, 3D, 1D, 1D));
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 09:05:00", dateFormat), 1D, 1.2, 0.5, 1D));
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 11:10:00", dateFormat), 1D, 1D, 1D, 1D));
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 12:05:00", dateFormat), 1D, 1D, 1D, 2D));
//
//
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:11:00", dateFormat), 1D, 1D, 1D, 1D));
//                        add(new PriceRecord(LocalDateTime.parse("03-04-1234 00:12:00", dateFormat), 1D, 1D, 1D, 1D));
//                        add(new PriceRecord(LocalDateTime.parse("03-04-1234 00:13:00", dateFormat), 1D, 1D, 1D, 1D));
//                        add(new PriceRecord(LocalDateTime.parse("03-04-1234 00:14:00", dateFormat), 1D, 1D, 1D, 1D));
//
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:16:00", dateFormat), 0.9, 4D, 1D, 1D));
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:17:00", dateFormat), 1D, 1D, 1D, 1D));
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:18:00", dateFormat), 1D, 1D, 1D, 1D));
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:19:00", dateFormat), 1D, 1D, 0.3, 2.4));
//
//                        add(new PriceRecord(LocalDateTime.parse("05-04-1234 00:21:00", dateFormat), 3D, 6D, 1D, 4D));
//                        add(new PriceRecord(LocalDateTime.parse("05-04-1234 00:22:00", dateFormat), 3D, 6D, 1D, 4D));
//                    }
//                },
//                new ArrayList<PriceRecord>() {
//                    {
//                        add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:05:00", dateFormat), 1.5D, 3D, 0.5, 2D));
//
//                        add(new PriceRecord(LocalDateTime.parse("03-04-1234 00:15:00", dateFormat), 1D, 1D, 1D, 1D));
//                        add(new PriceRecord(LocalDateTime.parse("04-04-1234 00:20:00", dateFormat), 0.9, 4D, 0.3, 2.4));
//                        add(new PriceRecord(LocalDateTime.parse("05-04-1234 00:25:00", dateFormat), 3D, 6D, 1D, 4D)); //TODO we may want it ending at 00:25
//                    }
//                },
//                Interval.ONE_DAY
//            }
        };
    }
}