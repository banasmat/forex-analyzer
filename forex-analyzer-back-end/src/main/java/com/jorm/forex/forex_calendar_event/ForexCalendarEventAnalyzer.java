package com.jorm.forex.forex_calendar_event;

import com.jorm.forex.model.ForexCalendarEvent;
import com.jorm.forex.model.ForexCalendarEventAnalysis;
import com.jorm.forex.model.ForexCalendarEventTrendAssoc;
import com.jorm.forex.model.Trend;
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
public class ForexCalendarEventAnalyzer {

    @Autowired
    private EntityManager em;

    public ForexCalendarEventAnalysis findForexCalendarEvents(ForexCalendarEventProvider forexCalendarEventProvider, List<Trend> trends){
        Integer hoursMargin = 12; //TODO should be in some Settings object

        ForexCalendarEventAnalysis analysis = new ForexCalendarEventAnalysis(forexCalendarEventProvider, new Date());

        for(Trend trend : trends){

            LocalDateTime trendStart = trend.getStart().getDateTime();
            LocalDateTime trendStartWithMargin = trendStart.minusHours(hoursMargin);
            trendStart = trendStart.plusHours(hoursMargin);

            List<ForexCalendarEvent> startEvents = forexCalendarEventProvider.getNewsInDateTimeRange(trendStart, trendStartWithMargin);

            for(ForexCalendarEvent startEvent: startEvents){
                ForexCalendarEventTrendAssoc assoc = createAssoc(startEvent, trend, analysis, TrendMoment.START);
                analysis.addForexCalendarEventTrendAssoc(assoc);
                em.persist(startEvent);
                em.persist(assoc);
            }

            LocalDateTime trendEnd = trend.getEnd().getDateTime();
            LocalDateTime trendEndWithMargin = trendEnd.minusHours(hoursMargin);
            trendEnd = trendEnd.plusHours(hoursMargin);

            List<ForexCalendarEvent> endEvents = forexCalendarEventProvider.getNewsInDateTimeRange(trendEnd, trendEndWithMargin);

            for(ForexCalendarEvent endEvent: endEvents){
                ForexCalendarEventTrendAssoc assoc = createAssoc(endEvent, trend, analysis, TrendMoment.END);
                analysis.addForexCalendarEventTrendAssoc(assoc);
                em.persist(endEvent);
                em.persist(assoc);
            }
        }

        em.persist(analysis);
        em.flush();

        return analysis;
    }

    private ForexCalendarEventTrendAssoc createAssoc(ForexCalendarEvent event, Trend trend, ForexCalendarEventAnalysis analysis, TrendMoment moment){
        ForexCalendarEventTrendAssoc assoc = new ForexCalendarEventTrendAssoc();
        assoc.setForexCalendarEvent(event);
        assoc.setTrend(trend);
        assoc.setForexCalendarEventAnalysis(analysis);
        assoc.setTrendMoment(TrendMoment.START);

        return assoc;
    }
}
