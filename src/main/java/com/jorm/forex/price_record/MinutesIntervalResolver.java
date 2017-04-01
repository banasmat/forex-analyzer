package com.jorm.forex.price_record;

import org.springframework.stereotype.Service;

@Service
public class MinutesIntervalResolver {

    public Integer resolve(String stringInterval){

        switch(stringInterval.toUpperCase()){
            case("1M"):
                return 1;
            case("5M"):
                return 5;
            case("30M"):
                return 30;
            case("1H"):
                return 60;
            case("1D"):
                return 1440;
            default:
                throw new RuntimeException("Invalid interval parameter: " + stringInterval);
        }

    }

}
