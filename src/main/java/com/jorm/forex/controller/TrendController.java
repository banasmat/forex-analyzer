package com.jorm.forex.controller;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.model.Trend;
import com.jorm.forex.price_record.IntervalResolver;
import com.jorm.forex.price_record.PriceRecordCondenser;
import com.jorm.forex.repository.PriceRecordSearchService;
import com.jorm.forex.repository.SymbolRepository;
import com.jorm.forex.repository.TrendRepository;
import com.jorm.forex.repository.TrendSearchService;
import com.jorm.forex.util.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping("trend")
public class TrendController {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormat;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private TrendSearchService trendSearchService;

    @Autowired
    private PriceRecordSearchService priceRecordSearchService;

    @Autowired
    private IntervalResolver intervalResolver;

    @Autowired
    private PriceRecordCondenser priceRecordCondenser;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Trend> trends(
        @RequestParam String symbol,
        @RequestParam String start,
        @RequestParam String end
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

            List<Trend> allResults = trendSearchService.findBySymbolBetweenDates(symbolObject, startDate, endDate);

            for(Trend trend : allResults){
                trend.add(linkTo(
                        TrendController.class,
                        TrendController.class.getMethod("trend", Long.class),
                        trend.getID()).withSelfRel());
                // Cleaner approach commented out (for some reason throws exception)
//                trend.add(linkTo(
//                        methodOn(TrendController.class).trend(trend.getID())).withSelfRel()
//                );
//              //TODO might set interval depending on trend length
                trend.add(linkTo(
                        methodOn(PriceRecordController.class).priceRecords(symbol, start, end, "1H")).withRel("priceRecords")
                );
            }

            return allResults;

        } catch (DateTimeParseException e){
            throw new RuntimeException(e.getMessage() + ". Correct date format: " + Format.dateTimeFormatString);
        }

    }

    @RequestMapping(method = RequestMethod.GET, path="/{id}")
    public @ResponseBody Resource<Trend> trend(
            @PathVariable Long id
    ){
            //TODO implement
            return new Resource<>(new Trend());
    }
}
