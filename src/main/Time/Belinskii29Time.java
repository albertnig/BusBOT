package main.Time;

import main.station.Belinskii29Early;
import main.station.Belinskii29Later;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.concurrent.*;

import static org.glassfish.grizzly.ProcessorExecutor.execute;

public class Belinskii29Time {

    public static String period29Early () throws ExecutionException, InterruptedException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        Callable<String> stringCallable = () -> {
            int waitEarly = Belinskii29Early.getBelinskii29EarlyTimeWait();
            int waitLater = Belinskii29Later.getBelinskii29LaterTimeWait();
            if (waitEarly>10) {
                Thread.sleep((10 - Belinskii29Early.getBelinskii29EarlyTimeWait())*60000);
                return "1 таймер с ожиданием";
            } else if (waitEarly == 10) {
                return "1 таймер без ожидания";
            } else if (waitLater>10) {
                Thread.sleep((10 - Belinskii29Later.getBelinskii29LaterTimeWait())*60000);
                return "2 таймер с ожиданием";
            } else if (waitLater == 10) {
                return "2 таймер без ожидания";
            } else
            return "Пора";
        };


        Future<String> stringFuture = executorService.submit(stringCallable);

        String result = stringFuture.get();
        System.out.println("Retrieved result from the task - " + result);

        executorService.shutdown();
        return "Выходи";
    }
}
