package com.jorm.forex.trend;

/**
 * Inspired by http://kh-yiu.blogspot.com/2013/04/spring-implementing-factory-pattern.html
 */
public interface TrendFinderFactory {
    TrendFinderStrategy getTrendFinderStrategy(String finderName);
}
