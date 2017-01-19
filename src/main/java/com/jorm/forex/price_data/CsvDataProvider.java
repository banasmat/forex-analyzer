package com.jorm.forex.price_data;

import com.jorm.forex.model.PriceRecord;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

public class CsvDataProvider implements PriceDataProvider {

    @Override
    public SortedMap<LocalDateTime, PriceRecord> getData(Resource resource) {
        return null;
    }
}
