package com.jorm.forex.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ForexCalendarEventAnalysis {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "forexCalendarEventAnalysis", cascade = CascadeType.PERSIST)
    private List<ForexCalendarEventTrendAssoc> forexCalendarEventTrendAssocs;

    //TODO save as entity relation or string? (or Class name)
    @Column(nullable = false)
    private String forexCalendarEventProviderName;

    @Column(nullable = false)
    private Date createdAt;

    //TODO probalby rename to ForexCalendarEventSearch / MatchSearch
    public ForexCalendarEventAnalysis(String forexCalendarEventProviderName, Date createdAt) {
        this.forexCalendarEventProviderName = forexCalendarEventProviderName;
        this.createdAt = createdAt;
        this.forexCalendarEventTrendAssocs = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<ForexCalendarEventTrendAssoc> getForexCalendarEventTrendAssocs() {
        return forexCalendarEventTrendAssocs;
    }

    public void setForexCalendarEventTrendAssocs(List<ForexCalendarEventTrendAssoc> forexCalendarEventTrendAssocs) {
        this.forexCalendarEventTrendAssocs = forexCalendarEventTrendAssocs;
    }

    public void addForexCalendarEventTrendAssoc(ForexCalendarEventTrendAssoc assoc){
        this.forexCalendarEventTrendAssocs.add(assoc);
    }

    public String getForexCalendarEventProviderName() {
        return forexCalendarEventProviderName;
    }

    public void setForexCalendarEventProviderName(String forexCalendarEventProviderName) {
        this.forexCalendarEventProviderName = forexCalendarEventProviderName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
