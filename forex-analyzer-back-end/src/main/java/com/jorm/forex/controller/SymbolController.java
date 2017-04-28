package com.jorm.forex.controller;

import com.jorm.forex.repository.SymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("symbol")
public class SymbolController {

    @Autowired
    private SymbolRepository symbolRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity symbols(){
        return ResponseEntity.ok(symbolRepository.findAll());
    }

}
