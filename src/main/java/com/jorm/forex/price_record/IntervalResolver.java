package com.jorm.forex.price_record;

import org.springframework.stereotype.Service;

@Service
public class IntervalResolver {

    public Interval resolve(String stringInterval){

        switch(stringInterval.toUpperCase()){
            case("1M"):
                return Interval.ONE_MINUTE;
            case("5M"):
                return Interval.FIVE_MINUTES;
            case("30M"):
                return Interval.HALF_HOUR;
            case("1H"):
                return Interval.ONE_HOUR;
            case("1D"):
                return Interval.ONE_DAY;
            default:
                throw new RuntimeException("Invalid interval parameter: " + stringInterval);
        }

    }

}
