package com.mvai.demo.batch;

import com.mvai.demo.batch.entity.Calculation;
import com.mvai.demo.batch.entity.Result;

import java.util.LinkedList;
import java.util.concurrent.*;

public class MainController {

    static LinkedList<Calculation> calculations = new LinkedList<>();

    static ExecutorService executorService = new ThreadPoolExecutor(16,
            16,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(16);

    public static Future<Result> submit(final Calculation calculation){

        calculations.add(calculation);

//        executorService.submit()

        scheduledExecutorService.submit(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                return null;
            }
        });

        Future<Result> resultFuture = executorService.submit(new Callable<Result>() {

            @Override
            public Result call() throws Exception {

                return null;
            }
        });
        return resultFuture;
    }

}
