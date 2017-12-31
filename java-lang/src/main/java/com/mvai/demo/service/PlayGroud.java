package com.mvai.demo.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by fanfengshi on 2017/12/29.
 */
public class PlayGroud {


    private ExecutorService executorService = new ThreadPoolExecutor(5,
            500,
            1,
            TimeUnit.MINUTES,
            null,
            new ThreadPoolExecutor.AbortPolicy());

}
