package com.jorm.forex.price_data;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import static org.junit.Assert.*;

public class PriceDataProviderServiceResolverTest {
    private PriceDataProviderServiceResolver resolver;

    @Before
    public void setUp() {
        resolver = new PriceDataProviderServiceResolver();
    }

    @Test
    public void shouldResolveCsvFileType(){
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource dataResource = resourceLoader.getResource("src/test/resources/historical-data-chunk.csv");

        assertEquals(PriceDataProviderName.CSV, resolver.resolveFromResource(dataResource));
    }
}