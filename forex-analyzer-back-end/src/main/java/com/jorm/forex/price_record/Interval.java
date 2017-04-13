package com.jorm.forex.price_record;

public enum Interval {
    ONE_MINUTE(1),
    FIVE_MINUTES(5),
    HALF_HOUR(30),
    ONE_HOUR(60),
    ONE_DAY(1440);

    public final int minutes;

    Interval(int minutes) {
        this.minutes = minutes;
    }
}
