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
    @OrderColumn
    public final List<Trend> trends;

    //TODO save as entity relation or string?
    public final String trendFinderStrategyName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="trend_finder_settings_id")
    public final TrendFinderSettings trendFinderSettings;

    @Column(name="created_at")
    public final Date createdAt;

    public PriceDataAnalysis(List<Trend> trends, String trendFinderStrategyName, TrendFinderSettings trendFinderSettings, Date createdAt) {
        this.trends = trends;
        this.trendFinderStrategyName = trendFinderStrategyName;
        this.trendFinderSettings = trendFinderSettings;
        this.createdAt = createdAt;
    }
}
