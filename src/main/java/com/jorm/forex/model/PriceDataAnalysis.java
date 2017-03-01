package com.jorm.forex.model;

import com.jorm.forex.trend.TrendFinderStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//TODO consider renaming to TrendFinderSession
@Entity
public class PriceDataAnalysis {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "priceDataAnalysis", cascade = CascadeType.PERSIST)
    public final List<Trend> trends;

    //TODO save as entity relation or string?
    @Column(nullable = false)
    public final String trendFinderStrategyName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    public final TrendFinderSettings trendFinderSettings;

    @Column(nullable = false)
    public final Date createdAt;

    public PriceDataAnalysis(List<Trend> trends, String trendFinderStrategyName, TrendFinderSettings trendFinderSettings, Date createdAt) {
        this.trends = trends;
        this.trendFinderStrategyName = trendFinderStrategyName;
        this.trendFinderSettings = trendFinderSettings;
        this.createdAt = createdAt;
    }
}
