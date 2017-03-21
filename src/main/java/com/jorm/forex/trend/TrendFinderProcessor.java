package com.jorm.forex.trend;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Trend;
import org.springframework.stereotype.Service;

@Service
public class TrendFinderProcessor {

    private TrendFinderStrategy trendFinderStrategy;

    public void setTrendFinderStrategy(TrendFinderStrategy trendFinderStrategy){
        this.trendFinderStrategy = trendFinderStrategy;
    }

    public List<Trend> findTrendsInData(List<PriceRecord> data){

        List<Trend> extractedTrends = new ArrayList<>();

        extractTrendsRecursively(data, extractedTrends);

        return extractedTrends;
    }

    private List<Trend> extractTrendsRecursively(List<PriceRecord> data, List<Trend> extractedTrends){
        PriceRecord start = trendFinderStrategy.findTrendStart(data);

        if (null != start) {

            removeUntilTargetEntry(data, start);

            PriceRecord end = trendFinderStrategy.findTrendEnd(data);

            if (null != end) {

                removeUntilTargetEntry(data, end);

                final Trend trend = new Trend(
                        start,
                        end
                    );

                extractedTrends.add(trend);
                extractTrendsRecursively(data, extractedTrends);
            }
        }

        return extractedTrends;
    }

    private void removeUntilTargetEntry(List<PriceRecord> data, PriceRecord targetEntry) {

        Method compareOperator;

        try {
            compareOperator = LocalDateTime.class.getMethod("isEqual", ChronoLocalDateTime.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            for (Iterator<PriceRecord> iter = data.listIterator(); iter.hasNext(); ) {
                PriceRecord priceRecord = iter.next();

                if ((boolean)compareOperator.invoke(priceRecord.getDateTime(), targetEntry.getDateTime())) {
                    break;
                } else {
                    iter.remove();
                }
            }

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
