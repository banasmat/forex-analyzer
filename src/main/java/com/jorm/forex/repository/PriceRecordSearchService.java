package com.jorm.forex.repository;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.price_record.PriceRecordCondenser;
import com.jorm.forex.specification.PriceRecordSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceRecordSearchService {

    @Autowired
    private PriceRecordRepository repository;

    @Autowired
    private PriceRecordCondenser priceRecordCondenser;

    public PriceRecordSearchService(PriceRecordRepository repository) {
        this.repository = repository;
    }

    public List<PriceRecord> findBySymbolBetweenDatesWithInterval(Symbol symbol, LocalDateTime start, LocalDateTime end, Integer interval){
        Specification<PriceRecord> hasSymbol = PriceRecordSpecifications.hasSymbol(symbol);
        Specification<PriceRecord> isBetweenDates = PriceRecordSpecifications.isBetweenDates(start, end);

        List<PriceRecord> allResults = repository.findAll(
                Specifications.where(hasSymbol).and(
                Specifications.where(isBetweenDates)
        ));

        //TODO condensation should be handled by sql query (optimization)
        return priceRecordCondenser.condense(allResults, interval);
    }
}
