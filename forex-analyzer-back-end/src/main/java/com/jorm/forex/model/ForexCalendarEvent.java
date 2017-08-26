package com.jorm.forex.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ForexCalendarEvent {

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

    @Column(nullable = false)
    private String impact;

    @Column()
    private String forecast;

    @Lob
    @Column()
    private String body;

    public ForexCalendarEvent(String title, LocalDateTime dateTime, String dataProviderClass, String url, String currency, String actual, String previous, String impact, String forecast, String body) {
        this.title = title;
        this.dateTime = dateTime;
        this.dataProviderClass = dataProviderClass;
        this.url = url;
        this.currency = currency;
        this.actual = actual;
        this.previous = previous;
        this.impact = impact;
        this.forecast = forecast;
        this.body = body;
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

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
