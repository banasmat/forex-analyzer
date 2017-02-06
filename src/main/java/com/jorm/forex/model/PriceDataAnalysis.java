package com.jorm.forex.model;

import com.jorm.forex.trend.TrendFinderStrategy;

import java.util.Date;

public class PriceDataAnalysis {

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
