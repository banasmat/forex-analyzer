package com.jorm.forex.trend;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;

public interface TrendFinder {

	/**
	 * LinkedHashMap format is required to keep the order
	 */
	public LocalDateTime findTrendStart(LinkedHashMap<LocalDateTime, Double> data);
	
	/**
	 * LinkedHashMap format is required to keep the order
	 */
	public LocalDateTime findTrendEnd(LinkedHashMap<LocalDateTime, Double> data);
}
