package com.jorm.forex.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Trend {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

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
