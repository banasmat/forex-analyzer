package com.jorm.forex.price_data;

import com.jorm.forex.model.*;
import com.jorm.forex.price_record.PriceRecordDuplicationChecker;
import com.jorm.forex.trend.TrendFinderProcessor;
import com.jorm.forex.trend.TrendFinderStrategy;
import com.jorm.forex.util.Format;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDateTime;
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
    private PriceRecordDuplicationChecker priceRecordDuplicationChecker;

    @Mock
    private EntityManager em;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp(){
        priceDataAnalyzer = new PriceDataAnalyzer(priceDataProviderServiceResolver, priceDataProviderFactory, trendFinderProcessor, priceRecordDuplicationChecker, em);
    }

    @Test
    public void shouldPerformDataAnalysis() throws IOException{

        Resource resource = new FileSystemResource("src/test/resources/empty-resource.txt");

        TrendFinderSettings trendFinderSettings = new TrendFinderSettings(999D);

        String service = "anything";

        Symbol symbol = new Symbol("whatever");

        List<PriceRecord> priceData = new ArrayList<>();
        PriceRecord start = new PriceRecord(LocalDateTime.parse("01-01-2001 00:00:00", Format.dateTimeFormatter), 1D,1D,1D,1D);
        PriceRecord end = new PriceRecord(LocalDateTime.parse("01-01-2001 00:00:00", Format.dateTimeFormatter), 1D,1D,1D,1D);
        List<Trend> trends = new ArrayList<>();

        trends.add(new Trend(start, end, symbol));

        when(priceDataProviderServiceResolver.resolveFromResource(resource)).thenReturn(service);
        when(priceDataProviderFactory.getPriceDataProvider(service)).thenReturn(priceDataProvider);
        when(priceDataProvider.getData(resource)).thenReturn(priceData);
        when(trendFinderProcessor.findTrendsInData(priceData)).thenReturn(trends);

        PriceDataAnalysis result = priceDataAnalyzer.analyzePriceData(resource, trendFinderStrategy, symbol, trendFinderSettings);

        assertEquals(symbol, result.getTrends().get(0).getSymbol());
        assertEquals(result, result.getTrends().get(0).getPriceDataAnalysis());
    }
}