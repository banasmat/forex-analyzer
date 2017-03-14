package com.jorm.forex.model;

import javax.persistence.*;

@Entity
public class Symbol {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    public String name;

    public Symbol(){}

    public Symbol(String name) {
        this.name = name;
    }
}