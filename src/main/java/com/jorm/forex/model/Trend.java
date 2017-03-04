package com.jorm.forex.model;

import javax.persistence.*;
import java.util.List;

//TODO might rename to something more generic like PriceDataGroup (subclasses: Trend, PriceSwig etc.) - then add type field
@Entity
public class Trend {

    //TODO getters and setters

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    //FIXME we might need some margin when displaying trends. What happened before and after. Margin will be added to TrendFinderSettings.
    @OneToMany(mappedBy = "trend", cascade = CascadeType.ALL)
    public final List<PriceRecord> priceRecords;

    @ManyToOne()
    @JoinColumn(nullable = false)
    public Symbol symbol;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public PriceDataAnalysis priceDataAnalysis;

    public Trend(List<PriceRecord> priceRecords, Symbol symbol) {
        this.symbol = symbol;
        this.priceRecords = priceRecords;
    }

    public Trend(List<PriceRecord> priceRecords) {
        this.priceRecords = priceRecords;
    }
}
