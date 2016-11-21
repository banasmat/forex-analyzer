package com.jorm.forex.trend;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.jorm.forex.util.Format;

import financial_data.DailyDataProvider;
import model.Trend;

public class TrendExtractorTest {
	
	private static DateFormat dateFormat = Format.dateFormat;
	
	private TrendExtractor trendExtractor;
	
	@Mock
	private TrendFinder trendFinder;
	
	@Mock
	private DailyDataProvider dataProvider;
	
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	public void setUp() {
		trendExtractor = new TrendExtractor(dataProvider, trendFinder);
	}
	
	@Test
	public void shouldExtractTrendsFromGivenData() throws ParseException{
		
		LinkedHashMap<Date, Double> data = new LinkedHashMap<Date, Double>();
		
		//TODO use data provider;
		Date date1 = dateFormat.parse("02/04/1234");
		Date date2 = dateFormat.parse("06/08/2032");
		
		when(trendFinder.findTrendStart(data)).thenReturn(date1);
		
		when(trendFinder.findTrendEnd(data)).thenReturn(date2);
		
		assertEquals(date1, trendExtractor.extractTrends()[0].getStart());
		assertEquals(date2, trendExtractor.extractTrends()[0].getEnd());
				
	}
}
