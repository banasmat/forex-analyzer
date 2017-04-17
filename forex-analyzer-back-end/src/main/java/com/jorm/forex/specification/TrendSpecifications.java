package com.jorm.forex.specification;

import com.jorm.forex.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.time.LocalDateTime;


public class TrendSpecifications {

    private TrendSpecifications(){}

    public static Specification<Trend> hasSymbol(Symbol symbol) {
        return (root, query, cb) -> {
            return cb.equal(root.get(Trend_.symbol), symbol);
        };
    }

    public static Specification<Trend> isBetweenDates(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> {
            return cb.and(
                cb.greaterThanOrEqualTo(root.get(Trend_.start).get(PriceRecord_.dateTime), start),
                cb.lessThanOrEqualTo(root.get(Trend_.end).get(PriceRecord_.dateTime), end)
            );
        };
    }

}
