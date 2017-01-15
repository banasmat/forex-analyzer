package com.jorm.forex.trend;

import com.jorm.forex.model.PriceRecord;

import java.time.LocalDateTime;
import java.util.SortedMap;

public interface TrendFinder {

	/**
	 * SortedMap format is required to keep the order
	 */
	LocalDateTime findTrendStart(SortedMap<LocalDateTime, PriceRecord> data);
	
	/**
	 * SortedMap format is required to keep the order
	 */
	LocalDateTime findTrendEnd(SortedMap<LocalDateTime, PriceRecord> data);
}
