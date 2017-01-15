package com.jorm.forex.financial_data;

import java.util.Date;
import java.util.SortedMap;

public interface DailyDataProvider {

	public SortedMap<Date, Double> getDate();
	
}
