package com.jorm.forex.trend;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.TrendFinderSettings;
import com.jorm.forex.util.Format;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

//TODO finding trend end is not effective. What if price is not falling after trend end but kept at one level.

@RunWith(DataProviderRunner.class)
public class HighLowTrendFinderStrategyTest {

    private static DateTimeFormatter dateFormat = Format.dateTimeFormatter;

    private HighLowTrendFinderStrategy trendFinder;

    @Test
    @UseDataProvider("dataProviderTrendStart")
    public void shouldFindTrendStart_GivenThanMinDifferenceIsSufficient(List<PriceRecord> data, Double minDifference, LocalDateTime result){
        trendFinder = new HighLowTrendFinderStrategy(new TrendFinderSettings(minDifference));
        assertEquals(result, trendFinder.findTrendStart(data).getDateTime());
    }

    @Test
    @UseDataProvider("dataProviderTrendStartNotExists")
    public void shouldNotFindTrendStart_GivenThanMinDifferenceIsNotSufficient(List<PriceRecord> data, Double minDifference){
        trendFinder = new HighLowTrendFinderStrategy(new TrendFinderSettings(minDifference));
        assertNull(trendFinder.findTrendStart(data));
    }

    //@Test //FIXME implement
    //@UseDataProvider("dataProviderTrendEnd")
    public void shouldFindTrendEnd_GivenThanMinDifferenceIsSufficient(List<PriceRecord> data, Double minDifference, LocalDateTime resultDateTime){
        trendFinder = new HighLowTrendFinderStrategy(new TrendFinderSettings(minDifference));
        assertEquals(resultDateTime, trendFinder.findTrendEnd(data).getDateTime());
    }

