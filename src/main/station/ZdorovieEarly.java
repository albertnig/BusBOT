package main.station;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZdorovieEarly {
    private static Document getPage() throws IOException {
        String url = "https://navi.kazantransport.ru/wap/online/?st_id=372";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static Pattern pattern29 = Pattern.compile("29\\s\\D+\\s\\d{2}:\\d{2}");

    private static String getDateFromString29(String stringDate) throws Exception {
        Matcher matcher = pattern29.matcher(stringDate);
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

    public static String getZdorovie() throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table").first();
        Elements names = tableWth.select("table");
        String dateString = names.select("td").text();
        String bus29 = getDateFromString29(dateString);
        String bus291 = getDateFromStringNumber(bus29);
        String bus292 = getDateFromStringTime(bus29);
        return bus291 + " автобус прибудет в " + bus292;
    }
}
