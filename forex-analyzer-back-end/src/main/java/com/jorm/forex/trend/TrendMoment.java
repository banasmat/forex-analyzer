package com.jorm.forex.trend;

public enum TrendMoment {
    BEGINNING("beginning"), END("end");

    TrendMoment(String trendMoment){
        this.trendMoment = trendMoment;
    }

    private String trendMoment;

    public String getTrendMoment(){
        return this.trendMoment;
    }

    public String toString(){
        return this.trendMoment;
    }

    public static TrendMoment fromValue(String input)
            throws IllegalArgumentException {

        for (TrendMoment trendMomentValue : TrendMoment.values()) {
            if (trendMomentValue.trendMoment.equalsIgnoreCase(input)) {
                return trendMomentValue;
            }
        }

        throw new IllegalArgumentException("Unknown enum value :"+ input);
    }
}
