package com.jorm.forex.repository;

import com.jorm.forex.model.Symbol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymbolRepository extends CrudRepository<Symbol, Long> {

    List<Symbol> findAll();

    Symbol findOneByName(String name);
}
