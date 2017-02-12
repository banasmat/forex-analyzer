package com.jorm.forex.model;

import javax.persistence.*;
import java.util.List;

//TODO might rename to something more generic like PriceDataGroup (subclasses: Trend, PriceSwig etc.) - then add type field
@Entity
public class Trend {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "trend")
    @OrderColumn
    public final List<PriceRecord> priceRecords;

    @ManyToOne()
    @JoinColumn(name="symbol_id")
    public Symbol symbol;

    @ManyToOne()
    @JoinColumn(name="price_data_analysis_id")
    public PriceDataAnalysis priceDataAnalysis;

    public Trend(List<PriceRecord> priceRecords, Symbol symbol) {
        this.symbol = symbol;
        this.priceRecords = priceRecords;
    }

    public Trend(List<PriceRecord> priceRecords) {
        this.priceRecords = priceRecords;
    }
}
