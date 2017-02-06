package com.jorm.forex.model;

import com.jorm.forex.trend.TrendFinderStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//TODO consider renaming to TrendFinderSession
@Entity
public class PriceDataAnalysis {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    //TODO might remove and use only reversed relation (we might use also other extracted forms as PriceSwing
    public final Trend[] trends;

    public final TrendFinderStrategy trendFinderStrategy;

    public final TrendFinderSettings trendFinderSettings;

    public final Date createdAt;

    public PriceDataAnalysis(Trend[] trends, TrendFinderStrategy trendFinderStrategy, TrendFinderSettings trendFinderSettings, Date createdAt) {
        this.trends = trends;
        this.trendFinderStrategy = trendFinderStrategy;
        this.trendFinderSettings = trendFinderSettings;
        this.createdAt = createdAt;
    }
}
