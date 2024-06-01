package main;

import main.Time.Belinskii29Time;
import main.station.Belinskii29Early;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.SQLOutput;
import java.util.concurrent.*;

public class TimeTest {
////    private static void run() {
////        System.out.println("Running: " + new java.util.Date());
////    }
////
////    public static void main(String[] args)
////    {
////        ScheduledExecutorService executorService;
////        executorService = Executors.newSingleThreadScheduledExecutor();
////        executorService.scheduleAtFixedRate(TimeTest::run, 0, 1, TimeUnit.MINUTES);
////    }
//    //---------------------------------------------------------------------------------------------------
//
//    //    public static void main(String[] args) {
////        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
////        Runnable task = () -> {
////            System.out.println(Thread.currentThread().getName());
////        };
////        scheduledExecutorService.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);
////    }
////--------------------------------------------------------------------------------------------------------------
//
//    static int time = 100;
//    public static void run() {
//        try {
//            time = Belinskii29Early.getBelinskii29EarlyTimeWait();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//            System.out.println(time + " минут до выхода (Я ран)");
//        if (time == 6) {
//            System.out.println("Уже 5");
//            System.out.println("Завершена работа (Я майн)");
//        }
//    }
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.scheduleAtFixedRate(TimeTest::run, 0, 5, TimeUnit.SECONDS);
//        System.out.println("Я Майн");
//        if (time == 10) {
//            System.out.println("Уже 5");
//            System.out.println("Завершена работа (Я майн)");
//            System.out.println("Shutdown");
//            executorService.shutdown();
//        }
//    }
    //---------------------------------------------------------------------------------------------------------
//}
//class MyCallable implements Callable<Integer> {
//    @Override
//    public Integer call() throws Exception {
//        try {
//            TimeTest.time = Belinskii29Early.getBelinskii29EarlyTimeWait();
//            System.out.println("Started:" + Thread.currentThread().getId());
//            Thread.sleep(5000);
//            System.out.println("Finished:" + Thread.currentThread().getId());
//        }catch(InterruptedException ex){
//            ex.printStackTrace(System.out);
//        }
//        return TimeTest.time;
//    }
    //--------------------------------------------------------------------------------------------------------------

//    public static void main(String[] args) throws Exception {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        Callable<String> stringCallable = () -> {
//            Thread.sleep(1000);
//            return "hello edpresso";
//        };
//
//        Future<String> stringFuture = executorService.submit(stringCallable);
//        int count = 0;
//        while(!stringFuture.isDone()) {
//            Thread.sleep(200);
//            System.out.println(Belinskii29Early.getBelinskii29EarlyTimeWait());
//            count=Belinskii29Early.getBelinskii29EarlyTimeWait();
//            if(count == 4) stringFuture.cancel(true);
//        }
//
//        String result = stringFuture.get();
//        System.out.println(count + " Retrieved result from the task - " + result);
//        executorService.shutdown();
//    }

        public static void main(String[] args) throws InterruptedException, ExecutionException {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

            Callable<String> stringCallable = () -> {
              //  System.out.println(Thread.currentThread().getName());
                Thread.sleep(500);
                return "hello edpresso";
            };

     //       executorService.schedule(stringCallable, 2, TimeUnit.SECONDS);

            Future<String> stringFuture = executorService.submit(stringCallable);
        //    int count = 0;
          //  while(!stringFuture.isDone()) {
            //    Thread.sleep(1000);
              //  count++;
                //System.out.println(count);
                //if(count > 5) stringFuture.cancel(true);

       //     }

            String result = stringFuture.get();
            System.out.println("Retrieved result from the task - " + result);


            executorService.shutdown();
        }

    }


