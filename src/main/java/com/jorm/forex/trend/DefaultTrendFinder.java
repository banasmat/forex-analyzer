package com.jorm.forex.trend;

import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.Map;

public class DefaultTrendFinder implements TrendFinder {

	private TrendFinderSettings settings;

	public DefaultTrendFinder(TrendFinderSettings settings) {
		this.settings = settings;
	}

	//TODO consider using Map in the interface (client classes should make sure the order is ok)
	public LocalDateTime findTrendStart(SortedMap<LocalDateTime, Double> data) {
		
		LocalDateTime result = null;
		
		Double min = null;
		Double max = null;
		Double current;
		
		LocalDateTime minDate = null;
		LocalDateTime maxDate = null;
		
		for(Map.Entry<LocalDateTime, Double> entry : data.entrySet()){
			current = entry.getValue();

			if (null == min || current < min){
				min = current;
				minDate = entry.getKey();
			}
			
			if (null == max || current > max){
				max = current;
				maxDate = entry.getKey();
			}
						
			if((max - min) >= settings.getMinStartDifference()){
				// Return earlier date
				result = minDate.isBefore(maxDate) ? minDate : maxDate;
				break;
			}
		}
		
		return result;
	}

	public LocalDateTime findTrendEnd(SortedMap<LocalDateTime, Double> data) {

		LocalDateTime result = null;
		
		Double min = null;
		Double max = null;
		Double current;
		
		LocalDateTime minDate = null;
		LocalDateTime maxDate = null;
		
		Boolean isUpwards = null;
		
		Double previousValue = null;
		
		for(Map.Entry<LocalDateTime, Double> entry : data.entrySet()){
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
				if(true == isUpwards && (max - current) >= settings.getMinEndDifference()){
					result = maxDate;
					break;
				} else if(false == isUpwards && -(min - current) >= settings.getMinEndDifference()){
					result = minDate;
					break;
				}
			}
		}
		
		return result;
	}
}
