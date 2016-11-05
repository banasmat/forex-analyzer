package com.jorm.forex.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrendFinder {

	private Double minDifference = 1.0;
	
	//TODO consider using Map in the interface (client classes should make sure the order is ok)
	public Date findTrendStart(LinkedHashMap<Date, Double> data) {
		
		Date result = null;
		
		Double min = null;
		Double max = null;
		Double current;
		
		Date minDate = null;
		Date maxDate = null;
		
		for(Map.Entry<Date, Double> entry : data.entrySet()){
			current = entry.getValue();

			if (null == min || current < min){
				min = current;
				minDate = entry.getKey();
			}
			
			if (null == max || current > max){
				max = current;
				maxDate = entry.getKey();
			}
						
			if((max - min) >= minDifference){
				// Return earlier date
				result = minDate.before(maxDate) ? minDate : maxDate;
				break;
			}
		}
		
		return result;
	}

	public void setMinDifference(double d) {
		
		//TODO this probably shouldn't be changable between findStart and findEnd
		
		minDifference = d;
	}
	

	
}
