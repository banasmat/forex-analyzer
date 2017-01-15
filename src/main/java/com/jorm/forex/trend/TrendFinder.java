package com.jorm.forex.trend;

import java.time.LocalDateTime;
import java.util.SortedMap;

public interface TrendFinder {

	/**
	 * SortedMap format is required to keep the order
	 */
	LocalDateTime findTrendStart(SortedMap<LocalDateTime, Double> data);
	
	/**
	 * SortedMap format is required to keep the order
	 */
	LocalDateTime findTrendEnd(SortedMap<LocalDateTime, Double> data);
}
