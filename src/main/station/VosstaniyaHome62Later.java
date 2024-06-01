package main.station;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VosstaniyaHome62Later {


    private static Document getPage() throws IOException {
        String url = "https://navi.kazantransport.ru/wap/online/?st_id=381";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static Pattern pattern62 = Pattern.compile("(?<=(62\\s.{31}\\s\\d{2}:\\d{2}))(.+)");

    private static String getDateFromString62(String stringDate) throws Exception {
        Matcher matcher = pattern62.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }

    private static Pattern pattern62Later = Pattern.compile("62\\s\\D+\\s\\d{2}:\\d{2}");

    private static String getDateFromString62Later(String stringDate) throws Exception {
        Matcher matcher = pattern62Later.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }

    private static Pattern patternNumber = Pattern.compile("\\d{2}");

    private static String getDateFromStringNumber(String stringDate) throws Exception {
        Matcher matcher = patternNumber.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }

    private static Pattern patternTime = Pattern.compile("\\d{2}:\\d{2}");

    private static String getDateFromStringTime(String stringDate) throws Exception {
        Matcher matcher = patternTime.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }


    public static String getVosstaniyaHome62Later() throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table").first();
        Elements names = tableWth.select("table");
        String dateString = names.select("td").text();
        String bus62 = getDateFromString62(dateString);
        String bus62Later = getDateFromString62Later(bus62);
        String bus621 = getDateFromStringNumber(bus62Later);
        String bus622 = getDateFromStringTime(bus62Later);
        return bus621 + " автобус прибудет в " + bus622;
    }
}
