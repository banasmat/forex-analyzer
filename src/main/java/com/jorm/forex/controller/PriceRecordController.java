package com.jorm.forex.controller;

import com.jorm.forex.util.Format;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@RestController
@RequestMapping("price-record")
public class PriceRecordController {

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormat;

    //TODO interval param
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> priceRecords(
        @RequestParam String start,
        @RequestParam String end
    ){

        try{
            LocalDateTime startDate = LocalDateTime.parse(start, dateFormat);

            return ResponseEntity.ok(startDate.toString());

        } catch (DateTimeParseException e){

            //TODO json error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + ". Correct date format: " + Format.dateTimeFormatString);
        }


    }
}
