package com.jorm.forex.forex_calendar_event;

import com.jorm.forex.trend.TrendFinderStrategy;

/**
 * Inspired by http://kh-yiu.blogspot.com/2013/04/spring-implementing-factory-pattern.html
 */
public interface ForexCalendarEventProviderFactory {
    ForexCalendarEventProvider getForexCalendarEventProvider(String providerName);
}
