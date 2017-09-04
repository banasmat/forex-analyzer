package com.jorm.forex.model;

import com.jorm.forex.trend.TrendMoment;

import javax.persistence.*;

@Entity
public class ForexCalendarEventTrendAssoc {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private Trend trend;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private ForexCalendarEvent forexCalendarEvent;

    @Column(nullable = false) //FIXME save as String (?)
    private TrendMoment trendMoment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private ForexCalendarEventGathering forexCalendarEventGathering;

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

    public void setForexCalendarEvent(ForexCalendarEvent forexCalendarEvent) {
        this.forexCalendarEvent = forexCalendarEvent;
    }

    public TrendMoment getTrendMoment() {
        return trendMoment;
    }

    public void setTrendMoment(TrendMoment trendMoment) {
        this.trendMoment = trendMoment;
    }

    public ForexCalendarEventGathering getForexCalendarEventGathering() {
        return forexCalendarEventGathering;
    }

    public void setForexCalendarEventGathering(ForexCalendarEventGathering forexCalendarEventGathering) {
        this.forexCalendarEventGathering = forexCalendarEventGathering;
    }
}
