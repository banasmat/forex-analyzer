package com.jorm.forex.specification;

import com.jorm.forex.model.*;
import com.jorm.forex.model.Symbol;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;


public class PriceRecordSpecifications {

    private PriceRecordSpecifications(){}

    public static Specification<PriceRecord> hasSymbol(Symbol symbol) {
        return (root, query, cb) -> {
            return cb.equal(root.get(PriceRecord_.symbol), symbol);
        };
    }

    public static Specification<PriceRecord> isBetweenDates(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> {
            return cb.between(root.get(PriceRecord_.dateTime), start, end);
        };
    }

}
