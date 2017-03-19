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
import java.util.*;

import static org.junit.Assert.*;

public class CsvPriceDataProviderTest {

    private static DateTimeFormatter dateFormat = Format.dateTimeFormat;

    private CsvPriceDataProvider dataProvider;

    @Before
    public void setUp() {
        dataProvider = new CsvPriceDataProvider();
    }

    @Test
    public void shouldParseCsvFile(){
        ResourceLoader resourceLoader = new FileSystemResourceLoader();

        Resource resource = resourceLoader.getResource("src/test/resources/historical-data-chunk.csv");

        List<PriceRecord> expectedResult = new ArrayList<PriceRecord>(){
            {
                add(new PriceRecord(LocalDateTime.parse("03-01-2016 17:00:00", dateFormat), 1.087010,1.087130,1.087010,1.087130));
                add(new PriceRecord(LocalDateTime.parse("03-01-2016 17:01:00", dateFormat), 1.087120,1.087120,1.087120,1.087120));
                add(new PriceRecord(LocalDateTime.parse("03-01-2016 17:02:00", dateFormat), 1.087080,1.087220,1.087080,1.087220));
                add(new PriceRecord(LocalDateTime.parse("03-01-2016 17:03:00", dateFormat), 1.087170,1.087230,1.087170,1.087230));
                add(new PriceRecord(LocalDateTime.parse("03-01-2016 17:04:00", dateFormat), 1.087180,1.087180,1.087110,1.087110));
                add(new PriceRecord(LocalDateTime.parse("03-01-2016 17:05:00", dateFormat), 1.087030,1.087160,1.087010,1.087120));
            }
        };

        List<PriceRecord> result = dataProvider.getData(resource);

        for(int i = 0; i < result.size(); i++)
        for(PriceRecord priceRecord : result){
            assertEquals(expectedResult.get(i).getOpen(), result.get(i).getOpen());
            assertEquals(expectedResult.get(i).getHigh(), result.get(i).getHigh());
            assertEquals(expectedResult.get(i).getLow(), result.get(i).getLow());
            assertEquals(expectedResult.get(i).getClose(), result.get(i).getClose());
        }
    }
}
