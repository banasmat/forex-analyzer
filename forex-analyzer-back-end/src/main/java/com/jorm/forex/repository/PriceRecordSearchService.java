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

    public PriceRecordSearchService(PriceRecordRepository repository) {
        this.repository = repository;
    }

    public List<PriceRecord> findBySymbolBetweenDates(Symbol symbol, LocalDateTime start, LocalDateTime end){
        Specification<PriceRecord> hasSymbol = PriceRecordSpecifications.hasSymbol(symbol);
        //TODO consider including start and end date
        Specification<PriceRecord> isBetweenDates = PriceRecordSpecifications.isBetweenDates(start, end);

        return repository.findAll(
                Specifications.where(hasSymbol).and(
                Specifications.where(isBetweenDates)
        ));

    }

    public long countAllWithSymbolBetweenIncludedDates(Symbol symbol, LocalDateTime start, LocalDateTime end){
        Specification<PriceRecord> hasSymbol = PriceRecordSpecifications.hasSymbol(symbol);
        Specification<PriceRecord> isBetweenDates = PriceRecordSpecifications.isBetweenDates(start.minusMinutes(1), end.plusMinutes(1));

        return repository.count(
                    Specifications.where(hasSymbol).and(
                    Specifications.where(isBetweenDates)
                ));
    }
}