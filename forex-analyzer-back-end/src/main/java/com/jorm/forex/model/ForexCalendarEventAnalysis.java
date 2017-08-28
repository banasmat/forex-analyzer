package com.jorm.forex.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class ForexCalendarEventAnalysis {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "forexCalendarEventTrendAssoc", cascade = CascadeType.PERSIST)
    private List<ForexCalendarEventTrendAssoc> forexCalendarEventTrendAssocs;

    //TODO save as entity relation or string?
    @Column(nullable = false)
    private String forexCalendarEventProviderName;

    @Column(nullable = false)
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public List<ForexCalendarEventTrendAssoc> getForexCalendarEventTrendAssocs() {
        return forexCalendarEventTrendAssocs;
    }

    public void setForexCalendarEventTrendAssocs(List<ForexCalendarEventTrendAssoc> forexCalendarEventTrendAssocs) {
        this.forexCalendarEventTrendAssocs = forexCalendarEventTrendAssocs;
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
