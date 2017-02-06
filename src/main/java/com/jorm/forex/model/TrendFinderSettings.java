package com.jorm.forex.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//TODO consider using builder
@Entity
public class TrendFinderSettings {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

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
