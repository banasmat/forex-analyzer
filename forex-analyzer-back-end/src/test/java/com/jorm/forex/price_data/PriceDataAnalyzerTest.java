package com.jorm.forex.price_data;

import com.jorm.forex.model.*;
import com.jorm.forex.price_record.PriceRecordCreator;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PriceDataAnalyzerTest {

    private PriceDataAnalyzer priceDataAnalyzer;

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
        priceDataAnalyzer = new PriceDataAnalyzer(trendFinderProcessor, em);
    }

    @Test
    public void shouldSavePriceDataAnalysisWithTrendsAndTrendFinderSettings() throws IOException{

        TrendFinderSettings trendFinderSettings = new TrendFinderSettings(999D);

        Symbol symbol = new Symbol("any_name");

        List<PriceRecord> priceData = new ArrayList<>();
        PriceRecord start = new PriceRecord(LocalDateTime.parse("01-01-2001 00:00:00", Format.dateTimeFormatter), 1D,1D,1D,1D);
        PriceRecord end = new PriceRecord(LocalDateTime.parse("01-01-2001 00:00:00", Format.dateTimeFormatter), 1D,1D,1D,1D);
        List<Trend> trends = new ArrayList<>();

        trends.add(new Trend(start, end, symbol));

        when(trendFinderProcessor.findTrendsInData(priceData)).thenReturn(trends);

        PriceDataAnalysis newPriceDataAnalysis = priceDataAnalyzer.analyzePriceData(priceData, trendFinderStrategy, symbol, trendFinderSettings);

        assertEquals(symbol, newPriceDataAnalysis.getTrends().get(0).getSymbol());
        assertEquals(newPriceDataAnalysis, newPriceDataAnalysis.getTrends().get(0).getPriceDataAnalysis());

        verify(em, times(1)).persist(newPriceDataAnalysis);
        verify(em, times(1)).flush();
    }
}