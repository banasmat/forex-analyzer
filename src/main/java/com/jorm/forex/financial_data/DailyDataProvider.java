package com.jorm.forex.financial_data;

import com.jorm.forex.model.PriceRecord;

import java.util.Date;
import java.util.SortedMap;

public interface DailyDataProvider {

    public SortedMap<Date, PriceRecord> getDate();

}
