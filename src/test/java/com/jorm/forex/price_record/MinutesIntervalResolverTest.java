package com.jorm.forex.price_record;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinutesIntervalResolverTest {

    private MinutesIntervalResolver resolver;

    @Before
    public void setUp(){
        resolver = new MinutesIntervalResolver();
    }

    @Test
    public void shouldReturn60Given1HParamIsPassed(){

        Integer expectedResult = 60;

        assertEquals(expectedResult, resolver.resolve("1H"));
        assertEquals(expectedResult, resolver.resolve("1h"));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionGivenInvalidParameterIsPassed(){
        resolver.resolve("xxd");
    }
}