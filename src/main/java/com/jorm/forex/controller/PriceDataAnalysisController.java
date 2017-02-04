package com.jorm.forex.controller;

import com.jorm.forex.model.Trend;
import com.jorm.forex.price_data.PriceDataProvider;
import com.jorm.forex.price_data.PriceDataProviderFactory;
import com.jorm.forex.trend.TrendFinder;
import com.jorm.forex.trend.TrendFinderFactory;
import com.jorm.forex.trend.TrendFinderProcessor;
import com.jorm.forex.trend.TrendFinderSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//FIXME setup upload files temp dir
//TODO apply consistent naming
//TODO write a script for running this command http://stackoverflow.com/questions/39329017/how-to-build-console-command-in-spring-boot-web-application-using-spring-shell
//TODO apply security
//TODO we want to keep this functionality runnable from REST endpoint as we may be creating some front end admin panel in the future

/*
Notes

 Url construction:
  - we can a domain entity: trend search (recording time, seaerch strategy, search strategy settings, source
    - which verb to use? we're not posting anything to server. We're ordering server to perform some action
  - trend/
 */


@RestController
@RequestMapping("price-data-analysis")
public class PriceDataAnalysisController {

    @Autowired
    private PriceDataProviderFactory priceDataProviderFactory;

    @Autowired
    private TrendFinderFactory trendFinderFactory;

    @Autowired
    private TrendFinderProcessor trendFinderProcessor;

    @RequestMapping(method = RequestMethod.POST, params = {"strategy"})
    public String extractTrends(
            @RequestPart("file") MultipartFile file,
            @RequestParam(defaultValue = "HighLowAverage") String strategy
    ) throws IOException {
        File convertedFile = convertMultipartFileToFile(file);

        Resource dataResource = new FileSystemResource(convertedFile);
        return extractTrends(dataResource, strategy);
    }

    @RequestMapping(method = RequestMethod.POST, params = {"source", "strategy"})
    public String extractTrends(
            @RequestParam String source,
            @RequestParam(defaultValue = "HighLowAverage") String strategy
    ) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource dataResource = resourceLoader.getResource(source);

        return extractTrends(dataResource, strategy);
    }

    protected String extractTrends(Resource dataResource, String strategy) {

        //TODO use file type resolver
        String sourceType = "Csv";
        PriceDataProvider priceDataProvider = priceDataProviderFactory.getPriceDataProvider(sourceType);

        TrendFinder trendFinder = trendFinderFactory.getTrendFinder(strategy);

        trendFinder.setSettings(new TrendFinderSettings(0.005));

        trendFinderProcessor.setTrendFinder(trendFinder);

        ArrayList<Trend> trends = trendFinderProcessor.findTrendsInData(priceDataProvider.getData(dataResource));

        //TODO persist

        return "Extracted " + trends.size() + " trends from " + dataResource.getFilename() + " with strategy " + strategy;
    }


    //TODO should probably be moved to some helper
    protected File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}