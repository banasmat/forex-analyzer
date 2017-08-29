package com.jorm.forex.controller;

import com.jorm.forex.forex_calendar_event.ForexCalendarEventAnalyzer;
import com.jorm.forex.model.*;
import com.jorm.forex.price_data.SymbolResolver;
import com.jorm.forex.repository.TrendSearchService;
import com.jorm.forex.util.Format;
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
@RequestMapping("forex-calendar-event-analysis")
public class ForexCalendarEventAnalysisController {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormatter;

    @Autowired
    private SymbolResolver symbolResolver;

    @Autowired
    private TrendSearchService trendSearchService;

    @Autowired
    private ForexCalendarEventAnalyzer analyzer;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity ForexCalendarEventAnalysis(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam String symbol,
            @RequestParam(defaultValue ="ForexFactory") String provider
    ) {
        //TODO probably should run on separate thread

        try {
            LocalDateTime startDate = LocalDateTime.parse(start, dateFormat);
            LocalDateTime endDate = LocalDateTime.parse(end, dateFormat);

            Symbol symbolObject = symbolResolver.resolve(symbol);

            List<Trend> trends = trendSearchService.findBySymbolBetweenDates(symbolObject, startDate, endDate);

            ForexCalendarEventAnalysis analysis = analyzer.findForexCalendarEvents(provider, trends);

            return ResponseEntity.status(HttpStatus.CREATED).body("Found " + analysis.getForexCalendarEventTrendAssocs().size() + " forex calendar events.");
        } catch (DateTimeParseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + ". Correct date format: " + Format.dateTimeFormatString);
        }


    }

}
