package com.jorm.forex.price_record;

import com.jorm.forex.model.PriceRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceRecordFilter {

    public List<PriceRecord> filter(List<PriceRecord> priceRecords, Integer interval){
        return priceRecords;
    }
}
