package com.jorm.forex.price_data;

/**
 * Inspired by http://kh-yiu.blogspot.com/2013/04/spring-implementing-factory-pattern.html
 */
public interface PriceDataProviderFactory {
    PriceDataProvider getPriceDataProvider(String providerName);
}
