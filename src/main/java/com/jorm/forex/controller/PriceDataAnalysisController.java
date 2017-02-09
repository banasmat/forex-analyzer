package com.jorm.forex.controller;

import com.jorm.forex.model.Symbol;
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
import java.util.List;

//TODO apply consistent naming
//TODO write a script for running this command http://stackoverflow.com/questions/39329017/how-to-build-console-command-in-spring-boot-web-application-using-spring-shell
//TODO apply security
//TODO symbol

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
            @RequestParam String symbol,
            @RequestParam(defaultValue = "HighLowAverage") String strategy
    ) throws IOException {
        File convertedFile = FileHelper.convertMultipartFileToTempFile(multipartFile, tempDir);

        Resource dataResource = new FileSystemResource(convertedFile);
        return extractTrends(dataResource, strategy, symbol);
    }

    @RequestMapping(method = RequestMethod.POST, params = {"source", "strategy"})
    public String extractTrends(
            @RequestParam String source,
            @RequestParam String symbol,
            @RequestParam(defaultValue = "HighLowAverage") String strategy
    ) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource dataResource = resourceLoader.getResource(source);

        return extractTrends(dataResource, strategy, symbol);
    }

    private String extractTrends(Resource dataResource, String strategy, String _symbol) throws IOException {

        String priceDataProviderName = priceDataProviderNameResolver.resolveFromResource(dataResource);

        //FIXME resolve symbol
        Symbol symbol = new Symbol(_symbol);

        PriceDataProvider priceDataProvider = priceDataProviderFactory.getPriceDataProvider(priceDataProviderName);

        TrendFinderStrategy trendFinderStrategy = trendFinderFactory.getTrendFinder(strategy);

        TrendFinderSettings trendFinderSettings = new TrendFinderSettings(0.005);

        // TODO set from param
        trendFinderStrategy.setSettings(trendFinderSettings);

        trendFinderProcessor.setTrendFinderStrategy(trendFinderStrategy);

        List<Trend> trends = trendFinderProcessor.findTrendsInData(priceDataProvider.getData(dataResource));

        for(Trend trend : trends){
            trend.symbol = symbol;
        }

        //TODO create PriceDataAnalysis record

        //TODO persist

        return "Extracted " + trends.size() + " trends from " + dataResource.getFilename() + " with strategy " + strategy;
    }

}