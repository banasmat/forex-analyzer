package com.jorm.forex.controller;

import com.jorm.forex.model.Trend;
import com.jorm.forex.price_data.PriceDataProvider;
import com.jorm.forex.price_data.PriceDataProviderFactory;
import com.jorm.forex.trend.TrendFinder;
import com.jorm.forex.trend.TrendFinderFactory;
import com.jorm.forex.trend.TrendFinderProcessor;
import com.jorm.forex.trend.TrendFinderSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TrendController {

    @Autowired
    private PriceDataProviderFactory priceDataProviderFactory;

    @Autowired
    private TrendFinderFactory trendFinderFactory;

    @Autowired
    private TrendFinderProcessor trendFinderProcessor;

    @RequestMapping("/")
    public String extractTrends(){

        String source = "s";
        String sourceType = "st";
        String strategy = "str";

        //TODO validate inputs

        //TODO use service?
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource dataResource = resourceLoader.getResource(source);

        PriceDataProvider priceDataProvider = priceDataProviderFactory.getPriceDataProvider(sourceType);

        TrendFinder trendFinder = trendFinderFactory.getTrendFinder(strategy);

        trendFinder.setSettings(new TrendFinderSettings(5d));

        trendFinderProcessor.setTrendFinder(trendFinder);

        ArrayList<Trend> trends = trendFinderProcessor.findTrendsInData(priceDataProvider.getData(dataResource));

        //TODO persist

        return "Extracted " + trends.size() + " trends from " + source + " with strategy " + strategy;

    }

}
