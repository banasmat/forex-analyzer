package com.jorm.forex.price_data;

import com.jorm.forex.model.*;
import com.jorm.forex.trend.TrendFinderFactory;
import com.jorm.forex.trend.TrendFinderProcessor;
import com.jorm.forex.trend.TrendFinderStrategy;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

//TODO Not written with TDD. Many dependencies, not simple enough. Might be hard to upkeep.
public class PriceDataAnalyzerTest {

    private PriceDataAnalyzer priceDataAnalyzer;

    @Mock
    private PriceDataProviderServiceResolver priceDataProviderServiceResolver;

    @Mock
    private PriceDataProviderFactory priceDataProviderFactory;

    @Mock
    private PriceDataProvider priceDataProvider;

    @Mock
    private TrendFinderProcessor trendFinderProcessor;

    @Mock
    private TrendFinderStrategy trendFinderStrategy;

    @Mock
    private EntityManager em;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp(){
        priceDataAnalyzer = new PriceDataAnalyzer(priceDataProviderServiceResolver, priceDataProviderFactory, trendFinderProcessor, em);
    }

    @Test
    public void shouldPerformDataAnalysis() throws IOException{

        Resource resource = new FileSystemResource("src/test/resources/empty-resource.txt");

        TrendFinderSettings trendFinderSettings = new TrendFinderSettings(999D);

        String service = "anything";

        Symbol symbol = new Symbol("whatever");

        List<PriceRecord> priceData = new ArrayList<>();
        List<Trend> trends = new ArrayList<>();
        List<PriceRecord> priceRecords = new ArrayList<>();

        trends.add(new Trend(priceRecords, symbol));

        when(priceDataProviderServiceResolver.resolveFromResource(resource)).thenReturn(service);
        when(priceDataProviderFactory.getPriceDataProvider(service)).thenReturn(priceDataProvider);
        when(priceDataProvider.getData(resource)).thenReturn(priceData);
        when(trendFinderProcessor.findTrendsInData(priceData)).thenReturn(trends);

        PriceDataAnalysis result = priceDataAnalyzer.analyzePriceData(resource, trendFinderStrategy, symbol, trendFinderSettings);

        assertEquals(symbol, result.trends.get(0).symbol);
        assertEquals(priceRecords, result.trends.get(0).priceRecords);
        assertEquals(result, result.trends.get(0).priceDataAnalysis);
    }
}