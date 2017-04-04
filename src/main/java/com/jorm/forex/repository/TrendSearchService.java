package com.jorm.forex.repository;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.model.Trend;
import com.jorm.forex.specification.PriceRecordSpecifications;
import com.jorm.forex.specification.TrendSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrendSearchService {

    @Autowired
    private TrendRepository repository;

    public TrendSearchService(TrendRepository repository) {
        this.repository = repository;
    }

    public List<Trend> findBySymbolBetweenDates(Symbol symbol, LocalDateTime start, LocalDateTime end){
        Specification<Trend> hasSymbol = TrendSpecifications.hasSymbol(symbol);
        //TODO consider including start and end date
        Specification<Trend> isBetweenDates = TrendSpecifications.isBetweenDates(start, end);

        return repository.findAll(
                Specifications.where(hasSymbol).and(
                Specifications.where(isBetweenDates)
        ));

    }
}
