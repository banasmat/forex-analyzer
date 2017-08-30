package com.jorm.forex.price_data;

import com.jorm.forex.model.Symbol;
import com.jorm.forex.repository.SymbolRepository;
import com.sun.javaws.exceptions.InvalidArgumentException;
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

    public Symbol resolve(String symbolName) throws InvalidArgumentException {

        Symbol existingSymbol = symbolRepository.findOneByName(symbolName);

        if(null == existingSymbol){
            throw new InvalidArgumentException(new String[]{"Symbol " + symbolName + " does not exist"});
        }

        return existingSymbol;
    }
}
