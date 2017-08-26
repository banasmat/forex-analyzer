package com.jorm.forex.forex_calendar_event;

import com.jorm.forex.http.RestClient;
import com.jorm.forex.model.ForexCalendarEvent;
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

/**\
 * CLASS NOT FINISHED. We decided not to use this provider for now.
 */
@Service
public class ForexFactoryForexCalendarEventProvider implements ForexCalendarEventProvider {

    @Value("${newsApiOrgApiKey}")
    private String apiKey;

    @Autowired
    private RestClient client;

    @Autowired
    private ForexCalendarEventProviderUrlGenerator urlGenerator;

    public ForexFactoryForexCalendarEventProvider(RestClient client, ForexCalendarEventProviderUrlGenerator urlGenerator) {
        this.client = client;
        this.urlGenerator = urlGenerator;
    }

    @Override
    public List<ForexCalendarEvent> getNewsInDateTimeRange(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {

        List<ForexCalendarEvent> results = new ArrayList<>();

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

                    //FIXME these news have body when clicked

                    // String url FIXME
                    //results.add(new ForexCalendarEvent(title, dateTime, this.getClass().toString(), url, "", "", "", "", "", ""));
                }
            }
        }

        return results;
    }
}
