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
    private List<Trend> trends;

    //TODO save as entity relation or string?
    @Column(nullable = false)
    private String trendFinderStrategyName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private TrendFinderSettings trendFinderSettings;

    @Column(nullable = false)
    private Date createdAt;

    public PriceDataAnalysis() {}

    public PriceDataAnalysis(List<Trend> trends, TrendFinderStrategy trendFinderStrategy, TrendFinderSettings trendFinderSettings) {
        this.trends = trends;
        this.trendFinderStrategyName = trendFinderStrategy.getName();
        this.trendFinderSettings = trendFinderSettings;
        this.createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public List<Trend> getTrends() {
        return trends;
    }

    public String getTrendFinderStrategyName() {
        return trendFinderStrategyName;
    }

    public TrendFinderSettings getTrendFinderSettings() {
        return trendFinderSettings;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
