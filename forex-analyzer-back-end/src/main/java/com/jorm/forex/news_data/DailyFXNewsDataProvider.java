package com.jorm.forex.news_data;

import com.jorm.forex.http.RestClient;
import com.jorm.forex.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyFXNewsDataProvider implements NewsDataProvider {

    protected String baseUrl = "https://www.dailyfx.com/calendar";

    @Value("${newsApiOrgApiKey}")
    private String apiKey;

    @Autowired
    private RestClient client;

    @Autowired
    private DailyFXUrlGenerator urlGenerator;

    public DailyFXNewsDataProvider(RestClient client, DailyFXUrlGenerator urlGenerator) {
        this.client = client;
        this.urlGenerator = urlGenerator;
    }

    @Override
    public List<News> getNewsInDateTimeRange(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {

        //TODO generate url: week number


        return new ArrayList<>();
    }
}
