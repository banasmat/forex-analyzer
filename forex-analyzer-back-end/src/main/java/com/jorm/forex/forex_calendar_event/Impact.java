package com.jorm.forex.forex_calendar_event;

public enum Impact {
    HIGH("high"), MEDIUM("medium"), LOW("low");

    private Impact(String impact){
        this.impact = impact;
    }

    private String impact;

    public String getImpact(){
        return this.impact;
    }

    public String toString(){
        return this.impact;
    }
}
