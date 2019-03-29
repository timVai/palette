package com.mvai.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncAndLock {

    final static Object object = new Object();
    final static Lock lock = new ReentrantLock();
    final static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        ping2();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        TimeUnit.MILLISECONDS.sleep(500);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        pong2();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        TimeUnit.SECONDS.sleep(10);
        System.out.println("finishing");
        System.exit(0);
    }


    public static void ping1() throws InterruptedException {
        synchronized (object){
            System.out.println("ping");
            object.notify();
            object.wait();
        }

    }

    public static void pong1() throws InterruptedException {
        synchronized (object){
            System.out.println("pong");
            object.notify();
            object.wait();
        }

    }

    public static void ping2() throws InterruptedException {
        lock.lock();
        try {
            System.out.println("ping");
            condition.signal();
            condition.await();
        }finally {
            lock.unlock();
        }
    }

    public static void pong2() throws InterruptedException {
        lock.lock();
        try {
            System.out.println("pong");
            condition.signal();
            condition.await();
        }finally {
            lock.unlock();
        }
    }

}
