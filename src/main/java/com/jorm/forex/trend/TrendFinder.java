package com.jorm.forex.trend;

import java.util.Date;
import java.util.LinkedHashMap;

public interface TrendFinder {

	/**
	 * LinkedHashMap format is required to keep the order
	 */
	public Date findTrendStart(LinkedHashMap<Date, Double> data);
	
	/**
	 * LinkedHashMap format is required to keep the order
	 */
	public Date findTrendEnd(LinkedHashMap<Date, Double> data);
}
