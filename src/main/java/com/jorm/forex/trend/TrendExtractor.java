package com.jorm.forex.trend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import financial_data.DailyDataProvider;
import model.Trend;

public class TrendExtractor {

	private TrendFinder trendFinder;
	
	TrendExtractor(TrendFinder trendFinder){
		this.trendFinder = trendFinder;
	}
	
	
	public ArrayList<Trend> extractTrends(LinkedHashMap<LocalDateTime, Double> data){
	
		ArrayList<Trend> extractedTrends = new ArrayList<Trend>();
		
		
		//fixme probably should be moved to recursive method
		
		LocalDateTime startDate = trendFinder.findTrendStart(data);
		
		if (null != startDate) {

			clearAllEntriesBeforeDate(data, startDate);
			
			LocalDateTime endDate = trendFinder.findTrendEnd(data);
			
			if (null != endDate) {
				clearAllEntriesBeforeDate(data, endDate);
				
				Trend trend = new Trend();
				
				trend.setStart(startDate);
				trend.setEnd(endDate);
				
				extractedTrends.add(trend);
			}
			
		}
		
		return extractedTrends;
		
	}
	
	private void clearAllEntriesBeforeDate(LinkedHashMap<LocalDateTime, Double> data, LocalDateTime breakpoint){
		for (Iterator<Map.Entry<LocalDateTime, Double>> it = data.entrySet().iterator(); it.hasNext();) {
			Map.Entry<LocalDateTime, Double> entry = it.next();
			if (entry.getKey() != breakpoint) {
				it.remove();
			} else {
				break;
			}
		}
	}
}
