package com.jorm.forex.price_data;

import com.jorm.forex.model.PriceRecord;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;
import java.util.SortedMap;

public interface PriceDataProvider {

    public SortedMap<LocalDateTime, PriceRecord> getData(Resource resource);

}
