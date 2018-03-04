package com.mvai.demo.shunt.strategy.impl;

import com.mvai.demo.shunt.model.Context;
import com.mvai.demo.shunt.model.Request;
import com.mvai.demo.shunt.strategy.ShuntStrategy;
import com.mvai.demo.shunt.strategy.Token;

import java.util.List;

public class RoundRobinStrategy implements ShuntStrategy {

    private static Integer pos = 0;
    private List<Token> tokens = null;

    public RoundRobinStrategy(){

    }
    
    public RoundRobinStrategy(List<Token> tokens){
        setTokens(tokens);
    }

    public Token getToken(Request request, Context context) {
        Token token = null;
        synchronized (pos) {
            if (pos > tokens.size())
                pos = 0;
            token = tokens.get(pos);
            pos++;
        }
        return token;
    }

    public void setTokens(List<Token> tokens) {

    }
}
