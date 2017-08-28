package com.jorm.forex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.List;

//TODO might rename to something more generic like PriceDataGroup (subclasses: Trend, PriceSwing etc.) - then add type field
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

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private PriceDataAnalysis priceDataAnalysis;

    @OneToMany(mappedBy = "trend", cascade = CascadeType.PERSIST)
    private List<ForexCalendarEventTrendAssoc> forexCalendarEventTrendAssocs;

    @Transient
    private List<PriceRecord> priceRecords;

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
    @JsonProperty("id")
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

    public List<PriceRecord> getPriceRecords() {
        return priceRecords;
    }

    public Trend setPriceRecords(List<PriceRecord> priceRecords) {
        this.priceRecords = priceRecords;
        return this;
    }

    public List<ForexCalendarEventTrendAssoc> getForexCalendarEventTrendAssocs() {
        return forexCalendarEventTrendAssocs;
    }

    public void setForexCalendarEventTrendAssocs(List<ForexCalendarEventTrendAssoc> forexCalendarEventTrendAssocs) {
        this.forexCalendarEventTrendAssocs = forexCalendarEventTrendAssocs;
    }
}
