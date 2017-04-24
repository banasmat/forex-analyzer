package com.jorm.forex.controller;

import com.jorm.forex.model.Symbol;
import com.jorm.forex.repository.SymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("symbol")
public class SymbolController {

    @Autowired
    private SymbolRepository symbolRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Symbol> symbols(){
        return symbolRepository.findAll();
    }

}
