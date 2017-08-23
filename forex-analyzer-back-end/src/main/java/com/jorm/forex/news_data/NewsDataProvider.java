package com.jorm.forex.news_data;

import com.jorm.forex.model.News;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsDataProvider {

    public List<News> getNewsInDateTimeRange(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
}
