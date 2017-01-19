package com.jorm.forex.model;

public class PriceRecord implements Comparable<PriceRecord> {

    public final Double open;

    public final Double high;

    public final Double low;

    public final Double close;

    //TODO probably rename to: StockEntityPrices?
    public PriceRecord(Double open, Double high, Double low, Double close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    @Override
    public int compareTo(PriceRecord o) {
        return 0;
//        if(open.equals(o.open) && high.equals(o.high) && )
//
//        Double avg = open + high + low + close / 4;


    }
}
