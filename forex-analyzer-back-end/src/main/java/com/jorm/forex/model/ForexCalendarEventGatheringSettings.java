package com.jorm.forex.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ForexCalendarEventGatheringSettings {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "forexCalendarEventGatheringSettings")
    private List<ForexCalendarEventGathering> forexCalendarEventGatherings;

    @Column(nullable = false )
    private Integer margin;

    public ForexCalendarEventGatheringSettings(Integer margin) {
        this.margin = margin;
        this.forexCalendarEventGatherings = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<ForexCalendarEventGathering> getForexCalendarEventGatherings() {
        return forexCalendarEventGatherings;
    }

    public void addForexCalendarEventGatherings(ForexCalendarEventGathering forexCalendarEventGatherings) {
        this.forexCalendarEventGatherings.add(forexCalendarEventGatherings);
    }

    public Integer getMargin() {
        return margin;
    }
}
