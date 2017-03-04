package com.jorm.forex.trend;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.TrendFinderSettings;

import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedMap;

public interface TrendFinderStrategy {

    //TODO settings should probably be injected some other way
    TrendFinderStrategy setSettings(TrendFinderSettings settings);

    PriceRecord findTrendStart(List<PriceRecord> data);

    PriceRecord findTrendEnd(List<PriceRecord> data);

    String getName();
}
