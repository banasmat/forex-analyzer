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

public class TrendFinderTest {

	private TrendFinder trendFinder;
	
	@Before
	public void setUp() throws Exception {
		trendFinder = new TrendFinder();
	}

	@Test
 	public void shouldFindTrendStartInGivenData() throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
		
		LinkedHashMap<Date, Double> data = new LinkedHashMap<Date, Double>(){
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
		};
		
		trendFinder.setMinDifference(0.15);
		
		assertEquals(dateFormat.parse("03/01/1990"), trendFinder.findTrendStart(data));
	}
}
