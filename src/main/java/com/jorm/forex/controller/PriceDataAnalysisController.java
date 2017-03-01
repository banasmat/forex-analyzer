package com.jorm.forex.controller;

import com.jorm.forex.model.*;
import com.jorm.forex.price_data.PriceDataProvider;
import com.jorm.forex.price_data.PriceDataProviderFactory;
import com.jorm.forex.price_data.PriceDataProviderNameResolver;
import com.jorm.forex.price_data.SymbolResolver;
import com.jorm.forex.repository.TrendRepository;
import com.jorm.forex.trend.TrendFinderStrategy;
import com.jorm.forex.trend.TrendFinderFactory;
import com.jorm.forex.trend.TrendFinderProcessor;
import com.jorm.forex.util.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

//TODO apply consistent naming
//TODO write a script for running this command http://stackoverflow.com/questions/39329017/how-to-build-console-command-in-spring-boot-web-application-using-spring-shell
//TODO security

@RestController
@RequestMapping("price-data-analysis")
@Transactional
public class PriceDataAnalysisController {

    @Autowired
    private PriceDataProviderNameResolver priceDataProviderNameResolver;

    @Autowired
    private PriceDataProviderFactory priceDataProviderFactory;

    @Autowired
    private TrendFinderFactory trendFinderFactory;

    @Autowired
    private TrendFinderProcessor trendFinderProcessor;

    @Autowired
    private SymbolResolver symbolResolver;

    @Autowired
    private EntityManager em;

    @Value("${java.io.tmpdir}")
    private String tempDir;

    @RequestMapping(method = RequestMethod.POST, params = {"strategy", "symbol", "minDifference"})
    public String extractTrends(
            @RequestPart("file") MultipartFile multipartFile,
            @RequestParam String symbol,
            @RequestParam(defaultValue = "HighLowAverage") String strategy,
            @RequestParam(defaultValue = "0.05") Double minDifference
    ) throws IOException {
        File convertedFile = FileHelper.convertMultipartFileToTempFile(multipartFile, tempDir);

        Resource dataResource = new FileSystemResource(convertedFile);
        return extractTrends(dataResource, strategy, symbol, minDifference);
    }

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
    }

    private String extractTrends(Resource dataResource, String strategyName, String symbolName, Double minDifference) throws IOException {

        // TODO probably delegate some parts to TrendManager(?) PriceDataAnalysisManager(?)

        String priceDataProviderName = priceDataProviderNameResolver.resolveFromResource(dataResource);

        Symbol symbol = symbolResolver.resolve(symbolName);

        PriceDataProvider priceDataProvider = priceDataProviderFactory.getPriceDataProvider(priceDataProviderName);

        TrendFinderStrategy trendFinderStrategy = trendFinderFactory.getTrendFinderStrategy(strategyName);

        TrendFinderSettings trendFinderSettings = new TrendFinderSettings(minDifference);
        trendFinderStrategy.setSettings(trendFinderSettings);
        em.persist(trendFinderSettings);

        trendFinderProcessor.setTrendFinderStrategy(trendFinderStrategy);

        List<Trend> trends = trendFinderProcessor.findTrendsInData(priceDataProvider.getData(dataResource));

        PriceDataAnalysis priceDataAnalysis = new PriceDataAnalysis(trends, strategyName, trendFinderSettings, new Date());

        //TODO setting these values here is inefficient. Do we really have to set trend for every priceRecord.
        for(Trend trend : trends){
            trend.symbol = symbol;
            trend.priceDataAnalysis = priceDataAnalysis;
            for(PriceRecord priceRecord : trend.priceRecords){
                priceRecord.trend = trend;
            }
        }

        em.persist(priceDataAnalysis);

        em.flush();

        return "Extracted " + trends.size() + " trends from " + dataResource.getFilename() + " with strategy " + strategyName ;
    }

}