package com.jorm.forex.trend;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import financial_data.DailyDataProvider;
import model.Trend;

public class TrendExtractor {

	private TrendFinder trendFinder;
	
	TrendExtractor(TrendFinder trendFinder){
		this.trendFinder = trendFinder;
	}
	
	public Trend[] extractTrends(LinkedHashMap<LocalDateTime, Double> data){
		
		LocalDateTime startDate = trendFinder.findTrendStart(data);
		
		return null;
		
	}
}
