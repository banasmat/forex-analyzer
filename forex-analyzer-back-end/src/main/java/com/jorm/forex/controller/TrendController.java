package com.jorm.forex.controller;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.model.Trend;
import com.jorm.forex.price_record.Interval;
import com.jorm.forex.price_record.IntervalResolver;
import com.jorm.forex.price_record.PriceRecordCondenser;
import com.jorm.forex.repository.PriceRecordSearchService;
import com.jorm.forex.repository.SymbolRepository;
import com.jorm.forex.repository.TrendRepository;
import com.jorm.forex.repository.TrendSearchService;
import com.jorm.forex.util.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Trend> trends(
        @RequestParam String symbol,
        @RequestParam String start,
        @RequestParam String end,
        @RequestParam(defaultValue = "1H") String interval
        //@RequestParam(defaultValue = "1H") String margin
    ) throws NoSuchMethodException {

        try{
            //TODO start / end - optional

            LocalDateTime startDate = LocalDateTime.parse(start, dateFormat);
            LocalDateTime endDate = LocalDateTime.parse(end, dateFormat);

            Symbol symbolObject = symbolRepository.findOneByName(symbol);

            if(null == symbolObject){
                //TODO invalid argument exception? or create specific exception class?
                throw new RuntimeException("Symbol: '" + symbol + "' not found.");
            }

            List<Trend> trends = trendSearchService.findBySymbolBetweenDates(symbolObject, startDate, endDate);

            //TODO should be optimized. Get with one query or maybe save a graph picture on front end.
            for(Trend trend : trends){
                setPriceRecords(trend, intervalResolver.resolve(interval));
            }

            return trends;

        } catch (DateTimeParseException e){
            throw new RuntimeException(e.getMessage() + ". Correct date format: " + Format.dateTimeFormatString);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path="/{id}")
    public @ResponseBody Trend trend(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1H") String interval
    ) throws NoSuchMethodException {

        Trend trend = trendRepository.findOne(id);

        if(null != trend){
            setPriceRecords(trend, intervalResolver.resolve(interval));
        }

        return trend;
    }

    private Trend setPriceRecords(Trend trend, Interval interval){

        List<PriceRecord> priceRecords = priceRecordSearchService.findBySymbolBetweenDates(trend.getSymbol(), trend.getStart().getDateTime(), trend.getEnd().getDateTime());
        trend.setPriceRecords(priceRecordCondenser.condense(priceRecords, interval));

        return trend;
    }

}
