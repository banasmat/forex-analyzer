package com.jorm.forex.model;

import com.jorm.forex.forex_calendar_event.Impact;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ForexCalendarEvent {

    //TODO consider adding body / details

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String url;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private String actual;

    @Column(nullable = false)
    private String previous;

    @Column()
    private String forecast;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Impact impact;

    @OneToMany(mappedBy = "trend", cascade = CascadeType.PERSIST)
    private List<ForexCalendarEventTrendAssoc> forexCalendarEventTrendAssocs;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private ForexCalendarEventGathering forexCalendarEventGathering;

    public ForexCalendarEvent(String title, LocalDateTime dateTime, String url, Currency currency, String actual, String previous, String forecast, Impact impact) {
        this.title = title;
        this.dateTime = dateTime;
        this.url = url;
        this.currency = currency;
        this.actual = actual;
        this.previous = previous;
        this.impact = impact;
        this.forecast = forecast;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Impact getImpact() {
        return impact;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ForexCalendarEventTrendAssoc> getForexCalendarEventTrendAssocs() {
        return forexCalendarEventTrendAssocs;
    }

    public void setForexCalendarEventTrendAssocs(List<ForexCalendarEventTrendAssoc> forexCalendarEventTrendAssocs) {
        this.forexCalendarEventTrendAssocs = forexCalendarEventTrendAssocs;
    }

    public ForexCalendarEventGathering getForexCalendarEventGathering() {
        return forexCalendarEventGathering;
    }

    public void setForexCalendarEventGathering(ForexCalendarEventGathering forexCalendarEventGathering) {
        this.forexCalendarEventGathering = forexCalendarEventGathering;
    }
}
