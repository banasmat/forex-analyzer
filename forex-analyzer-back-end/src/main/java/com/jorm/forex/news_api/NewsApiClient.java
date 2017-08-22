package com.jorm.forex.news_api;

import com.jorm.forex.model.News;

import java.time.LocalDateTime;

public interface NewsApiClient {

    public News[] getNewsByDate(LocalDateTime date);
}
