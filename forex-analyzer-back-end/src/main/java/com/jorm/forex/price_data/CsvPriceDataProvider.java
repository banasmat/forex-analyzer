package com.jorm.forex.price_data;

import com.jorm.forex.model.PriceRecord;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service(PriceDataProviderName.CSV)
public class CsvPriceDataProvider implements PriceDataProvider {

    @Override
    public List<PriceRecord> getData(Resource resource) {
        List<PriceRecord> result = new ArrayList<>();

        BufferedReader br;
        String line;
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

        try {
            br = new BufferedReader(new FileReader(resource.getFile()));

            String csvSeparator = resolveSeparator(resource);

            while ((line = br.readLine()) != null) {

                String[] data = line.split(csvSeparator);

                LocalDateTime dateTime = LocalDateTime.parse(data[0] + ' ' + data[1], dateTimeFormat);

                result.add(
                    new PriceRecord(dateTime, Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]))
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to parse file: " + resource.getFilename());
        }

        return result;
    }

    private String resolveSeparator(Resource resource) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));
        String[] separators = new String[]{",", ";"};
        String[] data;
        String line = br.readLine();

        for(String separator : separators){
            data = line.split(separator);
            if(data.length >= 6){
                return separator;
            }
        }

        throw new RuntimeException("Unable to parse file. Unknown csv separator.");
    }
}
