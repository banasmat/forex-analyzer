package com.jorm.forex.service;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class TrendFinderTest {

	private TrendFinder trendFinder;
	
	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Before
	public void setUp() {
		trendFinder = new TrendFinder();
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
	public void shouldThrowException_GivenThatMinDifferenceIsNotSet(LinkedHashMap<Date, Double> data, Double minDifference, Date result){
		trendFinder.findTrendStart(data);
	}
	
	@Test
	@UseDataProvider("dataProviderTrendStart")
 	public void shouldFindTrendStart_GivenThanMinDifferenceIsSufficient(LinkedHashMap<Date, Double> data, Double minDifference, Date result){
		trendFinder.setMinStartDifference(minDifference);
		assertEquals(result, trendFinder.findTrendStart(data));
	}
	
	@Test
	@UseDataProvider("dataProviderTrendStartNotExists")
	public void shouldNotFindTrendStart_GivenThanMinDifferenceIsNotSufficient(LinkedHashMap<Date, Double> data, Double minDifference){
		trendFinder.setMinStartDifference(minDifference);
		assertNull(trendFinder.findTrendStart(data));
	}

	@Test
	@UseDataProvider("dataProviderTrendEnd")
	public void shouldFindTrendEnd_GivenThanMinDifferenceIsSufficient(LinkedHashMap<Date, Double> data, Double minDifference, Date result){
		trendFinder.setMinEndDifference(minDifference);
		assertEquals(result, trendFinder.findTrendEnd(data));
	}
	
	@DataProvider
	public static Object[][] dataProviderTrendStart() throws ParseException{
		
		return new Object[][]{
			{
				new LinkedHashMap<Date, Double>(){
					{
						put(dateFormat.parse("01/01/1990"), 1.12);
						put(dateFormat.parse("02/01/1990"), 1.08);
						put(dateFormat.parse("03/01/1990"), 1.06);
						put(dateFormat.parse("04/01/1990"), 1.10);
						put(dateFormat.parse("05/01/1990"), 1.16);
						put(dateFormat.parse("06/01/1990"), 1.14);
						put(dateFormat.parse("07/01/1990"), 1.12);
						put(dateFormat.parse("08/01/1990"), 1.16);
						put(dateFormat.parse("09/01/1990"), 1.20);
						put(dateFormat.parse("10/01/1990"), 1.22);
					}
				},
				0.15,
				dateFormat.parse("03/01/1990")
			},
			{
				new LinkedHashMap<Date, Double>(){
					{
						put(dateFormat.parse("01/01/1990"), 2.00);
						put(dateFormat.parse("02/01/1990"), 2.05);
						put(dateFormat.parse("03/01/1990"), 2.06);
						put(dateFormat.parse("04/01/1990"), 2.00);
						put(dateFormat.parse("05/01/1990"), 1.96);
						put(dateFormat.parse("06/01/1990"), 1.85);
						put(dateFormat.parse("07/01/1990"), 1.89);
						put(dateFormat.parse("08/01/1990"), 1.82);
						put(dateFormat.parse("09/01/1990"), 1.65);
						put(dateFormat.parse("10/01/1990"), 1.90);
					}
				},
				0.2,
				dateFormat.parse("03/01/1990")
			}
		};
	}
	
	@DataProvider
	public static Object[][] dataProviderTrendStartNotExists() throws ParseException{
		
		return new Object[][]{
			{
				new LinkedHashMap<Date, Double>(){
					{
						put(dateFormat.parse("01/01/1990"), 1.12);
						put(dateFormat.parse("02/01/1990"), 1.08);
						put(dateFormat.parse("03/01/1990"), 1.06);
						put(dateFormat.parse("04/01/1990"), 1.10);
						put(dateFormat.parse("05/01/1990"), 1.16);
						put(dateFormat.parse("06/01/1990"), 1.14);
						put(dateFormat.parse("07/01/1990"), 1.12);
						put(dateFormat.parse("08/01/1990"), 1.16);
						put(dateFormat.parse("09/01/1990"), 1.20);
						put(dateFormat.parse("10/01/1990"), 1.22);
					}
				},
				1d
			},
			{
				new LinkedHashMap<Date, Double>(){
					{
						put(dateFormat.parse("01/01/1990"), 2.00);
						put(dateFormat.parse("02/01/1990"), 2.05);
						put(dateFormat.parse("03/01/1990"), 2.06);
						put(dateFormat.parse("04/01/1990"), 2.00);
						put(dateFormat.parse("05/01/1990"), 1.96);
						put(dateFormat.parse("06/01/1990"), 1.85);
						put(dateFormat.parse("07/01/1990"), 1.89);
						put(dateFormat.parse("08/01/1990"), 1.82);
						put(dateFormat.parse("09/01/1990"), 1.65);
						put(dateFormat.parse("10/01/1990"), 1.90);
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
				new LinkedHashMap<Date, Double>(){
					{
						put(dateFormat.parse("03/01/1990"), 1.06);
						put(dateFormat.parse("04/01/1990"), 1.10);
						put(dateFormat.parse("05/01/1990"), 1.16);
						put(dateFormat.parse("06/01/1990"), 1.14);
						put(dateFormat.parse("07/01/1990"), 1.12);
						put(dateFormat.parse("08/01/1990"), 1.16);
						put(dateFormat.parse("09/01/1990"), 1.20);
						put(dateFormat.parse("10/01/1990"), 1.22);
						put(dateFormat.parse("11/01/1990"), 1.18);
						put(dateFormat.parse("12/01/1990"), 1.15);
						put(dateFormat.parse("13/01/1990"), 1.16);
						put(dateFormat.parse("14/01/1990"), 1.10);
						put(dateFormat.parse("15/01/1990"), 1.05);
					}
				},
				0.15,
				dateFormat.parse("10/01/1990")
			},
			{
				new LinkedHashMap<Date, Double>(){
					{
						put(dateFormat.parse("03/01/1990"), 2.06);
						put(dateFormat.parse("04/01/1990"), 2.00);
						put(dateFormat.parse("05/01/1990"), 1.96);
						put(dateFormat.parse("06/01/1990"), 1.85);
						put(dateFormat.parse("07/01/1990"), 1.89);
						put(dateFormat.parse("08/01/1990"), 1.82);
						put(dateFormat.parse("09/01/1990"), 1.65);
						put(dateFormat.parse("10/01/1990"), 1.90);
						put(dateFormat.parse("11/01/1990"), 1.95);
						put(dateFormat.parse("12/01/1990"), 1.85);
					}
				},
				0.2,
				dateFormat.parse("09/01/1990")
			}
		};
	}
	
}
