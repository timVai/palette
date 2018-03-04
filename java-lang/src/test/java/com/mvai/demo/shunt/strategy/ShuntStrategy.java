package com.mvai.demo.shunt.strategy;

import com.mvai.demo.shunt.model.Context;
import com.mvai.demo.shunt.model.Request;

import java.util.List;

public interface ShuntStrategy {

    public Token getToken(Request request,Context context);

    public void setTokens(List<Token> tokens);



}
