package com.jorm.forex.controller;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.repository.PriceRecordRepository;
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
    private PriceRecordRepository priceRecordRepository;

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
            LocalDateTime endDate = LocalDateTime.parse(start, dateFormat);

            Symbol symbolObject = symbolRepository.findOneByName(symbol);

            if(null == symbolObject){
                //TODO invalid argument exception?
                //TODO create specific exception class?
                throw new RuntimeException("Symbol: '" + symbol + "' not found.");
            }

//            return priceRecordRepository.findBySymbolAndDateTimeBetweenFilterByInterval(symbolObject, startDate, endDate);
throw new RuntimeException();
        } catch (DateTimeParseException e){
            throw new RuntimeException(e.getMessage() + ". Correct date format: " + Format.dateTimeFormatString);
        }


    }
}
