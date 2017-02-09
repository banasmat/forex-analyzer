package com.jorm.forex.model;

import com.jorm.forex.trend.TrendFinderStrategy;

import javax.persistence.*;
import java.util.Date;

//TODO consider renaming to TrendFinderSession
@Entity
public class PriceDataAnalysis {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "priceDataAnalysis")
    public final Trend[] trends;

    //TODO save as entity relation or string?
    public final TrendFinderStrategy trendFinderStrategy;

    @ManyToOne()
    @JoinColumn(name="trend_finder_settings_id")
    public final TrendFinderSettings trendFinderSettings;

    @Column(name="created_at")
    public final Date createdAt;

    public PriceDataAnalysis(Trend[] trends, TrendFinderStrategy trendFinderStrategy, TrendFinderSettings trendFinderSettings, Date createdAt) {
        this.trends = trends;
        this.trendFinderStrategy = trendFinderStrategy;
        this.trendFinderSettings = trendFinderSettings;
        this.createdAt = createdAt;
    }
}
