package com.jorm.forex.price_data;

import com.jorm.forex.model.Symbol;
import com.jorm.forex.repository.SymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO rename to SymbolConverter? SymbolTransformer? SymbolCreator? SymbolLoader?
//FIXME validate symbol before saving (get some list of accepted symbols). Probably save symbols separately and only retrieve here.
@Service
public class SymbolResolver {

    @Autowired
    private SymbolRepository symbolRepository;

    public SymbolResolver(){}

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
