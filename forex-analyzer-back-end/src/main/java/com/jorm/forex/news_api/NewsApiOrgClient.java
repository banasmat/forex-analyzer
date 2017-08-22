package com.jorm.forex.news_api;

import com.jorm.forex.model.News;
import com.jorm.forex.model.NewsSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class NewsApiOrgClient implements NewsApiClient {

    protected String baseUrl = "https://newsapi.org/v1/";

    @Value("${newsApiOrgApiKey}")
    private String apiKey;

    @Override
    public News[] getNewsByDate(LocalDateTime date) {
        return new News[0];
    }

    public NewsSource[] getNewsSources(){
//TODO move to separate ApiClientHandler
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NewsSource[]> responseEntity = restTemplate.getForEntity(baseUrl + "sources", NewsSource[].class);
        NewsSource[] objects = responseEntity.getBody();
        MediaType contentType = responseEntity.getHeaders().getContentType();
        HttpStatus statusCode = responseEntity.getStatusCode();

        return objects;
    }
}
