package main.station;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Naberezhnaya49Early {
    private static Document getPage() throws IOException {
        String url = "https://navi.kazantransport.ru/wap/online/?st_id=208";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static Pattern patternNumber = Pattern.compile("^\\d{2}");

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

    private static Pattern pattern49 = Pattern.compile("49\\s\\D+\\s\\d{2}:\\d{2}");

    private static String getDateFromString49(String stringDate) throws Exception {
        Matcher matcher = pattern49.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }

    public static String getNaberezhnaya49() throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table").first();
        Elements names = tableWth.select("table");
        String dateString = names.select("td").text();
        String bus49 = getDateFromString49(dateString);
        String bus491 = getDateFromStringNumber(bus49);
        String bus492 = getDateFromStringTime(bus49);
        return bus491 + " автобус прибудет в " + bus492;
    }
}
