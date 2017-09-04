package com.jorm.forex.forex_calendar_event;

import com.jorm.forex.model.*;
import com.jorm.forex.util.Format;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

public class ForexCalendarEventGathererTest {

    ForexCalendarEventGatherer gatherer;

    @Mock
    private EntityManager em;

    @Mock
    private ForexCalendarEventProvider forexCalendarEventProvider;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp(){
        gatherer = new ForexCalendarEventGatherer(em);
    }

    @Test
    public void shouldCreateAssocsForEventsMatchingTrendDateTimesAndSymbol(){

        Currency currency1 = Currency.AED;
        Currency currency2 = Currency.AFN;

        Symbol symbol = new Symbol(currency1, currency2);

        LocalDateTime startDate = LocalDateTime.parse("01-01-2001 00:00:00", Format.dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse("01-02-2001 00:00:00", Format.dateTimeFormatter);

        PriceRecord start = new PriceRecord(startDate, 1D,1D,1D,1D);
        PriceRecord end = new PriceRecord(endDate, 1D,1D,1D,1D);

        List<Trend> trends = new ArrayList<Trend>(){
            {
                add(new Trend(start, end, symbol));
            }
        };
        ForexCalendarEventGatheringSettings settings = new ForexCalendarEventGatheringSettings(100);

        List<ForexCalendarEvent> startEvents = new ArrayList<ForexCalendarEvent>(){
            {
                add(new ForexCalendarEvent("", LocalDateTime.now(), "", currency1, "", "", "", Impact.MEDIUM));
                add(new ForexCalendarEvent("", LocalDateTime.now(), "", Currency.USD, "", "", "", Impact.MEDIUM));
                add(new ForexCalendarEvent("", LocalDateTime.now(), "", Currency.EUR, "", "", "", Impact.MEDIUM));
            }
        };
        List<ForexCalendarEvent> endEvents = new ArrayList<ForexCalendarEvent>(){
            {
                add(new ForexCalendarEvent("", LocalDateTime.now(), "", currency1, "", "", "", Impact.MEDIUM));
                add(new ForexCalendarEvent("", LocalDateTime.now(), "", currency2, "", "", "", Impact.MEDIUM));
                add(new ForexCalendarEvent("", LocalDateTime.now(), "", Currency.EUR, "", "", "", Impact.MEDIUM));
            }
        };

        when(forexCalendarEventProvider.getNewsInDateTimeRange(eq(startDate), any(LocalDateTime.class))).thenReturn(startEvents);
        when(forexCalendarEventProvider.getNewsInDateTimeRange(eq(endDate), any(LocalDateTime.class))).thenReturn(endEvents);

        ForexCalendarEventGathering result = gatherer.findForexCalendarEvents(forexCalendarEventProvider, trends, settings);

        assertEquals(3, result.getForexCalendarEventTrendAssocs().size());
    }
}