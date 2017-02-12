package com.jorm.forex.model;

import javax.persistence.*;

@Entity
public class Symbol {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    public final String name;

    public Symbol(String name) {
        this.name = name;
    }
}
