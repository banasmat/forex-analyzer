package com.jorm.forex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Symbol {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length=6)
    private String name;

    @Column(nullable = true)
    private Currency firstCurrency;

    @Column(nullable = true)
    private Currency secondCurrency;

    public Symbol(){}

    public Symbol(String name) {
        this.name = name;
    }

    public Symbol(Currency firstCurrency, Currency secondCurrency) {
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.name = firstCurrency.toString() + secondCurrency.toString();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Currency getFirstCurrency() {
        return firstCurrency;
    }

    public Currency getSecondCurrency() {
        return secondCurrency;
    }
}
