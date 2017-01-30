package com.jorm.forex.command;

import com.jorm.forex.model.Trend;
import com.jorm.forex.price_data.PriceDataProvider;
import com.jorm.forex.price_data.PriceDataProviderFactory;
import com.jorm.forex.trend.TrendFinderFactory;
import com.jorm.forex.trend.TrendFinderProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import org.springframework.shell.core.CommandMarker;

import java.util.ArrayList;

@Component
public class ExtractTrendsCommand implements CommandMarker {

    @Autowired
    private PriceDataProviderFactory priceDataProviderFactory;

    @Autowired
    private TrendFinderFactory trendFinderFactory;

    @Autowired
    private TrendFinderProcessor trendFinderProcessor;

    @CliAvailabilityIndicator({"extract-trends"})
    public boolean isCommandAvailable() {
        return true;
    }

    @CliCommand(value = "extract-trends", help = "Extracts trends from provided data source")
    public String ExtractTrends(
            @CliOption(key = { "source" }, mandatory = true, help = "Prices data source")
            final String source,

            //TODO might resolve type automatically
            @CliOption(key = { "sourceType" }, mandatory = true, help = "Type of the price data source")
            final String sourceType,

            @CliOption(key = { "strategy" }, mandatory = false, help = "Strategy for finding trends", specifiedDefaultValue="HighLowAverage")
            final String strategy) {

        //TODO validate inputs

        //TODO use service?
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource dataResource = resourceLoader.getResource(source);

        PriceDataProvider priceDataProvider = priceDataProviderFactory.getPriceDataProvider(sourceType);

        trendFinderProcessor.setTrendFinder(trendFinderFactory.getTrendFinder(strategy));

        ArrayList<Trend> trends = trendFinderProcessor.findTrendsInData(priceDataProvider.getData(dataResource));

        //TODO persist

        return "Extracted " + trends.size() + " trends from " + source + " with strategy " + strategy;

    }
}
