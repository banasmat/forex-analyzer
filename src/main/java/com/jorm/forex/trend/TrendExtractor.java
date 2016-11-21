package com.jorm.forex.trend;

import financial_data.DailyDataProvider;
import model.Trend;

public class TrendExtractor {

	private TrendFinder trendFinder;
	
	private DailyDataProvider dataProvider;
	
	TrendExtractor(DailyDataProvider dataProvider, TrendFinder trendFinder){
		this.trendFinder = trendFinder;
		this.dataProvider = dataProvider;
	}
	
	public Trend[] extractTrends(){
		return null;
		
	}
}
