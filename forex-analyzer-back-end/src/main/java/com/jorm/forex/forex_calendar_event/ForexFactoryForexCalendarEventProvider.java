package com.jorm.forex.forex_calendar_event;

import com.jorm.forex.http.RestClient;
import com.jorm.forex.model.Currency;
import com.jorm.forex.model.ForexCalendarEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ForexFactory")
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
    public String getName() {
        return "ForexFactory";
    }

    @Override
    public List<ForexCalendarEvent> getNewsInDateTimeRange(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {

        List<ForexCalendarEvent> results = new ArrayList<>();

        LocalDateTime currentDateTime = LocalDateTime.of(dateTimeFrom.getYear(), dateTimeFrom.getMonth(), dateTimeFrom.getDayOfMonth(), 0, 0, 0 );
        LocalDateTime lastDayDateTime = LocalDateTime.of(dateTimeTo.getYear(), dateTimeTo.getMonth(), dateTimeTo.getDayOfMonth(), 0, 0, 0 );

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

        String url;
        String html;
        Document doc;

        LocalTime prevTime = null;

        do {
            url = urlGenerator.generate(currentDateTime);
            html = client.get(url);
            doc = Jsoup.parse(html);

            Elements rows = doc.select(".calendar_row");

            for(Element row : rows){

                String timeString = row.getElementsByClass("calendar__time")
                        .first()
                        .text()
                        .replace("am", "AM")
                        .replace("pm", "PM");

                LocalTime time;

                if(timeString.isEmpty()){
                    time = prevTime;
                } else {
                    try {
                        time = LocalTime.parse(timeString, timeFormatter);
                    } catch (DateTimeParseException e){
                        time = LocalTime.MIDNIGHT; // "All Day" / "Day 1" / "Day 2" etc.  TODO consider saving as whole day some other way //TODO test this scenario
                    }
                    prevTime = time;
                }

                if(currentDateTime.getDayOfMonth() == dateTimeFrom.getDayOfMonth()){
                    if(time.isBefore(dateTimeFrom.toLocalTime())){
                        continue;
                    }
                }

                if(currentDateTime.getDayOfMonth() == dateTimeTo.getDayOfMonth()){
                    if(time.isAfter(dateTimeTo.toLocalTime())){
                        break;
                    }
                }

                String title = row.getElementsByClass("calendar__event-title").first().text();

                LocalDateTime dateTime = LocalDateTime.of(currentDateTime.toLocalDate(), time);

                String eventId = row.attr("data-eventid");
                String eventUrl = url + "#detail=" + eventId;

                String currency = row.getElementsByClass("calendar__currency").first().text();

                String actual = row.getElementsByClass("calendar__actual").first().text();
                String previous = row.getElementsByClass("calendar__previous").first().text();
                String forecast = row.getElementsByClass("calendar__forecast").first().text();

                String impactClass = row.getElementsByClass("calendar__impact").first()
                        .getElementsByClass("calendar__impact-icon--screen").first()
                        .getElementsByAttribute("title").first().className();

                results.add(new ForexCalendarEvent(title, dateTime, eventUrl, Currency.fromValue(currency), actual, previous, forecast, Impact.fromValue(impactClass)));
            }

            currentDateTime = currentDateTime.plusDays(1);

        } while(currentDateTime.isBefore(lastDayDateTime) || currentDateTime.isEqual(lastDayDateTime));

        return results;
    }
}
