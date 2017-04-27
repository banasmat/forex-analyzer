package com.jorm.forex.controller;

import com.jorm.forex.model.*;
import com.jorm.forex.price_data.*;
import com.jorm.forex.repository.PriceRecordSearchService;
import com.jorm.forex.trend.TrendFinderFactory;
import com.jorm.forex.trend.TrendFinderStrategy;
import com.jorm.forex.util.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

//TODO security

@RestController
@RequestMapping("price-data-analysis")
public class PriceDataAnalysisController {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormatter;

    @Autowired
    private PriceDataAnalyzer priceDataAnalyzer;

    @Autowired
    private SymbolResolver symbolResolver;

    @Autowired
    private PriceRecordSearchService priceRecordSearchService;

    @Autowired
    private TrendFinderFactory trendFinderFactory;

    @Value("${java.io.tmpdir}")
    private String tempDir;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity priceDataAnalysis(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam String symbol,
            @RequestParam(defaultValue = "HighLowAverage") String strategy,
            @RequestParam(defaultValue = "0.05") Double minDifference
    ) {
        //TODO probably should run on separate thread

        try {
            LocalDateTime startDate = LocalDateTime.parse(start, dateFormat);
            LocalDateTime endDate = LocalDateTime.parse(end, dateFormat);

            TrendFinderSettings trendFinderSettings = new TrendFinderSettings(minDifference);

            TrendFinderStrategy trendFinderStrategy = trendFinderFactory.getTrendFinderStrategy(strategy);

            Symbol symbolObject = symbolResolver.resolve(symbol);

            List<PriceRecord> priceRecords = priceRecordSearchService.findBySymbolBetweenDates(symbolObject, startDate, endDate);

            PriceDataAnalysis priceDataAnalysis = priceDataAnalyzer.analyzePriceData(priceRecords, trendFinderStrategy, symbolObject, trendFinderSettings);

            return ResponseEntity.status(HttpStatus.CREATED).body("Extracted " + priceDataAnalysis.getTrends().size() + " trends.");
        } catch (DateTimeParseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + ". Correct date format: " + Format.dateTimeFormatString);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }
}