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

    //TODO weak. what if there's no trend but big high/low differences
    //FIXME implement trend end

    public PriceRecord findTrendStart(List<PriceRecord> data) {

        PriceRecord result = null;

        Double min = null;
        Double max = null;

        PriceRecord minDateRecord = null;
        PriceRecord maxDateRecord = null;

        for(PriceRecord priceRecord : data){
            if (null == min || priceRecord.getLow() < min){
                min = priceRecord.getLow();
                minDateRecord = priceRecord;
            }

            if (null == max || priceRecord.getHigh() > max){
                max = priceRecord.getHigh();
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

        PriceRecord minDateRecord = null;
        PriceRecord maxDateRecord = null;

        Boolean isUpwards = null;

        Double previousMin = null;
        Double previousMax = null;

        for(PriceRecord priceRecord : data){

            if(null == previousMax){
                previousMax = priceRecord.getHigh();
            }

            if(null == previousMin){
                previousMin = priceRecord.getLow();
            } else if (null == isUpwards){
                if(priceRecord.getLow() > previousMin && priceRecord.getHigh() > previousMax){
                    isUpwards = true;
                } else if(priceRecord.getHigh() < previousMax && priceRecord.getHigh() < previousMax) {
                    isUpwards = false;
                }
            }

            if (null == min || priceRecord.getLow() < min){
                min = priceRecord.getLow();
                minDateRecord = priceRecord;
            }

            if (null == max || priceRecord.getHigh() > max){
                max = priceRecord.getHigh();
                maxDateRecord = priceRecord;
            }

            if(null != isUpwards){
                if(isUpwards && (max - priceRecord.getHigh()) >= settings.getMinPriceDifference()){
                    result = maxDateRecord;
                    break;
                } else if(!isUpwards && -(min - priceRecord.getLow()) >= settings.getMinPriceDifference()){
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

}
