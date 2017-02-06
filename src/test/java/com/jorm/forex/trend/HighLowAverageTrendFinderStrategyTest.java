package com.jorm.forex.trend;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.SortedMap;
import java.util.TreeMap;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.TrendFinderSettings;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jorm.forex.util.Format;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class HighLowAverageTrendFinderStrategyTest {

    private static DateTimeFormatter dateFormat = Format.dateTimeFormat;

    private HighLowAverageTrendFinderStrategy trendFinder;

    @Test
    @UseDataProvider("dataProviderTrendStart")
    public void shouldFindTrendStart_GivenThanMinDifferenceIsSufficient(SortedMap<LocalDateTime, PriceRecord> data, Double minDifference, LocalDateTime result){
        trendFinder = new HighLowAverageTrendFinderStrategy(new TrendFinderSettings(minDifference));
        assertEquals(result, trendFinder.findTrendStart(data));
    }

    @Test
    @UseDataProvider("dataProviderTrendStartNotExists")
    public void shouldNotFindTrendStart_GivenThanMinDifferenceIsNotSufficient(SortedMap<LocalDateTime, PriceRecord> data, Double minDifference){
        trendFinder = new HighLowAverageTrendFinderStrategy(new TrendFinderSettings(minDifference));
        assertNull(trendFinder.findTrendStart(data));
    }

    @Test
    @UseDataProvider("dataProviderTrendEnd")
    public void shouldFindTrendEnd_GivenThanMinDifferenceIsSufficient(SortedMap<LocalDateTime, PriceRecord> data, Double minDifference, LocalDateTime result){
        trendFinder = new HighLowAverageTrendFinderStrategy(new TrendFinderSettings(minDifference));
        assertEquals(result, trendFinder.findTrendEnd(data));
    }

    @DataProvider
    public static Object[][] dataProviderTrendStart() throws ParseException{

        return new Object[][]{
            {
                new TreeMap<LocalDateTime, PriceRecord>(){
                    {
                        put(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.15, 1.10, 1D));
                        put(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.09, 1.07, 1D));
                        put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.10, 1.02, 1D));
                        put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.20, 1.01, 1D));
                        put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.24, 1.08, 1D));
                        put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.18, 1.10, 1D));
                        put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.12, 1.12, 1D));
                        put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.24, 1.10, 1D));
                        put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.41, 1.00, 1D));
                        put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.24, 1.20, 1D));
                    }
                },
                0.15,
                LocalDateTime.parse("03-01-1990 00:00:00", dateFormat)
            },
            {
                new TreeMap<LocalDateTime, PriceRecord>(){
                    {
                        put(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 3.00, 1.01, 1D));
                        put(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 2.15, 1.95, 1D));
                        put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 2.09, 2.04, 1D));
                        put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 2.00, 2.00, 1D));
                        put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.97, 1.95, 1D));
                        put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.88, 1.83, 1D));
                        put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.90, 1.88, 1D));
                        put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.82, 1.82, 1D));
                        put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.71, 1.60, 1D));
                        put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.94, 1.86, 1D));
                    }
                },
                0.2,
                LocalDateTime.parse("03-01-1990 00:00:00", dateFormat)
            }
        };
    }

    @DataProvider
    public static Object[][] dataProviderTrendStartNotExists() throws ParseException{

        return new Object[][]{
            {
                new TreeMap<LocalDateTime, PriceRecord>(){
                    {
                        put(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.14, 1.11, 1D));
                        put(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.09, 1.08, 1D));
                        put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.12, 1.00, 1D));
                        put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.15, 1.05, 1D));
                        put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.18, 1.14, 1D));
                        put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.21, 1.08, 1D));
                        put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.18, 1.06, 1D));
                        put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.24, 1.10, 1D));
                        put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.50, 0.91, 1D));
                        put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.24, 1.20, 1D));
                    }
                },
                1d
            },
            {
                new TreeMap<LocalDateTime, PriceRecord>(){
                    {
                        put(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 3.00, 1.00, 1D));
                        put(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 2.06, 2.05, 1D));
                        put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 2.16, 1.96, 1D));
                        put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 2.50, 1.50, 1D));
                        put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.97, 1.95, 1D));
                        put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.85, 1.85, 1D));
                        put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.89, 1.89, 1D));
                        put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.82, 1.82, 1D));
                        put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.65, 1.65, 1D));
                        put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.90, 1.90, 1D));
                    }
                },
                1d
            }
        };
    }

    @DataProvider
    public static Object[][] dataProviderTrendEnd() throws ParseException{

        return new Object[][]{
            {
                new TreeMap<LocalDateTime, PriceRecord>(){
                    {
                        put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.12, 1.00, 1D));
                        put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.08, 1.12, 1D));
                        put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.22, 1.10, 1D));
                        put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.07, 1.21, 1D));
                        put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.15, 1.12, 1D));
                        put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.16, 1.16, 1D));
                        put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.21, 1.19, 1D));
                        put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.24, 1.21, 1D));
                        put(LocalDateTime.parse("11-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.20, 1.16, 1D));
                        put(LocalDateTime.parse("12-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.25, 1.05, 1D));
                        put(LocalDateTime.parse("13-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.19, 1.13, 1D));
                        put(LocalDateTime.parse("14-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.18, 1.02, 1D));
                        put(LocalDateTime.parse("15-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.10, 1.00, 1D));
                    }
                },
                0.15,
                LocalDateTime.parse("10-01-1990 00:00:00", dateFormat)
            },
            {
                new TreeMap<LocalDateTime, PriceRecord>(){
                    {
                        put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 2.16, 1.96, 1D));
                        put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 3.50, 0.50, 1D));
                        put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.98, 1.94, 1D));
                        put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.86, 1.85, 1D));
                        put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.97, 1.81, 1D));
                        put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.83, 1.81, 1D));
                        put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.70, 1.61, 1D));
                        put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.92, 1.88, 1D));
                        put(LocalDateTime.parse("11-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.97, 1.94, 1D));
                        put(LocalDateTime.parse("12-01-1990 00:00:00", dateFormat), new PriceRecord(1D, 1.89, 1.80, 1D));
                    }
                },
                0.2,
                LocalDateTime.parse("09-01-1990 00:00:00", dateFormat)
            }
        };
    }

}
