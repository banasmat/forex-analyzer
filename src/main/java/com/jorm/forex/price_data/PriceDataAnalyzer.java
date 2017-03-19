package com.jorm.forex.price_data;

import com.jorm.forex.model.*;
import com.jorm.forex.trend.TrendFinderFactory;
import com.jorm.forex.trend.TrendFinderProcessor;
import com.jorm.forex.trend.TrendFinderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

//TODO rename to PriceDataAnalysisManager?
@Service
@Transactional
public class PriceDataAnalyzer {

    @Autowired
    private PriceDataProviderServiceResolver priceDataProviderServiceResolver;

    @Autowired
    private PriceDataProviderFactory priceDataProviderFactory;

    @Autowired
    private TrendFinderProcessor trendFinderProcessor;

    @Autowired
    private EntityManager em;

    public PriceDataAnalyzer(PriceDataProviderServiceResolver priceDataProviderServiceResolver, PriceDataProviderFactory priceDataProviderFactory, TrendFinderProcessor trendFinderProcessor, EntityManager em) {
        this.priceDataProviderServiceResolver = priceDataProviderServiceResolver;
        this.priceDataProviderFactory = priceDataProviderFactory;
        this.trendFinderProcessor = trendFinderProcessor;
        this.em = em;
    }

    public PriceDataAnalysis analyzePriceData(Resource dataResource, TrendFinderStrategy trendFinderStrategy, Symbol symbol, TrendFinderSettings trendFinderSettings ) throws IOException {

        String priceDataProviderName = priceDataProviderServiceResolver.resolveFromResource(dataResource);
        PriceDataProvider priceDataProvider = priceDataProviderFactory.getPriceDataProvider(priceDataProviderName);

        trendFinderStrategy.setSettings(trendFinderSettings);
        em.persist(trendFinderSettings);

        trendFinderProcessor.setTrendFinderStrategy(trendFinderStrategy);

        List<Trend> trends = trendFinderProcessor.findTrendsInData(priceDataProvider.getData(dataResource));

        PriceDataAnalysis priceDataAnalysis = new PriceDataAnalysis(trends, trendFinderStrategy, trendFinderSettings, new Date());

        //TODO setting these values here is inefficient.
        for(Trend trend : trends){
            trend.setSymbol(symbol);
            trend.setPriceDataAnalysis(priceDataAnalysis);
            //TODO Do we really have to set trend for every priceRecord. Check if it can't be configured with Hibernate.
            for(PriceRecord priceRecord : trend.getPriceRecords()){
                priceRecord.setTrend(trend);
            }
        }

        em.persist(priceDataAnalysis);
        em.flush();

        return priceDataAnalysis;
    }

}
