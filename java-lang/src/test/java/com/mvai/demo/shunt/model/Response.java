package com.mvai.demo.shunt.model;

public class Response<T> {



    public T value;


    public static class Builder{

        public static Response build(){
            return new Response();
        }

    }
}
