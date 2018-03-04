package com.mvai.demo.shunt.strategy.impl;

import com.mvai.demo.shunt.model.Context;
import com.mvai.demo.shunt.model.Request;
import com.mvai.demo.shunt.strategy.ShuntStrategy;
import com.mvai.demo.shunt.strategy.Token;

import java.util.*;

public class HashStrategy implements ShuntStrategy {

    private List<Token> tokens = null;

    public HashStrategy(List<Token> tokens){
        setTokens(tokens);
    }

    @Override
    public Token getToken(Request request, Context context) {
        int hashCode = request.uuid.hashCode();
        int serverListSize = tokens.size();
        int serverPos = hashCode % serverListSize;
        return tokens.get(serverPos);

    }

    @Override
    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
}
