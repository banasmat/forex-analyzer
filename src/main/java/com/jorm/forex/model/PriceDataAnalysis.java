package com.jorm.forex.model;

import com.jorm.forex.trend.TrendFinder;
import com.jorm.forex.trend.TrendFinderSettings;

import java.util.Date;

public class PriceDataAnalysis {

    //TODO might remove and use only reversed relation (we might use also other extracted forms as PriceSwing
    public final Trend[] trends;

    public final TrendFinder trendFinder;

    public final TrendFinderSettings trendFinderSettings;

    public final Date createdAt;

    public PriceDataAnalysis(Trend[] trends, TrendFinder trendFinder, TrendFinderSettings trendFinderSettings, Date createdAt) {
        this.trends = trends;
        this.trendFinder = trendFinder;
        this.trendFinderSettings = trendFinderSettings;
        this.createdAt = createdAt;
    }
}
