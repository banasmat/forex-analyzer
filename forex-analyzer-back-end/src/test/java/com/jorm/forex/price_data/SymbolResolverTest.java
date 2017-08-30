package com.jorm.forex.price_data;

import com.jorm.forex.model.Symbol;
import com.jorm.forex.repository.SymbolRepository;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SymbolResolverTest {

    private SymbolResolver resolver;

    @Mock
    private SymbolRepository symbolRepository;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp(){
        resolver = new SymbolResolver(symbolRepository);
    }

    @Test
    public void shouldReturnExistingSymbolIfItExistsInRepository() throws InvalidArgumentException {
        Symbol symbol = new Symbol("SomeName");

        when(symbolRepository.findOneByName("SomeName")).thenReturn(symbol);

        assertEquals(symbol, resolver.resolve("SomeName"));
    }

    @Test(expected = InvalidArgumentException.class)
    public void shouldThrowExceptionSymbolIfItDoesNotExistsInRepository() throws InvalidArgumentException {
        Symbol symbol = new Symbol("SomeName");

        when(symbolRepository.findOneByName("SomeName")).thenReturn(null);

        assertNotEquals(symbol, resolver.resolve("SomeName"));
        assertEquals(symbol.getName(), resolver.resolve("SomeName").getName());
    }
}