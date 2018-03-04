package com.mvai.demo.shunt.strategy;

import com.mvai.demo.shunt.strategy.impl.RoundRobinStrategy;
import org.assertj.core.util.Lists;

import java.util.List;

public class ShuntStrategyFactory {

    public enum Strategy{
        DEFAULT,ROUND_ROBIN,RANDOM,BY_UUID
    }


    public static ShuntStrategy getShuntStrategy(Strategy strategy){

        switch (strategy){
            case DEFAULT:
                break;
            case ROUND_ROBIN:
                break;
            case RANDOM:
                break;
            case BY_UUID:
                return createUuidStrategy();
            default:
                throw new IllegalArgumentException("strategy must refer from enum ShuntStrategyFactory.Strategy");
        }
        return null;
    }

    static List<Token> tokens = Lists.newArrayList();
    static {
        for (int i = 1; i <= 100; i++) {
            Token t = new Token(String.valueOf(i));
            tokens.add(t);
        }
    }


    private static ShuntStrategy createUuidStrategy(){
        return new RoundRobinStrategy(tokens);
    }





}
