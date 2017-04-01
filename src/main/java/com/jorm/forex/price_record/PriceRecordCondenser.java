package com.jorm.forex.price_record;

import com.jorm.forex.model.PriceRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceRecordCondenser {

    //FIXME PriceRecords might not be recorder every minute (some minute records might be missing) - if so, we have check by DateTime

    public List<PriceRecord> condense(List<PriceRecord> priceRecords, Integer interval){

        if(interval == 1){
            return priceRecords;
        }

        List<PriceRecord> condensedPriceRecords = new ArrayList<>();

        Double open = 0D;
        Double high = 0D;
        Double low = 9999D;

        int lastElementInARowIndex = interval - 1;
        int lastPriceRecordIndex = priceRecords.size() - 1;

        PriceRecord condensedPriceRecord;

        for(int i=0; i < priceRecords.size(); i++){
            int mod = i % interval;

            PriceRecord currentPriceRecord = priceRecords.get(i);

            if(mod == 0){
                open = currentPriceRecord.getOpen();
            }

            if(currentPriceRecord.getHigh() > high){
                high = currentPriceRecord.getHigh();
            }

            if(currentPriceRecord.getLow() < low){
                low = currentPriceRecord.getLow();
            }

            if(mod == lastElementInARowIndex || i == lastPriceRecordIndex){
                condensedPriceRecord = new PriceRecord(currentPriceRecord.getDateTime(), open, high, low, currentPriceRecord.getClose());
                condensedPriceRecord.setSymbol(currentPriceRecord.getSymbol()); //TODO consider adding Symbol param to constructor

                condensedPriceRecords.add(condensedPriceRecord);

                high = 0D;
                low = 9999D;
            }
        }

        return condensedPriceRecords;
    }
}
