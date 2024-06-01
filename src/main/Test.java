package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test extends TelegramLongPollingBot {

    public Test() throws Exception {
    }

    @Override
    public String getBotUsername() {
        return "BelinskyBusBot";
    }

    @Override
    public String getBotToken() {
        return "5866059669:AAGUtxqA39HT8_F6SLurQcofqljpT3FvXYo";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                try {
                    execute(
                            SendMessage.builder().chatId(message.getChatId().toString()).text(getDate()).build()
                    );
                    execute(
                            SendMessage.builder().chatId(message.getChatId().toString()).text(Runner.getRunner()).build()
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //------------------------------------------------------------------------------------
    private static Document getPage() throws IOException {
        String url = "https://navi.kazantransport.ru/wap/online/?st_id=363";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static Pattern pattern = Pattern.compile("29\\s\\D+\\s\\d{2}:\\d{2}");

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
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

    public volatile static int time;

    public static String getDate() throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table").first();
        Elements names = tableWth.select("table");
        String dateString = names.select("td").text();
        String date = getDateFromString(dateString);

        String date2 = getDateFromString2(date);

        int minuteForecast = Integer.parseInt(date2);
        Calendar now = new GregorianCalendar();
        Date dateNow = now.getTime();
        System.out.println(dateNow);
        int minuteNow = now.get(Calendar.MINUTE);
//        int time;
        if (minuteForecast > minuteNow + 1) {
            time = minuteForecast - minuteNow - 2;
        } else if (minuteForecast < minuteNow) {
            time = minuteForecast + 60 - minuteNow - 2;
        } else {
            time = 0;
        }
        if (time == 1 || time == 21 || time == 31 || time == 41 || time == 51) {
            return time + " минута до прибытия автобуса";
        } else if (2 <= time && time <= 4 || 22 <= time && time <= 24 || 32 <= time && time <= 34 || 42 <= time && time <= 44 || 52 <= time && time <= 54) {
            return time + " минуты до прибытия автобуса";
        } else {
            return time + " минут до прибытия автобуса";
        }
    }

    //----------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(test);
//        MyThread myThread = new MyThread();
//        myThread.start();
        Thread thread = new Thread(new Runner());
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }
}

//class MyThread extends Thread {
//    public void run() {
//        try {
//            Test.getDate();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(time);
//        if (time > 10) {
//            int wait = time - 10;
//            try {
//                Thread.sleep(wait * 60000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public static String getMyThread() throws Exception {
//            return "Пора";
//    }
//}
class Runner implements Runnable {

    @Override
    public void run() {
        if (Test.time > 10) {
            int wait = Test.time - 10;
            try {
                Thread.sleep(wait * 60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Runner.getRunner();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getRunner() throws InterruptedException {
        if (Test.time>10){
            int wait = Test.time - 10;
            Thread.sleep(wait * 60000);
            return "Выход";
        } else
        return "";
    }

}
