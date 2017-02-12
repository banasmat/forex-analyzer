package com.jorm.forex.repository;

import com.jorm.forex.model.Symbol;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SymbolRepository extends CrudRepository<Symbol, Long> {

    List<Symbol> findAll();

    Symbol findOneByName(String name);
}