    @DataProvider
    public static Object[][] dataProviderTrendStart() throws ParseException{

        return new Object[][]{
            {
                new ArrayList<PriceRecord>(){
                    {
                        add(new PriceRecord(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), 1D, 1.15, 1.10, 1D));
                        add(new PriceRecord(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), 1D, 1.09, 1.07, 1D));
                        add(new PriceRecord(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 1D, 1.10, 1.02, 1D));
                        add(new PriceRecord(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 1D, 1.20, 1.01, 1D));
                        add(new PriceRecord(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1D, 1.24, 1.08, 1D));
                        add(new PriceRecord(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1D, 1.18, 1.10, 1D));
                        add(new PriceRecord(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1D, 1.12, 1.12, 1D));
                        add(new PriceRecord(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1D, 1.24, 1.10, 1D));
                        add(new PriceRecord(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1D, 1.41, 1.00, 1D));
                        add(new PriceRecord(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1D, 1.24, 1.20, 1D));
                    }
                },
                0.15,
                LocalDateTime.parse("04-01-1990 00:00:00", dateFormat)
            },
            {
                new ArrayList<PriceRecord>(){
                    {
                        add(new PriceRecord(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), 1D, 3.00, 1.01, 1D));
                        add(new PriceRecord(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), 1D, 2.15, 1.95, 1D));
                        add(new PriceRecord(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 1D, 2.09, 2.04, 1D));
                        add(new PriceRecord(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 1D, 2.00, 2.00, 1D));
                        add(new PriceRecord(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1D, 1.97, 1.95, 1D));
                        add(new PriceRecord(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1D, 1.88, 1.83, 1D));
                        add(new PriceRecord(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1D, 1.90, 1.88, 1D));
                        add(new PriceRecord(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1D, 1.82, 1.82, 1D));
                        add(new PriceRecord(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1D, 1.71, 1.60, 1D));
                        add(new PriceRecord(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1D, 1.94, 1.86, 1D));
                    }
                },
                0.2,
                LocalDateTime.parse("01-01-1990 00:00:00", dateFormat)
            }
        };
    }

    @DataProvider
    public static Object[][] dataProviderTrendStartNotExists() throws ParseException{

        return new Object[][]{
            {
                new ArrayList<PriceRecord>(){
                    {
                        add(new PriceRecord(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), 1D, 1.14, 1.11, 1D));
                        add(new PriceRecord(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), 1D, 1.09, 1.08, 1D));
                        add(new PriceRecord(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 1D, 1.12, 1.00, 1D));
                        add(new PriceRecord(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 1D, 1.15, 1.05, 1D));
                        add(new PriceRecord(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1D, 1.18, 1.14, 1D));
                        add(new PriceRecord(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1D, 1.21, 1.08, 1D));
                        add(new PriceRecord(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1D, 1.18, 1.06, 1D));
                        add(new PriceRecord(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1D, 1.24, 1.10, 1D));
                        add(new PriceRecord(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1D, 1.50, 0.91, 1D));
                        add(new PriceRecord(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1D, 1.24, 1.20, 1D));
                    }
                },
                1d
            },
            {
                new ArrayList<PriceRecord>(){
                    {
                        add(new PriceRecord(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), 1D, 2.90, 1.00, 1D));
                        add(new PriceRecord(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), 1D, 2.06, 1.05, 1D));
                        add(new PriceRecord(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 1D, 2.16, 1.96, 1D));
                        add(new PriceRecord(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 1D, 2.50, 1.50, 1D));
                        add(new PriceRecord(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1D, 1.97, 1.95, 1D));
                        add(new PriceRecord(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1D, 1.85, 1.85, 1D));
                        add(new PriceRecord(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1D, 1.89, 1.89, 1D));
                        add(new PriceRecord(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1D, 1.82, 1.82, 1D));
                        add(new PriceRecord(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1D, 1.65, 1.65, 1D));
                        add(new PriceRecord(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1D, 1.90, 1.90, 1D));
                    }
                },
                2d
            }
        };
    }

    @DataProvider
    public static Object[][] dataProviderTrendEnd() throws ParseException{

        return new Object[][]{
            {
                new ArrayList<PriceRecord>(){
                    {
                        add(new PriceRecord(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 1D, 1.12, 1.00, 1D));
                        add(new PriceRecord(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 1D, 1.18, 1.12, 1D));
                        add(new PriceRecord(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1D, 1.22, 1.10, 1D));
                        add(new PriceRecord(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1D, 1.22, 1.21, 1D));
                        add(new PriceRecord(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1D, 1.15, 1.12, 1D));
                        add(new PriceRecord(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1D, 1.16, 1.16, 1D));
                        add(new PriceRecord(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1D, 1.21, 1.19, 1D));
                        add(new PriceRecord(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1D, 1.24, 1.21, 1D));
                        add(new PriceRecord(LocalDateTime.parse("11-01-1990 00:00:00", dateFormat), 1D, 1.20, 1.16, 1D));
                        add(new PriceRecord(LocalDateTime.parse("12-01-1990 00:00:00", dateFormat), 1D, 1.25, 1.05, 1D));
                        add(new PriceRecord(LocalDateTime.parse("13-01-1990 00:00:00", dateFormat), 1D, 1.19, 1.13, 1D));
                        add(new PriceRecord(LocalDateTime.parse("14-01-1990 00:00:00", dateFormat), 1D, 1.18, 1.02, 1D));
                        add(new PriceRecord(LocalDateTime.parse("15-01-1990 00:00:00", dateFormat), 1D, 1.10, 1.00, 1D));
                    }
                },
                0.15,
                LocalDateTime.parse("15-01-1990 00:00:00", dateFormat)
            },
            {
                new ArrayList<PriceRecord>(){
                    {
                        add(new PriceRecord(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 1D, 2.16, 1.96, 1D));
                        add(new PriceRecord(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 1D, 2.00, 2.00, 1D));
                        add(new PriceRecord(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1D, 1.98, 1.94, 1D));
                        add(new PriceRecord(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1D, 1.86, 1.85, 1D));
                        add(new PriceRecord(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1D, 1.97, 1.81, 1D));
                        add(new PriceRecord(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1D, 1.83, 1.81, 1D));
                        add(new PriceRecord(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1D, 1.70, 1.61, 1D));
                        add(new PriceRecord(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1D, 1.92, 1.88, 1D));
                        add(new PriceRecord(LocalDateTime.parse("11-01-1990 00:00:00", dateFormat), 1D, 1.97, 1.94, 1D));
                        add(new PriceRecord(LocalDateTime.parse("12-01-1990 00:00:00", dateFormat), 1D, 1.89, 1.80, 1D));
                    }
                },
                0.2,
                LocalDateTime.parse("09-01-1990 00:00:00", dateFormat)
            }
        };
    }

}
