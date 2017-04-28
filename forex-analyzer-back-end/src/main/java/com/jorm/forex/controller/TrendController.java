package com.jorm.forex.controller;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.model.Trend;
import com.jorm.forex.price_record.Interval;
import com.jorm.forex.price_record.IntervalResolver;
import com.jorm.forex.price_record.MarginResolver;
import com.jorm.forex.price_record.PriceRecordCondenser;
import com.jorm.forex.repository.PriceRecordSearchService;
import com.jorm.forex.repository.SymbolRepository;
import com.jorm.forex.repository.TrendRepository;
import com.jorm.forex.repository.TrendSearchService;
import com.jorm.forex.util.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("trend")
public class TrendController {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormatter;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private TrendSearchService trendSearchService;

    @Autowired
    private PriceRecordSearchService priceRecordSearchService;

    @Autowired
    private TrendRepository trendRepository;

    @Autowired
    private PriceRecordCondenser priceRecordCondenser;

    @Autowired
    private IntervalResolver intervalResolver;

    @Autowired
    private MarginResolver marginResolver;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity trends(
        @RequestParam String symbol,
        @RequestParam String start,
        @RequestParam String end,
        @RequestParam(defaultValue = "1H") String interval,
        @RequestParam(defaultValue = "10") Integer marginPercent
    ) throws NoSuchMethodException {

        try{
            Symbol symbolObject = symbolRepository.findOneByName(symbol);

            if(null == symbolObject){
                //TODO invalid argument exception? or create specific exception class?
                throw new RuntimeException("Symbol: '" + symbol + "' not found.");
            }

            LocalDateTime startDate = LocalDateTime.parse(start, dateFormat);
            LocalDateTime endDate = LocalDateTime.parse(end, dateFormat);

            List<Trend> trends = trendSearchService.findBySymbolBetweenDates(symbolObject, startDate, endDate);

            //TODO should be optimized. Get with one query or maybe save a graph picture on front end.
            for(Trend trend : trends){

                Integer minutesMargin = marginResolver.countMinutesMargin(trend.getStart().getDateTime(), trend.getEnd().getDateTime(), marginPercent);

                setPriceRecords(trend, intervalResolver.resolve(interval), minutesMargin);
            }

            return ResponseEntity.ok(trends);

            //FIXME got this response: "Index: 0, Size: 0" 400

        } catch (DateTimeParseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + ". Correct date format: " + Format.dateTimeFormatString);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path="/{id}")
    public ResponseEntity trend(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1H") String interval,
            @RequestParam(defaultValue = "10") Integer marginPercent
    ) throws NoSuchMethodException {

        Trend trend = trendRepository.findOne(id);

        if(null != trend){
            Integer minutesMargin = marginResolver.countMinutesMargin(trend.getStart().getDateTime(), trend.getEnd().getDateTime(), marginPercent);
            setPriceRecords(trend, intervalResolver.resolve(interval), minutesMargin);
            return ResponseEntity.ok(trend);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trend with id: " + id + " not found.");
    }

    private Trend setPriceRecords(Trend trend, Interval interval, Integer minutesMargin){

        List<PriceRecord> priceRecords = priceRecordSearchService.findBySymbolBetweenDates(trend.getSymbol(), trend.getStart().getDateTime().minus(minutesMargin, ChronoUnit.MINUTES), trend.getEnd().getDateTime().plus(minutesMargin, ChronoUnit.MINUTES));
        trend.setPriceRecords(priceRecordCondenser.condense(priceRecords, interval));

        return trend;
    }

}
