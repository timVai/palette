package com.mvai.datastruct;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue blockingQueue = new BlockingQueue(5);
        blockingQueue.add("ele");
        System.out.println(blockingQueue.poll());


        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        blockingQueue.add("1");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        System.out.println(blockingQueue.poll());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        TimeUnit.SECONDS.sleep(10);
        System.exit(0);
    }





}


class BlockingQueue<E>{
    int maxSize = 0;
    Queue<E> queue;

    final Lock lock = new ReentrantLock(true);
    final Condition noFill = lock.newCondition();
    final Condition noEmpty = lock.newCondition();

    BlockingQueue(int maxSize){
        queue = new ArrayDeque();
        this.maxSize = maxSize;
    }

    public void add(E e) throws InterruptedException {
        lock.lock();
        try {
            if(queue.size() == maxSize){
                System.out.println("adding blocking");
                noFill.await();
            }
            queue.add(e);
            noEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public E poll() throws InterruptedException {
        lock.lock();
        try {
            if(queue.size() == 0){
                System.out.println("polling blocking");
                noEmpty.await();
            }
            E ele = queue.poll();
            noFill.signal();
            return ele;
        }finally {
            lock.unlock();
        }
    }
}