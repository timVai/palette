package com.mvai.interest;

import java.util.concurrent.TimeUnit;

public class Demo {

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                int i = 0;
                while (i++ < 1000) {
                    System.out.println(Thread.currentThread().isInterrupted());
                }

                System.out.println("A1");
            } catch (Exception e) {
                System.out.println("B1");
            }
        });
        t1.start();
        TimeUnit.NANOSECONDS.sleep(10);
        t1.interrupt();
    }
}
