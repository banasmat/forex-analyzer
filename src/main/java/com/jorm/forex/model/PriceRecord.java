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
    private Trend trend;

    //TODO consider storing prices as int

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Double open;

    @Column(nullable = false)
    private Double high;

    @Column(nullable = false)
    private Double low;

    @Column(nullable = false)
    private Double close;

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
        return p1.getDateTime().compareTo(p2.getDateTime());
    }

    public Long getId() {
        return id;
    }

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Double getOpen() {
        return open;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

    public Double getClose() {
        return close;
    }
}
