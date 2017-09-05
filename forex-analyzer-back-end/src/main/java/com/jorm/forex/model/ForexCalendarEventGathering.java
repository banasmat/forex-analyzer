package com.jorm.forex.model;

import com.jorm.forex.forex_calendar_event.ForexCalendarEventProvider;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ForexCalendarEventGathering {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "forexCalendarEventGathering", cascade = CascadeType.PERSIST)
    private List<ForexCalendarEventTrendAssoc> forexCalendarEventTrendAssocs;

    @OneToMany(mappedBy = "forexCalendarEventGathering", cascade = CascadeType.PERSIST)
    private List<ForexCalendarEvent> forexCalendarEvents;

    //TODO save as entity relation or string? (or Class name)
    @Column(nullable = false)
    private String forexCalendarEventProviderName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private ForexCalendarEventGatheringSettings forexCalendarEventGatheringSettings;

    @Column(nullable = false)
    private Date createdAt;

    public ForexCalendarEventGathering() {}

    //TODO probalby rename to ForexCalendarEventSearch / MatchSearch
    public ForexCalendarEventGathering(ForexCalendarEventProvider forexCalendarEventProvider, ForexCalendarEventGatheringSettings forexCalendarEventGatheringSettings) {
        this.forexCalendarEventGatheringSettings = forexCalendarEventGatheringSettings;

        this.forexCalendarEventProviderName = forexCalendarEventProvider.getName();
        this.createdAt = new Date();
        this.forexCalendarEventTrendAssocs = new ArrayList<>();
        this.forexCalendarEvents = new ArrayList<>();
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

    public List<ForexCalendarEvent> getForexCalendarEvents() {
        return forexCalendarEvents;
    }

    public void setForexCalendarEvents(List<ForexCalendarEvent> forexCalendarEvents) {
        this.forexCalendarEvents = forexCalendarEvents;
    }

    public void addForexCalendarEvent(ForexCalendarEvent assoc){
        this.forexCalendarEvents.add(assoc);
    }

    public String getForexCalendarEventProviderName() {
        return forexCalendarEventProviderName;
    }

    public ForexCalendarEventGatheringSettings getForexCalendarEventGatheringSettings() {
        return forexCalendarEventGatheringSettings;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
