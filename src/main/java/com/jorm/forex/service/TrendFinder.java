package com.jorm.forex.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrendFinder {

	private Double minStartDifference = null;
	private Double minEndDifference = null;
	
	//TODO consider using Map in the interface (client classes should make sure the order is ok)
	public Date findTrendStart(LinkedHashMap<Date, Double> data) {
		
		if(null == minStartDifference){
			throw new RuntimeException("Please set minDifference");
		}
		
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
						
			if((max - min) >= minStartDifference){
				// Return earlier date
				result = minDate.before(maxDate) ? minDate : maxDate;
				break;
			}
		}
		
		return result;
	}

	public Object findTrendEnd(LinkedHashMap<Date, Double> data) {
		
		if(null == minEndDifference){
			throw new RuntimeException("Please set minDifference");
		}
		
		Date result = null;
		
		Double min = null;
		Double max = null;
		Double current;
		
		Date minDate = null;
		Date maxDate = null;
		
		Boolean isUpwards = null;
		
		Double previousValue = null;
		
		for(Map.Entry<Date, Double> entry : data.entrySet()){
			current = entry.getValue();

			if(null == previousValue){
				previousValue = current;
			} else if (null == isUpwards){
				if(current > previousValue){
					isUpwards = true;
				} else if(current < previousValue) {
					isUpwards = false;
				}
			}
			
			if (null == min || current < min){
				min = current;
				minDate = entry.getKey();
			}
			
			if (null == max || current > max){
				max = current;
				maxDate = entry.getKey();
			}
						
			if(null != isUpwards){
				if(true == isUpwards && (max - current) >= minEndDifference){
					result = maxDate;
					break;
				} else if(false == isUpwards && -(min - current) >= minEndDifference){
					result = minDate;
					break;
				}
			}
		}
		
		return result;
	}

	//TODO not sure if these should be separated
	public void setMinStartDifference(Double d) {
		
		if(null != minStartDifference){
			throw new RuntimeException("minStartDifference has already been set to: " + minStartDifference.toString());
		}
		
		minStartDifference = d;
	}

	public void setMinEndDifference(Double d) {
		if(null != minEndDifference){
			throw new RuntimeException("minEndDifference has already been set to: " + minStartDifference.toString());
		}
		
		minEndDifference = d;
		
	}
	
}
