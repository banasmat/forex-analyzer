package com.jorm.forex.repository;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.specification.PriceRecordSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceRecordSearchService {

    @Autowired
    private PriceRecordRepository repository;

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

        //FIXME when interval is higher than 1min. we should take {interval} amount of records and get opening price from the first, closing from the last and highest/lowest from the middle

        return allResults.stream().filter(r -> r.getHigh() < 100).collect(Collectors.toList());
//.stream().filter(p -> p.getAge() > 16).collect(Collectors.toList());
    }
}
