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

    public ForexCalendarEventGatherer(EntityManager em) {
        this.em = em;
    }

    public ForexCalendarEventGathering findForexCalendarEvents(ForexCalendarEventProvider forexCalendarEventProvider, List<Trend> trends, ForexCalendarEventGatheringSettings settings){
        Integer hoursMargin = settings.getMargin();

        ForexCalendarEventGathering gathering = new ForexCalendarEventGathering(forexCalendarEventProvider, settings);

        for(Trend trend : trends){

            LocalDateTime trendStart = trend.getStart().getDateTime();
            LocalDateTime trendStartWithMargin = trendStart.minusHours(hoursMargin);

            List<ForexCalendarEvent> startEvents = forexCalendarEventProvider.getNewsInDateTimeRange(trendStart, trendStartWithMargin);

            createAssocs(startEvents, trend, gathering, TrendMoment.START);

            LocalDateTime trendEnd = trend.getEnd().getDateTime();
            LocalDateTime trendEndWithMargin = trendEnd.minusHours(hoursMargin);

            List<ForexCalendarEvent> endEvents = forexCalendarEventProvider.getNewsInDateTimeRange(trendEnd, trendEndWithMargin);

            createAssocs(endEvents, trend, gathering, TrendMoment.END);
        }

        em.persist(gathering);
        em.flush();

        return gathering;
    }

    private void createAssocs(List<ForexCalendarEvent> events, Trend trend, ForexCalendarEventGathering gathering, TrendMoment moment){
        for(ForexCalendarEvent event: events){

            Symbol trendSymbol = trend.getSymbol();
            if(null != trendSymbol.getFirstCurrency() &&
                    (event.getCurrency() == trendSymbol.getFirstCurrency() || event.getCurrency() == trendSymbol.getSecondCurrency())){
                ForexCalendarEventTrendAssoc assoc = createAssoc(event, trend, gathering, moment);
                gathering.addForexCalendarEventTrendAssoc(assoc);
                event.setForexCalendarEventGathering(gathering);
                em.persist(event);
                em.persist(assoc);
            }
        }
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
