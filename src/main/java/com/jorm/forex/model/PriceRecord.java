package com.jorm.forex.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PriceRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public final Double open;

    public final Double high;

    public final Double low;

    public final Double close;

    //TODO probably rename to: StockEntityPrices?
    //TODO include datetime?
    public PriceRecord(Double open, Double high, Double low, Double close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }
}
