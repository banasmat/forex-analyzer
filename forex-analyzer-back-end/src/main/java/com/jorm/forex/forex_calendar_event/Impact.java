package com.jorm.forex.forex_calendar_event;

public enum Impact {
    HIGH("high"), MEDIUM("medium"), LOW("low");

    Impact(String impact){
        this.impact = impact;
    }

    private String impact;

    public String getImpact(){
        return this.impact;
    }

    public String toString(){
        return this.impact;
    }

    public static Impact fromValue(String input)
            throws IllegalArgumentException {

        for (Impact impactValue : Impact.values()) {
            if (impactValue.impact.equalsIgnoreCase(input)) {
                return impactValue;
            }
        }

        throw new IllegalArgumentException("Unknown enum value :"+ input);
    }
}
