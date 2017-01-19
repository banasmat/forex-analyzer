package com.jorm.forex.price_data;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.util.Format;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class CsvDataProviderTest {

    private static DateTimeFormatter dateFormat = Format.dateTimeFormat;

    private CsvDataProvider dataProvider;

    @Before
    public void setUp() {
        dataProvider = new CsvDataProvider();
    }

    @Test
    public void shouldReadACsvFileAndReturnSortedMap(){
        ResourceLoader resourceLoader = new FileSystemResourceLoader();

        Resource resource = resourceLoader.getResource("src/test/resources/historical-data-chunk.csv");

        SortedMap<LocalDateTime, PriceRecord> expectedResult = new TreeMap<LocalDateTime, PriceRecord>(){
            {
                put(LocalDateTime.parse("03-01-2016 17:00:00", dateFormat), new PriceRecord(1.087010,1.087130,1.087010,1.087130));
                put(LocalDateTime.parse("03-01-2016 17:01:00", dateFormat), new PriceRecord(1.087120,1.087120,1.087120,1.087120));
                put(LocalDateTime.parse("03-01-2016 17:02:00", dateFormat), new PriceRecord(1.087080,1.087220,1.087080,1.087220));
                put(LocalDateTime.parse("03-01-2016 17:03:00", dateFormat), new PriceRecord(1.087170,1.087230,1.087170,1.087230));
                put(LocalDateTime.parse("03-01-2016 17:04:00", dateFormat), new PriceRecord(1.087180,1.087180,1.087110,1.087110));
                put(LocalDateTime.parse("03-01-2016 17:05:00", dateFormat), new PriceRecord(1.087030,1.087160,1.087010,1.087120));
            }
        };

        SortedMap<LocalDateTime, PriceRecord> result = dataProvider.getData(resource);

        //TODO might try implementing Comparable in PriceRecord instead
        for(Map.Entry<LocalDateTime, PriceRecord> entry : result.entrySet()){
            assertEquals(expectedResult.get(entry.getKey()).open, entry.getValue().open);
            assertEquals(expectedResult.get(entry.getKey()).high, entry.getValue().high);
            assertEquals(expectedResult.get(entry.getKey()).low, entry.getValue().low);
            assertEquals(expectedResult.get(entry.getKey()).close, entry.getValue().close);
        }
    }
}
