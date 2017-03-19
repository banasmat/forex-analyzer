package com.jorm.forex.controller;

import com.jorm.forex.model.*;
import com.jorm.forex.price_data.*;
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

    @Value("${java.io.tmpdir}")
    private String tempDir;

    @RequestMapping(method = RequestMethod.POST, params = {"strategy", "symbol", "minDifference", "entriesNumberMargin"})
    public String extractTrends(
            @RequestPart("file") MultipartFile multipartFile,
            @RequestParam String symbol,
            @RequestParam(defaultValue = "HighLowAverage") String strategy,
            @RequestParam(defaultValue = "0.05") Double minDifference,
            @RequestParam(defaultValue = "10") int entriesNumberMargin
    ) throws IOException {

        //TODO consider if entriesNumberMargin should be set or resolved automatically based on trend size
        /*
         * Options:
         *   - don't use margin for saving data. Front end application might want to request different margins for display. Options:
         *      - don't save price data here at all. Front end app will have it's own resource. (data duplication. there's a change that sources will differ)
         *      - keep file data as a whole file. Send it to front end once and store it in cache. (these files are big)
         *      - save all price data to db. Send requested records to front end.
         *          (
         *          - most flexible,
         *          - front end usually will request filtered data (5mins/1hr intervals) so responses won't be that large
         *          - but will take a lot of db space
         *          - [set index on data column; add PriceRecordTrendMap]
         *          )
     *       - save data with proper margin. Options:
     *          - save 10% data before start and 10% after end
     *          - set how much percent should be saved in TrendFinderSettings or set it in TrendFinderProcessor
     *          - set fixed amount
         */

        File convertedFile = FileHelper.convertMultipartFileToTempFile(multipartFile, tempDir);

        Resource dataResource = new FileSystemResource(convertedFile);

        TrendFinderSettings trendFinderSettings = new TrendFinderSettings(minDifference);

        TrendFinderStrategy trendFinderStrategy = trendFinderFactory.getTrendFinderStrategy(strategy);

        Symbol symbolObject = symbolResolver.resolve(symbol);

        PriceDataAnalysis priceDataAnalysis = priceDataAnalyzer.analyzePriceData(dataResource, trendFinderStrategy, symbolObject, trendFinderSettings);

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