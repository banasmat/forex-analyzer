package com.jorm.forex.price_data;

import com.jorm.forex.model.*;
import com.jorm.forex.price_record.PriceRecordCreator;
import com.jorm.forex.repository.PriceRecordSearchService;
import com.jorm.forex.trend.TrendFinderProcessor;
import com.jorm.forex.trend.TrendFinderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

//TODO rename to PriceDataAnalysisManager? PriceDataAnalysisCreator?
@Service
@Transactional
public class PriceDataAnalyzer {

    @Autowired
    private TrendFinderProcessor trendFinderProcessor;

    @Autowired
    private EntityManager em;

    public PriceDataAnalyzer(TrendFinderProcessor trendFinderProcessor,  EntityManager em) {
        this.trendFinderProcessor = trendFinderProcessor;
        this.em = em;
    }

    public PriceDataAnalysis analyzePriceData(List<PriceRecord> priceRecords, TrendFinderStrategy trendFinderStrategy, Symbol symbol, TrendFinderSettings trendFinderSettings ) throws IOException {

        trendFinderStrategy.setSettings(trendFinderSettings);
        //TODO verify if we need to persist separately
        em.persist(trendFinderSettings);

        trendFinderProcessor.setTrendFinderStrategy(trendFinderStrategy);

        List<Trend> trends = trendFinderProcessor.findTrendsInData(priceRecords);

        PriceDataAnalysis priceDataAnalysis = new PriceDataAnalysis(trends, trendFinderStrategy, trendFinderSettings);

        //TODO might pass symbol and analysis to trendFinderProcessor instead.
        for(Trend trend : trends){
            trend.setSymbol(symbol);
            trend.setPriceDataAnalysis(priceDataAnalysis);
        }

        em.persist(priceDataAnalysis);
        em.flush();

        return priceDataAnalysis;
    }

}
