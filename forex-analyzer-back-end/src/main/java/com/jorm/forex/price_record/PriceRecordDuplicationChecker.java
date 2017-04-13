package com.jorm.forex.price_record;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.repository.PriceRecordSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO consider moving saving PriceRecords here (if so, rename)

@Service
public class PriceRecordDuplicationChecker {

    @Autowired
    private PriceRecordSearchService priceRecordSearchService;

    public PriceRecordDuplicationChecker(PriceRecordSearchService priceRecordSearchService) {
        this.priceRecordSearchService = priceRecordSearchService;
    }

    public Boolean checkIfPriceRecordsExist(List<PriceRecord> priceRecords, Symbol symbol){
        Long countExisting = priceRecordSearchService.countAllWithSymbolBetweenIncludedDates(
                symbol,
                priceRecords.get(0).getDateTime(),
                priceRecords.get(priceRecords.size() - 1).getDateTime());

        if(countExisting == priceRecords.size()){
            return true;
        }

        return false;
    }
}
