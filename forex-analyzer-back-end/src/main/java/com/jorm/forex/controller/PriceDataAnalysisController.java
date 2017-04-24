package com.jorm.forex.controller;

import com.jorm.forex.model.*;
import com.jorm.forex.price_data.*;
import com.jorm.forex.price_record.PriceRecordCreator;
import com.jorm.forex.trend.TrendFinderFactory;
import com.jorm.forex.trend.TrendFinderStrategy;
import com.jorm.forex.util.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

//TODO apply consistent naming
//TODO write a script for running this command http://stackoverflow.com/questions/39329017/how-to-build-console-command-in-spring-boot-web-application-using-spring-shell
//TODO security

@RestController
@RequestMapping("price-data-analysis")
public class PriceDataAnalysisController {

    @Autowired
    private PriceDataAnalyzer priceDataAnalyzer;

    @Autowired
    private SymbolResolver symbolResolver;

    @Autowired
    private TrendFinderFactory trendFinderFactory;

    @Autowired
    private PriceRecordCreator priceRecordCreator;

    @Value("${java.io.tmpdir}")
    private String tempDir;

    @RequestMapping(method = RequestMethod.POST, params = {"strategy", "symbol", "minDifference"})
    public String priceDataAnalysis(
            @RequestPart("file") MultipartFile multipartFile,
            @RequestParam String symbol,
            @RequestParam(defaultValue = "HighLowAverage") String strategy,
            @RequestParam(defaultValue = "0.05") Double minDifference
    ) throws IOException {

        //TODO probably should run on separate thread
        //TODO make file optional. Then run analysis on existing data.
        //TODO Or create PriceRecord POST enpoint. if so remove 'file' and add 'start' and 'end' params here.

        File convertedFile = FileHelper.convertMultipartFileToTempFile(multipartFile, tempDir);

        Resource dataResource = new FileSystemResource(convertedFile);

        TrendFinderSettings trendFinderSettings = new TrendFinderSettings(minDifference);

        TrendFinderStrategy trendFinderStrategy = trendFinderFactory.getTrendFinderStrategy(strategy);

        Symbol symbolObject = symbolResolver.resolve(symbol);

        List<PriceRecord> priceRecords = priceRecordCreator.createPriceRecords(dataResource, symbolObject);

        PriceDataAnalysis priceDataAnalysis = priceDataAnalyzer.analyzePriceData(priceRecords, trendFinderStrategy, symbolObject, trendFinderSettings);

        return "Extracted " + priceDataAnalysis.getTrends().size() + " trends from " + dataResource.getFilename() + " with strategy " + priceDataAnalysis.getTrendFinderStrategyName();
    }

    /*
    //TODO this actions is supposed to handle url source
    @RequestMapping(method = RequestMethod.POST, params = {"source", "strategy", "symbol", "minDifference"})
    public String extractTrends(
            @RequestParam String source,
            @RequestParam String symbol,
            @RequestParam(defaultValue = "HighLowAverage") String strategy,
            @RequestParam(defaultValue = "0.05") Double minDifference
    ) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource dataResource = resourceLoader.getResource(source);

        return extractTrends(dataResource, strategy, symbol, minDifference);
    }*/



}