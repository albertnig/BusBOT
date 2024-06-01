package main.station;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Belinskii29Early {
    private static Document getPage() throws IOException {
        String url = "https://navi.kazantransport.ru/wap/online/?st_id=363";
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

    private static Pattern patternTimeWait = Pattern.compile("\\d{2}$");

    private static String getDateFromStringTimeWait(String stringDate) throws Exception {
        Matcher matcher = patternTimeWait.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }
    public static String getBelinskii29() throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table").first();
        Elements names = tableWth.select("table");
        String dateString = names.select("td").text();
        String bus29 = getDateFromString29(dateString);
        String bus291 = getDateFromStringNumber(bus29);
        String bus292 = getDateFromStringTime(bus29);
        return bus291 + " автобус прибудет в " + bus292;
    }
    public static int getBelinskii29EarlyTimeWait() throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table").first();
        Elements names = tableWth.select("table");
        String dateString = names.select("td").text();
        String bus29 = getDateFromString29(dateString);
        String bus29Time = getDateFromStringTimeWait(bus29);
        int minuteEarly = Integer.parseInt(bus29Time);
        Calendar now = new GregorianCalendar();
        int minuteNow = now.get(Calendar.MINUTE);
        int time;
        if (minuteEarly > minuteNow) {
           time = minuteEarly - minuteNow;
        } else if (minuteEarly < minuteNow) {
            time = minuteEarly + 60 - minuteNow;
        } else {
            time = 0;
        }
        //----------------------------------------------------
//        Calendar now = new GregorianCalendar();
//        //Date dateNow = now.getTime();
//        int minuteNow = now.get(Calendar.MINUTE);
//        int time;
//        if (minuteForecast > minuteNow + 1) {
//           time = minuteForecast - minuteNow - 2;
//        } else if (minuteForecast < minuteNow) {
//            time = minuteForecast + 60 - minuteNow - 2;
//        } else {
//            time = 0;
//        }
//        if (time == 1 || time == 21 || time == 31 || time == 41 || time == 51) {
//            return time + " минута до прибытия автобуса";
//        } else if (2 <= time && time <= 4 || 22 <= time && time <= 24 || 32 <= time && time <= 34 || 42 <= time && time <= 44 || 52 <= time && time <= 54) {
//            return time + " минуты до прибытия автобуса";
//        } else {
//            return time + " минут до прибытия автобуса";
//        }
        return time;
    }
}
