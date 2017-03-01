package com.jorm.forex.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class TrendFinderSettings {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "trendFinderSettings")
    public List<PriceDataAnalysis> priceDataAnalysis;

    //TODO these fields will vary. consider using key value instead
    @Column(nullable = false)
    private Double minPriceDifference = null;

    public TrendFinderSettings(Double minDifference) {
        this.minPriceDifference = minDifference;
    }

    public Double getMinPriceDifference() {
        return minPriceDifference;
    }
}
