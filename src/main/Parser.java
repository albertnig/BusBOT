package main;

import oracle.jrockit.jfr.StringConstantPool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static Document getPage() throws IOException {
        String url = "https://navi.kazantransport.ru/wap/online/?st_id=208";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static Pattern pattern = Pattern.compile("49\\s\\D+\\s\\d{2}:\\d{2}");

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }
    private static Pattern pattern49 = Pattern.compile("(?<=(49\\s.{13}\\s\\d{2}:\\d{2}))(.+)");

    private static String getDateFromString49(String stringDate) throws Exception {
        Matcher matcher = pattern49.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }

    private static Pattern pattern2 = Pattern.compile("\\d{2}$");

    private static String getDateFromString2(String stringDate) throws Exception {
        Matcher matcher = pattern2.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }

    public static void main(String[] args) throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table").first();
        Elements names = tableWth.select("table");
        String dateString = names.select("td").text();
        String date = getDateFromString(dateString);
        String date2 = getDateFromString2(date);

        String date49 = getDateFromString49(dateString);


        System.out.println(dateString);
        System.out.println(date);


        System.out.println(date49);
        //-----------------------------------------------------------------------------------
        int minuteForecast = Integer.parseInt(date2);
        Calendar now = new GregorianCalendar();
        Date dateNow = now.getTime();
        //System.out.println(dateNow);
        int minuteNow = now.get(Calendar.MINUTE);
        int time;
        if (minuteForecast >= minuteNow) {
            time = minuteForecast - minuteNow;
        } else {
            time = minuteForecast+60-minuteNow;
        }
//        if (time == 1 || time == 21 || time == 31 || time == 41 || time == 51) {
//            System.out.println(time + " минута до прибытия автобуса");
//        } else if (2 <= time && time <= 4 || 22 <= time && time <= 24 || 32 <= time && time <= 34 || 42 <= time && time <= 44 || 52 <= time && time <= 54) {
//            System.out.println(time + " минуты до прибытия автобуса");
//        } else {
//            System.out.println(time + " минут до прибытия автобуса");
//        }
    }
}