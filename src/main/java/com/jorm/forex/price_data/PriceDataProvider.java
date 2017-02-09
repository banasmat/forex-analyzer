package com.jorm.forex.price_data;

import com.jorm.forex.model.PriceRecord;
import org.springframework.core.io.Resource;

import java.util.List;

public interface PriceDataProvider {

    public List<PriceRecord> getData(Resource resource);

}
