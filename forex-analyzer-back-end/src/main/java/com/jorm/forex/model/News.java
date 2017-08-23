package com.jorm.forex.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String dataProviderClass;

    @Column(nullable = false)
    private String url;

    @Column(nullable = true)
    private Symbol symbol;

    public News(String title, LocalDateTime dateTime, String dataProviderClass, String url, Symbol symbol) {
        this.title = title;
        this.dateTime = dateTime;
        this.dataProviderClass = dataProviderClass;
        this.url = url;
        this.symbol = symbol;
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

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
