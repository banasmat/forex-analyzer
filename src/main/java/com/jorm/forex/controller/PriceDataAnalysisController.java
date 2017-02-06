package com.jorm.forex.controller;

import com.jorm.forex.model.Trend;
import com.jorm.forex.price_data.PriceDataProvider;
import com.jorm.forex.price_data.PriceDataProviderFactory;
import com.jorm.forex.price_data.PriceDataProviderNameResolver;
import com.jorm.forex.trend.TrendFinderStrategy;
import com.jorm.forex.trend.TrendFinderFactory;
import com.jorm.forex.trend.TrendFinderProcessor;
import com.jorm.forex.model.TrendFinderSettings;
import com.jorm.forex.util.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//FIXME setup upload files temp dir
//TODO apply consistent naming
//TODO write a script for running this command http://stackoverflow.com/questions/39329017/how-to-build-console-command-in-spring-boot-web-application-using-spring-shell
//TODO apply security
//TODO we want to keep this functionality runnable from REST endpoint as we may be creating some front end admin panel in the future

@RestController
@RequestMapping("price-data-analysis")
public class PriceDataAnalysisController {

    @Autowired
    private PriceDataProviderNameResolver priceDataProviderNameResolver;

    @Autowired
    private PriceDataProviderFactory priceDataProviderFactory;

    @Autowired
    private TrendFinderFactory trendFinderFactory;

    @Autowired
    private TrendFinderProcessor trendFinderProcessor;

    @Value("${java.io.tmpdir}")
    private String tempDir;


    @RequestMapping(method = RequestMethod.POST, params = {"strategy"})
    public String extractTrends(
            @RequestPart("file") MultipartFile multipartFile,
            @RequestParam(defaultValue = "HighLowAverage") String strategy
    ) throws IOException {
        File convertedFile = FileHelper.convertMultipartFileToTempFile(multipartFile, tempDir);

        Resource dataResource = new FileSystemResource(convertedFile);
        return extractTrends(dataResource, strategy);
    }

    @RequestMapping(method = RequestMethod.POST, params = {"source", "strategy"})
    public String extractTrends(
            @RequestParam String source,
            @RequestParam(defaultValue = "HighLowAverage") String strategy
    ) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource dataResource = resourceLoader.getResource(source);

        return extractTrends(dataResource, strategy);
    }

    private String extractTrends(Resource dataResource, String strategy) throws IOException {

        String priceDataProviderName = priceDataProviderNameResolver.resolveFromResource(dataResource);

        PriceDataProvider priceDataProvider = priceDataProviderFactory.getPriceDataProvider(priceDataProviderName);

        TrendFinderStrategy trendFinderStrategy = trendFinderFactory.getTrendFinder(strategy);

        // TODO set from param
        trendFinderStrategy.setSettings(new TrendFinderSettings(0.005));

        trendFinderProcessor.setTrendFinderStrategy(trendFinderStrategy);

        ArrayList<Trend> trends = trendFinderProcessor.findTrendsInData(priceDataProvider.getData(dataResource));

        //TODO create PriceDataAnalysis record

        //TODO persist

        return "Extracted " + trends.size() + " trends from " + dataResource.getFilename() + " with strategy " + strategy;
    }

}