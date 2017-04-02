package com.jorm.forex.controller;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.price_record.IntervalResolver;
import com.jorm.forex.price_record.PriceRecordCondenser;
import com.jorm.forex.repository.PriceRecordSearchService;
import com.jorm.forex.repository.SymbolRepository;
import com.jorm.forex.util.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("price-record")
public class PriceRecordController {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormat;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private PriceRecordSearchService priceRecordSearchService;

    @Autowired
    private IntervalResolver intervalResolver;

    @Autowired
    private PriceRecordCondenser priceRecordCondenser;

    //TODO interval param
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<PriceRecord> priceRecords(
        @RequestParam String symbol,
        @RequestParam String start,
        @RequestParam String end,
        @RequestParam(defaultValue = "5M") String interval //TODO implement
    ){

        try{
            LocalDateTime startDate = LocalDateTime.parse(start, dateFormat);
            LocalDateTime endDate = LocalDateTime.parse(end, dateFormat);

            Symbol symbolObject = symbolRepository.findOneByName(symbol);

            if(null == symbolObject){
                //TODO invalid argument exception?
                //TODO create specific exception class?
                throw new RuntimeException("Symbol: '" + symbol + "' not found.");
            }

            //TODO probably interval can't be applied in sql query. All rows have to be selected anyway according to http://stackoverflow.com/questions/2694429/how-do-i-get-every-nth-row-in-a-table-or-how-do-i-break-up-a-subset-of-a-table
            List<PriceRecord> allResults = priceRecordSearchService.findBySymbolBetweenDates(symbolObject, startDate, endDate);

            return  priceRecordCondenser.condense(allResults, intervalResolver.resolve(interval));

        } catch (DateTimeParseException e){
            throw new RuntimeException(e.getMessage() + ". Correct date format: " + Format.dateTimeFormatString);
        }

    }
}
