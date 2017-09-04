package com.jorm.forex.forex_calendar_event;

import com.jorm.forex.model.*;
import com.jorm.forex.trend.TrendMoment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

//TODO unit test
@Service
@Transactional
public class ForexCalendarEventGatherer {

    @Autowired
    private EntityManager em;

    public ForexCalendarEventGathering findForexCalendarEvents(ForexCalendarEventProvider forexCalendarEventProvider, List<Trend> trends, ForexCalendarEventGatheringSettings settings){
        Integer hoursMargin = settings.getMargin();

        ForexCalendarEventGathering gathering = new ForexCalendarEventGathering(forexCalendarEventProvider, settings);

        for(Trend trend : trends){

            LocalDateTime trendStart = trend.getStart().getDateTime();
            LocalDateTime trendStartWithMargin = trendStart.minusHours(hoursMargin);
            trendStart = trendStart.plusHours(hoursMargin);

            List<ForexCalendarEvent> startEvents = forexCalendarEventProvider.getNewsInDateTimeRange(trendStart, trendStartWithMargin);

            for(ForexCalendarEvent startEvent: startEvents){
                ForexCalendarEventTrendAssoc assoc = createAssoc(startEvent, trend, gathering, TrendMoment.START);
                gathering.addForexCalendarEventTrendAssoc(assoc);
                startEvent.setForexCalendarEventGathering(gathering);
                em.persist(startEvent);
                em.persist(assoc);
            }

            LocalDateTime trendEnd = trend.getEnd().getDateTime();
            LocalDateTime trendEndWithMargin = trendEnd.minusHours(hoursMargin);
            trendEnd = trendEnd.plusHours(hoursMargin);

            List<ForexCalendarEvent> endEvents = forexCalendarEventProvider.getNewsInDateTimeRange(trendEnd, trendEndWithMargin);

            for(ForexCalendarEvent endEvent: endEvents){
                ForexCalendarEventTrendAssoc assoc = createAssoc(endEvent, trend, gathering, TrendMoment.END);
                gathering.addForexCalendarEventTrendAssoc(assoc);
                endEvent.setForexCalendarEventGathering(gathering);
                em.persist(endEvent);
                em.persist(assoc);
            }
        }

        em.persist(gathering);
        em.flush();

        return gathering;
    }

    private ForexCalendarEventTrendAssoc createAssoc(ForexCalendarEvent event, Trend trend, ForexCalendarEventGathering analysis, TrendMoment moment){
        ForexCalendarEventTrendAssoc assoc = new ForexCalendarEventTrendAssoc();
        assoc.setForexCalendarEvent(event);
        assoc.setTrend(trend);
        assoc.setForexCalendarEventGathering(analysis);
        assoc.setTrendMoment(TrendMoment.START);

        return assoc;
    }
}
