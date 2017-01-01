package com.jorm.forex.trend;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jorm.forex.trend.DefaultTrendFinder;
import com.jorm.forex.util.Format;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class DefaultTrendFinderTest {

	private static DateTimeFormatter dateFormat = Format.dateTimeFormat;
	
	private DefaultTrendFinder trendFinder;
		
	@Before
	public void setUp() {
		trendFinder = new DefaultTrendFinder();
	}

	@Test(expected=RuntimeException.class)
	public void shouldBeAbleToSetMinStartDifferenceOnlyOnce(){
		trendFinder.setMinStartDifference(1d);
		trendFinder.setMinStartDifference(1d);
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldBeAbleToSetMinEndDifferenceOnlyOnce(){
		trendFinder.setMinEndDifference(1d);
		trendFinder.setMinEndDifference(1d);
	}
	
	@Test(expected=RuntimeException.class)
	@UseDataProvider("dataProviderTrendStart")
	public void shouldThrowException_GivenThatMinDifferenceIsNotSet(LinkedHashMap<LocalDateTime, Double> data, Double minDifference, LocalDateTime result){
		trendFinder.findTrendStart(data);
	}
	
	@Test
	@UseDataProvider("dataProviderTrendStart")
 	public void shouldFindTrendStart_GivenThanMinDifferenceIsSufficient(LinkedHashMap<LocalDateTime, Double> data, Double minDifference, LocalDateTime result){
		trendFinder.setMinStartDifference(minDifference);
		assertEquals(result, trendFinder.findTrendStart(data));
	}
	
	@Test
	@UseDataProvider("dataProviderTrendStartNotExists")
	public void shouldNotFindTrendStart_GivenThanMinDifferenceIsNotSufficient(LinkedHashMap<LocalDateTime, Double> data, Double minDifference){
		trendFinder.setMinStartDifference(minDifference);
		assertNull(trendFinder.findTrendStart(data));
	}

	@Test
	@UseDataProvider("dataProviderTrendEnd")
	public void shouldFindTrendEnd_GivenThanMinDifferenceIsSufficient(LinkedHashMap<LocalDateTime, Double> data, Double minDifference, LocalDateTime result){
		trendFinder.setMinEndDifference(minDifference);
		assertEquals(result, trendFinder.findTrendEnd(data));
	}
	
	@DataProvider
	public static Object[][] dataProviderTrendStart() throws ParseException{
		
		return new Object[][]{
			{
				new LinkedHashMap<LocalDateTime, Double>(){
					{
						put(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), 1.12);
						put(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), 1.08);
						put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 1.06);
						put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 1.10);
						put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1.16);
						put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1.14);
						put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1.12);
						put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1.16);
						put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1.20);
						put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1.22);
					}
				},
				0.15,
				LocalDateTime.parse("03-01-1990 00:00:00", dateFormat)
			},
			{
				new LinkedHashMap<LocalDateTime, Double>(){
					{
						put(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), 2.00);
						put(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), 2.05);
						put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 2.06);
						put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 2.00);
						put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1.96);
						put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1.85);
						put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1.89);
						put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1.82);
						put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1.65);
						put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1.90);
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
				new LinkedHashMap<LocalDateTime, Double>(){
					{
						put(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), 1.12);
						put(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), 1.08);
						put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 1.06);
						put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 1.10);
						put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1.16);
						put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1.14);
						put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1.12);
						put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1.16);
						put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1.20);
						put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1.22);
					}
				},
				1d
			},
			{
				new LinkedHashMap<LocalDateTime, Double>(){
					{
						put(LocalDateTime.parse("01-01-1990 00:00:00", dateFormat), 2.00);
						put(LocalDateTime.parse("02-01-1990 00:00:00", dateFormat), 2.05);
						put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 2.06);
						put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 2.00);
						put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1.96);
						put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1.85);
						put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1.89);
						put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1.82);
						put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1.65);
						put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1.90);
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
				new LinkedHashMap<LocalDateTime, Double>(){
					{
						put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 1.06);
						put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 1.10);
						put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1.16);
						put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1.14);
						put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1.12);
						put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1.16);
						put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1.20);
						put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1.22);
						put(LocalDateTime.parse("11-01-1990 00:00:00", dateFormat), 1.18);
						put(LocalDateTime.parse("12-01-1990 00:00:00", dateFormat), 1.15);
						put(LocalDateTime.parse("13-01-1990 00:00:00", dateFormat), 1.16);
						put(LocalDateTime.parse("14-01-1990 00:00:00", dateFormat), 1.10);
						put(LocalDateTime.parse("15-01-1990 00:00:00", dateFormat), 1.05);
					}
				},
				0.15,
				LocalDateTime.parse("10-01-1990 00:00:00", dateFormat)
			},
			{
				new LinkedHashMap<LocalDateTime, Double>(){
					{
						put(LocalDateTime.parse("03-01-1990 00:00:00", dateFormat), 2.06);
						put(LocalDateTime.parse("04-01-1990 00:00:00", dateFormat), 2.00);
						put(LocalDateTime.parse("05-01-1990 00:00:00", dateFormat), 1.96);
						put(LocalDateTime.parse("06-01-1990 00:00:00", dateFormat), 1.85);
						put(LocalDateTime.parse("07-01-1990 00:00:00", dateFormat), 1.89);
						put(LocalDateTime.parse("08-01-1990 00:00:00", dateFormat), 1.82);
						put(LocalDateTime.parse("09-01-1990 00:00:00", dateFormat), 1.65);
						put(LocalDateTime.parse("10-01-1990 00:00:00", dateFormat), 1.90);
						put(LocalDateTime.parse("11-01-1990 00:00:00", dateFormat), 1.95);
						put(LocalDateTime.parse("12-01-1990 00:00:00", dateFormat), 1.85);
					}
				},
				0.2,
				LocalDateTime.parse("09-01-1990 00:00:00", dateFormat)
			}
		};
	}
	
}
