package com.jorm.forex.trend;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		
		LinkedHashMap<LocalDateTime, Double> data = new LinkedHashMap<LocalDateTime, Double>();
		
		//TODO use data provider;
		LocalDateTime date1 = LocalDateTime.parse("02.04.1234 00:00:00", dateFormat);
		LocalDateTime date2 = LocalDateTime.parse("06.08.2032 00:00:00", dateFormat);
		
		when(trendFinder.findTrendStart(data)).thenReturn(date1);
		
		when(trendFinder.findTrendEnd(data)).thenReturn(date2);
		
		assertEquals(date1, trendExtractor.extractTrends(data)[0].getStart());
		assertEquals(date2, trendExtractor.extractTrends(data)[0].getEnd());
				
	}
}
