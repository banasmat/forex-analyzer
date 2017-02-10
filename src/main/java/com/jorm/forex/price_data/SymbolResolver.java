package com.jorm.forex.price_data;

import com.jorm.forex.model.Symbol;
import com.jorm.forex.repository.SymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SymbolResolver {

    private SymbolRepository symbolRepository;

    @Autowired
    public SymbolResolver(SymbolRepository symbolRepository) {
        this.symbolRepository = symbolRepository;
    }

    public Symbol resolve(String symbolName){

        Symbol existingSymbol = symbolRepository.findOneByName(symbolName);

        if(null == existingSymbol){
            Symbol newSymbol = new Symbol(symbolName);
            symbolRepository.save(newSymbol);
            return newSymbol;
        }

        return existingSymbol;
    }
}
