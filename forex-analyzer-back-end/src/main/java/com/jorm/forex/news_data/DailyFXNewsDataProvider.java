package com.jorm.forex.news_data;

import com.jorm.forex.model.News;
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

    @Override
    public List<News> getNewsInDateTimeRange(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        return new ArrayList<>();
    }
}
