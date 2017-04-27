package com.jorm.forex.controller;

import com.jorm.forex.model.PriceRecord;
import com.jorm.forex.model.Symbol;
import com.jorm.forex.price_data.SymbolResolver;
import com.jorm.forex.price_record.IntervalResolver;
import com.jorm.forex.price_record.PriceRecordCondenser;
import com.jorm.forex.price_record.PriceRecordCreator;
import com.jorm.forex.repository.PriceRecordSearchService;
import com.jorm.forex.repository.SymbolRepository;
import com.jorm.forex.util.FileHelper;
import com.jorm.forex.util.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("price-record")
public class PriceRecordController {

    //TODO should return 400 codes when params are wrong

    private static final DateTimeFormatter dateFormat = Format.dateTimeFormatter;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private PriceRecordSearchService priceRecordSearchService;

    @Autowired
    private IntervalResolver intervalResolver;

    @Autowired
    private PriceRecordCondenser priceRecordCondenser;

    @Autowired
    private SymbolResolver symbolResolver;

    @Autowired
    private PriceRecordCreator priceRecordCreator;

    @Value("${java.io.tmpdir}")
    private String tempDir;

    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity priceRecords(
        @RequestParam String symbol,
        @RequestParam String start,
        @RequestParam String end,
        @RequestParam(defaultValue = "1H") String interval
    ){

        try{
            LocalDateTime startDate = LocalDateTime.parse(start, dateFormat);
            LocalDateTime endDate = LocalDateTime.parse(end, dateFormat);

            Symbol symbolObject = symbolRepository.findOneByName(symbol);

            if(null == symbolObject){
                //TODO invalid argument exception?
                //TODO create specific exception class?
                //TODO or create NullSymbol object and remove this if?
                throw new RuntimeException("Symbol: '" + symbol + "' not found.");
            }

            //TODO probably interval can't be applied in sql query. All rows have to be selected anyway according to http://stackoverflow.com/questions/2694429/how-do-i-get-every-nth-row-in-a-table-or-how-do-i-break-up-a-subset-of-a-table
            List<PriceRecord> allResults = priceRecordSearchService.findBySymbolBetweenDates(symbolObject, startDate, endDate);

            return  ResponseEntity.ok(priceRecordCondenser.condense(allResults, intervalResolver.resolve(interval)));

        } catch (DateTimeParseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + ". Correct date format: " + Format.dateTimeFormatString);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity priceRecords(
            @RequestParam String symbol,
            @RequestPart("file") MultipartFile multipartFile
    ) {
        try {
            File convertedFile = FileHelper.convertMultipartFileToTempFile(multipartFile, tempDir);

            Resource dataResource = new FileSystemResource(convertedFile);

            Symbol symbolObject = symbolResolver.resolve(symbol);

            List<PriceRecord> priceRecords = priceRecordCreator.createPriceRecords(dataResource, symbolObject);

            //TODO should return other http code if all price records already existed and none have been created?
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
