package com.jorm.forex.model;

//TODO consider using builder
public class TrendFinderSettings {

    private Double minStartDifference = null;
    private Double minEndDifference = null;

    public TrendFinderSettings(Double minDifference) {
        this.minStartDifference = minDifference;
        this.minEndDifference = minDifference;
    }

    public Double getMinEndDifference() {
        return minEndDifference;
    }

    public Double getMinStartDifference() {
        return minStartDifference;
    }
}
