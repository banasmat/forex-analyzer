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

    @Autowired
    private RestClient client;

    @Value("${newsApiOrgApiKey}")
    private String apiKey;

    public DailyFXNewsDataProvider(RestClient client) {
        this.client = client;
    }

    @Override
    public List<News> getNewsInDateTimeRange(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        return new ArrayList<>();
    }
}
