package com.jorm.forex.trend;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.TrendFinderSettings;

import java.time.LocalDateTime;
import java.util.SortedMap;

public interface TrendFinderStrategy {

    //TODO settings should probably be injected some other way
    TrendFinderStrategy setSettings(TrendFinderSettings settings);

    /**
     * SortedMap format is required to keep the order
     */
    LocalDateTime findTrendStart(SortedMap<LocalDateTime, PriceRecord> data);

    /**
     * SortedMap format is required to keep the order
     */
    LocalDateTime findTrendEnd(SortedMap<LocalDateTime, PriceRecord> data);
}
