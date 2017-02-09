package com.jorm.forex.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;

@Entity
public class PriceRecord implements Comparator<PriceRecord> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trend_id", nullable = false)
    public Trend trend;

    @Column(name="date_time")
    public final LocalDateTime dateTime;

    @Column(name="open")
    public final Double open;

    @Column(name="high")
    public final Double high;

    @Column(name="low")
    public final Double low;

    @Column(name="close")
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
