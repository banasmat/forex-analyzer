package com.jorm.forex.controller;

import com.jorm.forex.news_data.DailyFXNewsDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("news-source")
public class NewsSourceController {

    @Autowired
    private DailyFXNewsDataProvider newsApiOrgClient;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity newsSources() {

        //FIXME save once, then retrieve from repository
        return ResponseEntity.ok("");

    }

}
