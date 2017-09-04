package com.jorm.forex.controller;

import com.jorm.forex.forex_calendar_event.ForexCalendarEventGatherer;
import com.jorm.forex.forex_calendar_event.ForexCalendarEventProvider;
import com.jorm.forex.forex_calendar_event.ForexCalendarEventProviderFactory;
import com.jorm.forex.model.*;
import com.jorm.forex.price_data.SymbolResolver;
import com.jorm.forex.repository.TrendSearchService;
import com.jorm.forex.util.Format;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("forex-calendar-event-gathering")
public class ForexCalendarEventGatheringController {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormatter;

    @Autowired
    private SymbolResolver symbolResolver;

    @Autowired
    private TrendSearchService trendSearchService;

    @Autowired
    private ForexCalendarEventProviderFactory forexCalendarEventProviderFactory;

    @Autowired
    private ForexCalendarEventGatherer gatherer;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity ForexCalendarEventGathering(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam String symbol,
            @RequestParam(defaultValue ="ForexFactory") String provider,
            @RequestParam(defaultValue ="4") String margin
    ) throws InvalidArgumentException {
        //TODO probably should run on separate thread

        try {
            LocalDateTime startDate = LocalDateTime.parse(start, dateFormat);
            LocalDateTime endDate = LocalDateTime.parse(end, dateFormat);

            Symbol symbolObject = symbolResolver.resolve(symbol);

            List<Trend> trends = trendSearchService.findBySymbolBetweenDates(symbolObject, startDate, endDate);

            ForexCalendarEventProvider forexCalendarEventProvider = forexCalendarEventProviderFactory.getForexCalendarEventProvider(provider);

            ForexCalendarEventGatheringSettings settings = new ForexCalendarEventGatheringSettings(Integer.parseInt(margin));

            ForexCalendarEventGathering gathering = gatherer.findForexCalendarEvents(forexCalendarEventProvider, trends, settings);

            return ResponseEntity.status(HttpStatus.CREATED).body("Found " + gathering.getForexCalendarEventTrendAssocs().size() + " forex calendar events.");
        } catch (DateTimeParseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + ". Correct date format: " + Format.dateTimeFormatString);
        }


    }

}
