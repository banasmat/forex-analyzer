package com.jorm.forex.price_record;

import com.jorm.forex.model.PriceRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PriceRecordCondenser {

    public List<PriceRecord> condense(List<PriceRecord> priceRecords, Interval interval){

        if(interval == Interval.ONE_MINUTE){
            return priceRecords;
        }

        List<PriceRecord> condensedPriceRecords = new ArrayList<>();

        Double open = 0D;
        Double high = 0D;
        Double low = 9999D;

        int minutesElapsed;
        int minutesElapsedAtRowEnd = 999;
        int lastRecordIndex = priceRecords.size() - 1;

        LocalDateTime baseDateTime = getBaseTime(priceRecords.get(0).getDateTime(), interval);
        LocalDateTime condensedPriceRecordDateTime;

        PriceRecord condensedPriceRecord;
        PriceRecord currentPriceRecord;
        PriceRecord closingPriceRecord;

        for(int i=0; i < priceRecords.size(); i++){

            currentPriceRecord = priceRecords.get(i);

            minutesElapsed = (int)baseDateTime.until(currentPriceRecord.getDateTime(), ChronoUnit.MINUTES);

            if(minutesElapsed > minutesElapsedAtRowEnd){

                closingPriceRecord = priceRecords.get(i-1);

                condensedPriceRecordDateTime = baseDateTime.plusMinutes(minutesElapsedAtRowEnd - interval.minutes);

                condensedPriceRecord = new PriceRecord(condensedPriceRecordDateTime, open, high, low, closingPriceRecord.getClose());
                condensedPriceRecord.setSymbol(closingPriceRecord.getSymbol()); //TODO consider adding Symbol param to constructor

                condensedPriceRecords.add(condensedPriceRecord);

                open = 0D;
                high = 0D;
                low = 9999D;
            }

            if(open == 0D){

                open = currentPriceRecord.getOpen();

                minutesElapsedAtRowEnd = roundDownToNearestIntervalMultiple(minutesElapsed + interval.minutes, interval.minutes);
            }

            if(currentPriceRecord.getHigh() > high){
                high = currentPriceRecord.getHigh();
            }

            if(currentPriceRecord.getLow() < low){
                low = currentPriceRecord.getLow();
            }

            if(i == lastRecordIndex){
                closingPriceRecord = priceRecords.get(i);

                //TODO remove code duplication
                condensedPriceRecordDateTime = baseDateTime.plusMinutes(minutesElapsedAtRowEnd - interval.minutes);

                condensedPriceRecord = new PriceRecord(condensedPriceRecordDateTime, open, high, low, closingPriceRecord.getClose());
                condensedPriceRecord.setSymbol(closingPriceRecord.getSymbol());

                condensedPriceRecords.add(condensedPriceRecord);
            }
        }

        return condensedPriceRecords;
    }

    private int roundDownToNearestIntervalMultiple(int num, int interval){
        return num - num % interval;
    }

    private LocalDateTime getBaseTime(LocalDateTime firstDateTime, Interval interval){

        LocalDateTime baseDateTime;

        switch(interval){
            case ONE_DAY:
                baseDateTime = LocalDateTime.of(
                        firstDateTime.toLocalDate(),
                        LocalTime.MIN
                );
                break;
            default:
                baseDateTime = firstDateTime.minusMinutes(firstDateTime.getMinute() % interval.minutes);
        }

        return baseDateTime;
    }
}
