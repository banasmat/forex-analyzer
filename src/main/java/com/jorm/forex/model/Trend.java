package com.jorm.forex.model;

import javax.persistence.*;
import java.util.List;

//TODO might rename to something more generic like PriceDataGroup (subclasses: Trend, PriceSwig etc.) - then add type field
@Entity
public class Trend {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private PriceRecord start;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private PriceRecord end;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private Symbol symbol;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private PriceDataAnalysis priceDataAnalysis;

    public Trend(PriceRecord start, PriceRecord end, Symbol symbol) {
        this.symbol = symbol;
        this.start = start;
        this.end = end;
    }

    public Trend(PriceRecord start, PriceRecord end) {
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public PriceRecord getStart() {
        return start;
    }

    public PriceRecord getEnd() {
        return end;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Trend setSymbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public PriceDataAnalysis getPriceDataAnalysis() {
        return priceDataAnalysis;
    }

    public Trend setPriceDataAnalysis(PriceDataAnalysis priceDataAnalysis) {
        this.priceDataAnalysis = priceDataAnalysis;
        return this;
    }
}
