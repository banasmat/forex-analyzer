package com.jorm.forex.trend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.Map;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Trend;

public class TrendFinderProcessor {

    private TrendFinder trendFinder;

    TrendFinderProcessor(TrendFinder trendFinder){
        this.trendFinder = trendFinder;
    }


    public ArrayList<Trend> findTrendsInData(SortedMap<LocalDateTime, PriceRecord> data){

        ArrayList<Trend> extractedTrends = new ArrayList<>();

        extractTrendsRecursively(data, extractedTrends);

        return extractedTrends;
    }

    private ArrayList<Trend> extractTrendsRecursively(SortedMap<LocalDateTime, PriceRecord> data, ArrayList<Trend> extractedTrends){
        LocalDateTime startDate = trendFinder.findTrendStart(data);

        if (null != startDate) {

            clearAllEntriesBeforeDate(data, startDate);

            LocalDateTime endDate = trendFinder.findTrendEnd(data);

            if (null != endDate) {
                clearAllEntriesBeforeDate(data, endDate);

                final Trend trend = new Trend.Builder()
                    .start(startDate)
                    .end(endDate)
                    .build();

                extractedTrends.add(trend);

                extractTrendsRecursively(data, extractedTrends);
            }
        }

        return extractedTrends;
    }

    private void clearAllEntriesBeforeDate(SortedMap<LocalDateTime, PriceRecord> data, LocalDateTime date){
        for (Iterator<Map.Entry<LocalDateTime, PriceRecord>> it = data.entrySet().iterator(); it.hasNext();) {
            Map.Entry<LocalDateTime, PriceRecord> entry = it.next();
            if (entry.getKey().isEqual(date)) {
                break;
            } else {
                it.remove();
            }
        }
    }
}
