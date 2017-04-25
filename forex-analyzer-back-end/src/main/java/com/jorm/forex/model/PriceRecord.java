package com.jorm.forex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;

//TODO add dateTime (+ symbol) index
//TODO add unique constraint for symbol & dateTime

@Entity
public class PriceRecord implements Comparator<PriceRecord> {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Symbol symbol;

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

    public PriceRecord(){}

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

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
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
