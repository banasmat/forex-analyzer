package com.jorm.forex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class TrendFinderSettings {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "trendFinderSettings")
    private List<PriceDataAnalysis> priceDataAnalysis;

    //TODO these fields will vary. consider using key value instead
    @Column(nullable = false )
    private Double minPriceDifference;

    public TrendFinderSettings(){}

    public TrendFinderSettings(Double minDifference) {
        this.minPriceDifference = minDifference;
    }

    //TODO builder and different constructors might be useful here

    public Double getMinPriceDifference() {
        return minPriceDifference;
    }

    public Long getId() {
        return id;
    }

    public List<PriceDataAnalysis> getPriceDataAnalysis() {
        return priceDataAnalysis;
    }
}
