package com.jorm.forex.price_data;

import com.jorm.forex.model.PriceRecord;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.SortedMap;
import java.util.TreeMap;

public class CsvDataProvider implements PriceDataProvider {

    @Override
    public SortedMap<LocalDateTime, PriceRecord> getData(Resource resource) {
        SortedMap<LocalDateTime, PriceRecord> result = new TreeMap<>();

        BufferedReader br;
        String line;
        //TODO should be able to change it with parameter or should be auto resolved
        String csvSeparator = ",";
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

        try {
            br = new BufferedReader(new FileReader(resource.getFile()));

            while ((line = br.readLine()) != null) {

                String[] data = line.split(csvSeparator);

                result.put(
                    LocalDateTime.parse(data[0] + ' ' + data[1], dateTimeFormat),
                    new PriceRecord(Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]))
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to parse file: " + resource.getFilename());
        }

        return result;
    }
}
