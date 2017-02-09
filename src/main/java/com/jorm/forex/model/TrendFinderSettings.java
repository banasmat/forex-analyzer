package com.jorm.forex.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TrendFinderSettings {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    private PriceDataAnalysis priceDataAnalysis;


    //TODO these fields will vary. consider using key value instead
    private Double minPriceDifference = null;
    private Double minEndDifference = null;

    public TrendFinderSettings(Double minDifference) {
        this.minPriceDifference = minDifference;
        this.minEndDifference = minDifference;
    }

    public Double getMinEndDifference() {
        return minEndDifference;
    }

    public Double getMinPriceDifference() {
        return minPriceDifference;
    }
}
