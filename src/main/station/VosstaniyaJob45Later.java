package main.station;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VosstaniyaJob45Later {

    private static Document getPage() throws IOException {
        String url = "https://navi.kazantransport.ru/wap/online/?st_id=121";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static Pattern pattern45 = Pattern.compile("(?<=(45\\s.{8}\\s\\d{2}:\\d{2}))(.+)");

    private static String getDateFromString45(String stringDate) throws Exception {
        Matcher matcher = pattern45.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }
    private static Pattern pattern45Later = Pattern.compile("45\\s\\D+\\s\\d{2}:\\d{2}");

    private static String getDateFromString45Later(String stringDate) throws Exception {
        Matcher matcher = pattern45Later.matcher(stringDate);
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

    public static String getVosstaniyaJob45Later() throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table").first();
        Elements names = tableWth.select("table");
        String dateString = names.select("td").text();
        String bus45 = getDateFromString45(dateString);
        String bus45Later = getDateFromString45Later(bus45);
        String bus451 = getDateFromStringNumber(bus45Later);
        String bus452 = getDateFromStringTime(bus45Later);
        return bus451 + " автобус прибудет в " + bus452;
    }
}
