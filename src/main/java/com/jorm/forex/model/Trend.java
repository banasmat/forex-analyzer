package com.jorm.forex.model;

import java.time.LocalDateTime;

public class Trend {

    public final LocalDateTime start;

    public final LocalDateTime end;

    public Trend(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public static class Builder {
        private LocalDateTime start;
        private LocalDateTime end;

        public Builder start(LocalDateTime srart) {
            this.start = srart;
            return this;
        }

        public Builder end(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public Trend build() {
            return new Trend(start, end);
        }
    }
}
