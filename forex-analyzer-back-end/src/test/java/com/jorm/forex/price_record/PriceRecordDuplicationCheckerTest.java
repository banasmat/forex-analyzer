package com.jorm.forex.price_record;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.repository.PriceRecordSearchService;
import com.jorm.forex.util.Format;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

//TODO reconsider. These checks are pretty weak.

public class PriceRecordDuplicationCheckerTest {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormatter;

    private PriceRecordDuplicationChecker checker;

    @Mock
    private PriceRecordSearchService priceRecordSearchService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private List<PriceRecord> inputData = new ArrayList<PriceRecord>(){
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
        checker = new PriceRecordDuplicationChecker(priceRecordSearchService);
    }

    @Test
    public void shouldReturnTrueIfSameNumberOfRecordsExistBetweenGivenDatesForGivenSymbol(){

        Symbol symbol = new Symbol();

        when(priceRecordSearchService.countAllWithSymbolBetweenIncludedDates(
                symbol,
                inputData.get(0).getDateTime(),
                inputData.get(inputData.size()-1).getDateTime()
            )).thenReturn(10L);

        assertTrue(checker.checkIfPriceRecordsExist(inputData, symbol));
    }

    @Test
    public void shouldReturnFalseIfNoRecordsExistBetweenGivenDatesForGivenSymbol(){

        Symbol symbol = new Symbol();

        when(priceRecordSearchService.countAllWithSymbolBetweenIncludedDates(
                symbol,
                inputData.get(0).getDateTime(),
                inputData.get(inputData.size()-1).getDateTime()
            )).thenReturn(0L);

        assertFalse(checker.checkIfPriceRecordsExist(inputData, symbol));
    }

    //TODO what if more/less records exist? think about it.

}