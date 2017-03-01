package com.jorm.forex.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;

@Entity
public class PriceRecord implements Comparator<PriceRecord> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="trend_id", nullable = false)
    public Trend trend;

    //TODO consider storing prices as int

    @Column(nullable = false)
    public final LocalDateTime dateTime;

    @Column(nullable = false)
    public final Double open;

    @Column(nullable = false)
    public final Double high;

    @Column(nullable = false)
    public final Double low;

    @Column(nullable = false)
    public final Double close;

    //TODO probably rename to: StockEntityPrices?
    public PriceRecord(LocalDateTime dateTime, Double open, Double high, Double low, Double close) {
        this.dateTime = dateTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    @Override
    public int compare(PriceRecord p1, PriceRecord p2) {
        return p1.dateTime.compareTo(p2.dateTime);
    }
}
