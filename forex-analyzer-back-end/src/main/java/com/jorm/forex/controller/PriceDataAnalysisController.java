package com.jorm.forex.controller;

import com.jorm.forex.model.*;
import com.jorm.forex.price_data.*;
import com.jorm.forex.price_record.PriceRecordCreator;
import com.jorm.forex.repository.PriceRecordSearchService;
import com.jorm.forex.trend.TrendFinderFactory;
import com.jorm.forex.trend.TrendFinderStrategy;
import com.jorm.forex.util.FileHelper;
import com.jorm.forex.util.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//TODO apply consistent naming
//TODO write a script for running this command http://stackoverflow.com/questions/39329017/how-to-build-console-command-in-spring-boot-web-application-using-spring-shell
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
    public String priceDataAnalysis(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam String symbol,
            @RequestParam(defaultValue = "HighLowAverage") String strategy,
            @RequestParam(defaultValue = "0.05") Double minDifference
    ) throws IOException {

        //TODO probably should run on separate thread
        //TODO decide how to handle IOExceptions
        //TODO decide if return http codes

        LocalDateTime startDate = LocalDateTime.parse(start, dateFormat);
        LocalDateTime endDate = LocalDateTime.parse(end, dateFormat);

        TrendFinderSettings trendFinderSettings = new TrendFinderSettings(minDifference);

        TrendFinderStrategy trendFinderStrategy = trendFinderFactory.getTrendFinderStrategy(strategy);

        Symbol symbolObject = symbolResolver.resolve(symbol);

        List<PriceRecord> priceRecords = priceRecordSearchService.findBySymbolBetweenDates(symbolObject, startDate, endDate);

        PriceDataAnalysis priceDataAnalysis = priceDataAnalyzer.analyzePriceData(priceRecords, trendFinderStrategy, symbolObject, trendFinderSettings);

        return "Extracted " + priceDataAnalysis.getTrends().size() + " trends.";
    }
}