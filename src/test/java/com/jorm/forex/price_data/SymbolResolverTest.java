package com.jorm.forex.price_data;

import com.jorm.forex.model.Symbol;
import com.jorm.forex.repository.SymbolRepository;
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
    public void shouldReturnExistingSymbolIfItExistsInRepository(){
        Symbol symbol = new Symbol("SomeName");

        when(symbolRepository.findOneByName("SomeName")).thenReturn(symbol);

        assertEquals(symbol, resolver.resolve("SomeName"));
    }

    @Test
    public void shouldInstantiateAndSaveSymbolIfItDoesNotExistsInRepository(){
        Symbol symbol = new Symbol("SomeName");

        when(symbolRepository.findOneByName("SomeName")).thenReturn(null);

        assertNotEquals(symbol, resolver.resolve("SomeName"));
        assertEquals(symbol.name, resolver.resolve("SomeName").name);

        ArgumentCaptor<Symbol> symbolArgument = ArgumentCaptor.forClass(Symbol.class);
        verify(symbolRepository, times(2)).save(symbolArgument.capture());
    }
}