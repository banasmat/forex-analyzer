package com.jorm.forex.news_data;

import com.jorm.forex.http.RestClient;
import com.jorm.forex.model.News;
import com.jorm.forex.util.Format;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        List<News> results = new ArrayList<>();

        //TODO array_unique of urls for every day between dates
        String url = urlGenerator.generate(dateTimeFrom);

        //TODO for each url make get call
        String html = client.get(url);

        Document doc = Jsoup.parse(html);
        Elements dateTimeElements = doc.select("td[id^=date]");

        //TODO get end part of date td id and use it to fetch title
        for(Element dateTimeElement : dateTimeElements){

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeElement.html());

            if((dateTime.isAfter(dateTimeFrom) || dateTime.isEqual(dateTimeFrom)) && (dateTime.isBefore(dateTimeTo) || dateTime.isEqual(dateTimeTo))){

                Element titleElement = doc.getElementById(dateTimeElement.id().replace("date", "title"));

                String title = titleElement.outerHtml().replace("\n", "");

                Pattern titlePattern = Pattern.compile("<td.+?[0-9]{2}:[0-9]{2}.+?</div>(.+?)</td>");
                Matcher matcher = titlePattern.matcher(title);

                Boolean find = matcher.find();

                if(find){
                    title = matcher.group(1);

                    // String url FIXME
                    results.add(new News(title, dateTime, this.getClass().toString(), url));
                }
            }
        }

        return results;
    }
}
