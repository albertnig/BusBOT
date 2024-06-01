package main.station;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Belinskii29Later {
    private static Document getPage() throws IOException {
        String url = "https://navi.kazantransport.ru/wap/online/?st_id=363";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static Pattern pattern29 = Pattern.compile("(?<=(29\\s.{21}\\s\\d{2}:\\d{2}))(.+)");

    private static String getDateFromString29(String stringDate) throws Exception {
        Matcher matcher = pattern29.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }

    private static Pattern pattern29Later = Pattern.compile("29\\s\\D+\\s\\d{2}:\\d{2}");

    private static String getDateFromString29Later(String stringDate) throws Exception {
        Matcher matcher = pattern29Later.matcher(stringDate);
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

    private static Pattern patternTimeWait = Pattern.compile("\\d{2}$");

    private static String getDateFromStringTimeWait(String stringDate) throws Exception {
        Matcher matcher = patternTimeWait.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }
    public static String getBelinskii29Later() throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table").first();
        Elements names = tableWth.select("table");
        String dateString = names.select("td").text();
        String bus29 = getDateFromString29(dateString);
        String bus29Later = getDateFromString29Later(bus29);
        String bus291 = getDateFromStringNumber(bus29Later);
        String bus292 = getDateFromStringTime(bus29Later);
        return bus291 + " автобус прибудет в " + bus292;
    }

    public static int getBelinskii29LaterTimeWait() throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table").first();
        Elements names = tableWth.select("table");
        String dateString = names.select("td").text();
        String bus29 = getDateFromString29(dateString);
        String bus29Later = getDateFromString29Later(bus29);
        String bus29Time = getDateFromStringTimeWait(bus29Later);
        int minuteLater = Integer.parseInt(bus29Time);
        Calendar now = new GregorianCalendar();
        int minuteNow = now.get(Calendar.MINUTE);
        int time;
        if (minuteLater > minuteNow) {
            time = minuteLater - minuteNow;
        } else if (minuteLater < minuteNow) {
            time = minuteLater + 60 - minuteNow;
        } else {
            time = 0;
        }
        return time;
    }
}
