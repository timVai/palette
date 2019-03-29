package com.mvai.concurrent;

public class ThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal();
        threadLocal.set("aaaa");
        System.out.println(threadLocal.get());
    }

}
