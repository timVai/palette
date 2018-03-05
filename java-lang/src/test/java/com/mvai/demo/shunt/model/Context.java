package com.mvai.demo.shunt.model;

import java.util.Map;

public class Context {

    public Request request;
    public Response response;
    public Throwable errorHandler; // catch any exceptions occurs in service


    public Map serviceParameterMap;
    public Map currentExperimentParameterMap;
}
