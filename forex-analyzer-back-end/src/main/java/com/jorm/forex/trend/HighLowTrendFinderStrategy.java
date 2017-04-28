package com.jorm.forex.trend;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.TrendFinderSettings;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO HighLowExtreme?
@Service("HighLow")
public class HighLowTrendFinderStrategy implements TrendFinderStrategy {

    private TrendFinderSettings settings;

    public HighLowTrendFinderStrategy() {}
    public HighLowTrendFinderStrategy(TrendFinderSettings settings) {
        this.settings = settings;
    }

    //TODO rethink - how to inject settings (might register settings as a service...)
    public TrendFinderStrategy setSettings(TrendFinderSettings settings) {
        this.settings = settings;
        return this;
    }

    public PriceRecord findTrendStart(List<PriceRecord> data) {

        PriceRecord result = null;

        Double min = null;
        Double max = null;
        Double current;

        PriceRecord minDateRecord = null;
        PriceRecord maxDateRecord = null;

        for(PriceRecord priceRecord : data){
            current = getAverage(priceRecord);

            if (null == min || current < min){
                min = current;
                minDateRecord = priceRecord;
            }

            if (null == max || current > max){
                max = current;
                maxDateRecord = priceRecord;
            }

            if((max - min) >= settings.getMinPriceDifference()){
                // Return earlier date
                result = minDateRecord.getDateTime().isBefore(maxDateRecord.getDateTime()) ? minDateRecord : maxDateRecord;
                break;
            }
        }

        return result;
    }

    public PriceRecord findTrendEnd(List<PriceRecord> data) {

        PriceRecord result = null;

        Double min = null;
        Double max = null;
        Double current;

        PriceRecord minDateRecord = null;
        PriceRecord maxDateRecord = null;

        Boolean isUpwards = null;

        Double previousValue = null;

        for(PriceRecord priceRecord : data){
            current = getAverage(priceRecord);

            if(null == previousValue){
                previousValue = current;
            } else if (null == isUpwards){
                if(current > previousValue){
                    isUpwards = true;
                } else if(current < previousValue) {
                    isUpwards = false;
                }
            }

            if (null == min || current < min){
                min = current;
                minDateRecord = priceRecord;
            }

            if (null == max || current > max){
                max = current;
                maxDateRecord = priceRecord;
            }

            if(null != isUpwards){
                if(true == isUpwards && (max - current) >= settings.getMinPriceDifference()){
                    result = maxDateRecord;
                    break;
                } else if(false == isUpwards && -(min - current) >= settings.getMinPriceDifference()){
                    result = minDateRecord;
                    break;
                }
            }
        }

        return result;
    }

    public String getName(){
        return "HighLow";
    }

    private Double getAverage(PriceRecord prices){
        return (prices.getHigh() + prices.getLow()) / 2;
    }
}
