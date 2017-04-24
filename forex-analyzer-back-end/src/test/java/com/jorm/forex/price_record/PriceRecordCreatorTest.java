package com.jorm.forex.price_record;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.price_data.PriceDataProvider;
import com.jorm.forex.price_data.PriceDataProviderFactory;
import com.jorm.forex.price_data.PriceDataProviderServiceResolver;
import com.jorm.forex.repository.PriceRecordSearchService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PriceRecordCreatorTest {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormatter;

    private PriceRecordCreator creator;

    @Mock
    private PriceRecordSearchService priceRecordSearchService;

    @Mock
    private PriceDataProviderServiceResolver priceDataProviderServiceResolver;

    @Mock
    private PriceDataProviderFactory priceDataProviderFactory;

    @Mock
    private PriceDataProvider priceDataProvider;

    @Mock
    private EntityManager em;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private List<PriceRecord> newPriceRecords = new ArrayList<PriceRecord>(){
        {
            add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:00:00", dateFormat), 1.5D, 1D, 1D, 0.6));
            add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:01:00", dateFormat), 1.5D, 1D, 1D, 0.6));
            add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:02:00", dateFormat), 1D, 3D, 1D, 1D));
            add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:03:00", dateFormat), 1D, 1.2, 0.5, 1D));
            add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:04:00", dateFormat), 1D, 1D, 1D, 2D));
            add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:05:00", dateFormat), 2.5D, 4D, 1D, 1D));
            add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:06:00", dateFormat), 1D, 1D, 1D, 1D));
            add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:07:00", dateFormat), 1D, 1D, 1D, 1D));
            add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:09:00", dateFormat), 1D, 1D, 0.4, 1D));
            add(new PriceRecord(LocalDateTime.parse("01-04-1234 00:10:00", dateFormat), 1D, 1D, 1D, 1D));
        }
    };

    @Before
    public void setUp(){
        creator = new PriceRecordCreator(priceRecordSearchService, priceDataProviderServiceResolver, priceDataProviderFactory, em);
    }

    @Test
    public void shouldInstantiateAndSavePriceRecordsIfTheyNotAlreadyExist(){

        Resource resource = new FileSystemResource("src/test/resources/empty-resource.txt");

        Symbol symbol = new Symbol();
        String service = "any_service_name";
        List<PriceRecord> alreadyExistingPriceRecords = new ArrayList<>();

        when(priceDataProviderServiceResolver.resolveFromResource(resource)).thenReturn(service);
        when(priceDataProviderFactory.getPriceDataProvider(service)).thenReturn(priceDataProvider);
        when(priceDataProvider.getData(resource)).thenReturn(newPriceRecords);

        when(priceRecordSearchService.findBySymbolBetweenDates(
                symbol,
                newPriceRecords.get(0).getDateTime(),
                newPriceRecords.get(newPriceRecords.size()-1).getDateTime()
        )).thenReturn(alreadyExistingPriceRecords);

        assertEquals(newPriceRecords, creator.createPriceRecords(resource, symbol));

        for(PriceRecord newPriceRecord : newPriceRecords){
            verify(em, times(1)).persist(newPriceRecord);
        }
        verify(em, times(1)).flush();
    }

    @Test
    public void shouldInstantiateAndNotSavePriceRecordsIfTheyAlreadyExist(){

        Resource resource = new FileSystemResource("src/test/resources/empty-resource.txt");

        Symbol symbol = new Symbol();
        String service = "any_service_name";
        List<PriceRecord> alreadyExistingPriceRecords = newPriceRecords;

        when(priceDataProviderServiceResolver.resolveFromResource(resource)).thenReturn(service);
        when(priceDataProviderFactory.getPriceDataProvider(service)).thenReturn(priceDataProvider);
        when(priceDataProvider.getData(resource)).thenReturn(newPriceRecords);

        when(priceRecordSearchService.findBySymbolBetweenDates(
                symbol,
                newPriceRecords.get(0).getDateTime(),
                newPriceRecords.get(newPriceRecords.size()-1).getDateTime()
        )).thenReturn(alreadyExistingPriceRecords);

        assertEquals(newPriceRecords, creator.createPriceRecords(resource, symbol));

        for(PriceRecord newPriceRecord : newPriceRecords){
            verify(em, times(0)).persist(newPriceRecord);
        }
        verify(em, times(0)).flush();
    }

    //FIXME if some price data already exist, save only new

}