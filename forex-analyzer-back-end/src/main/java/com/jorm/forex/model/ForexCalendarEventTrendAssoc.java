package com.jorm.forex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorm.forex.trend.TrendMoment;

import javax.persistence.*;

@Entity
public class ForexCalendarEventTrendAssoc {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @ManyToOne()
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Trend trend;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private ForexCalendarEvent forexCalendarEvent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TrendMoment trendMoment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    @JsonIgnore
    private ForexCalendarEventGathering forexCalendarEventGathering;

    public ForexCalendarEventTrendAssoc() {}

    public ForexCalendarEventTrendAssoc(Trend trend, ForexCalendarEvent forexCalendarEvent, TrendMoment trendMoment, ForexCalendarEventGathering forexCalendarEventGathering) {
        this.trend = trend;
        this.forexCalendarEvent = forexCalendarEvent;
        this.trendMoment = trendMoment;
        this.forexCalendarEventGathering = forexCalendarEventGathering;
    }

    public Long getId() {
        return id;
    }

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    public ForexCalendarEvent getForexCalendarEvent() {
        return forexCalendarEvent;
    }

    public TrendMoment getTrendMoment() {
        return trendMoment;
    }

    public ForexCalendarEventGathering getForexCalendarEventGathering() {
        return forexCalendarEventGathering;
    }
}
