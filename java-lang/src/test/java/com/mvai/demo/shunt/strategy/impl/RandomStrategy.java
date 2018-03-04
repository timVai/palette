package com.mvai.demo.shunt.strategy.impl;

import com.mvai.demo.shunt.model.Context;
import com.mvai.demo.shunt.model.Request;
import com.mvai.demo.shunt.strategy.ShuntStrategy;
import com.mvai.demo.shunt.strategy.Token;

import java.util.*;

public class RandomStrategy implements ShuntStrategy {

    private List<Token> tokens = null;

    public RandomStrategy(List<Token> tokens){
        setTokens(tokens);
    }

    @Override
    public Token getToken(Request request, Context context) {
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(tokens.size());

        return tokens.get(randomPos);
    }

    @Override
    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
}
