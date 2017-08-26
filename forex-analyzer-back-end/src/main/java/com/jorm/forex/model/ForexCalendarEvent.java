package com.jorm.forex.model;

import com.jorm.forex.forex_calendar_event.Impact;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ForexCalendarEvent {

    //TODO consider adding body / details

    public static final String IMPACT_HIGH = "high";
    public static final String IMPACT_MEDIUM = "medium";
    public static final String IMPACT_LOW = "low";

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false) //TODO change type to Class (save as String)
    private String dataProviderClass;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String actual;

    @Column(nullable = false)
    private String previous;

    @Column()
    private String forecast;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Impact impact;

    public ForexCalendarEvent(String title, LocalDateTime dateTime, String dataProviderClass, String url, String currency, String actual, String previous, String forecast, Impact impact) {
        this.title = title;
        this.dateTime = dateTime;
        this.dataProviderClass = dataProviderClass;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
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

    public String getDataProviderClass() {
        return dataProviderClass;
    }

    public void setDataProviderClass(String dataProviderClass) {
        this.dataProviderClass = dataProviderClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
