package com.jorm.forex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.List;

//TODO might rename to something more generic like PriceDataGroup (subclasses: Trend, PriceSwig etc.) - then add type field
@Entity
public class Trend extends ResourceSupport {

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

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private PriceDataAnalysis priceDataAnalysis;

    public Trend(){}

    public Trend(PriceRecord start, PriceRecord end, Symbol symbol) {
        this.symbol = symbol;
        this.start = start;
        this.end = end;
    }

    public Trend(PriceRecord start, PriceRecord end) {
        this.start = start;
        this.end = end;
    }

    // https://github.com/spring-projects/spring-hateoas/issues/66
    public Long getID() {
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
