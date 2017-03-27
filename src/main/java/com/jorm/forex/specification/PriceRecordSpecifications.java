package com.jorm.forex.specification;

import com.jorm.forex.model.*;
import com.jorm.forex.model.Symbol;
import org.springframework.data.jpa.domain.Specification;


public class PriceRecordSpecifications {
    public static Specification<PriceRecord> priceRecordHasSymbol(Symbol symbol) {
        return (root, query, cb) -> {
            return cb.equal(root.get(PriceRecord_.symbol), symbol);
        };
    }

}
