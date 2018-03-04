package com.mvai.demo.shunt;

import com.google.common.collect.Maps;
import com.mvai.demo.shunt.strategy.ShuntStrategyFactory;

import java.util.Map;
public class ConfigCenter {


    public static ShuntStrategyFactory.Strategy strategy = ShuntStrategyFactory.Strategy.BY_UUID;


    public static Map<String,Integer> experiments = Maps.newLinkedHashMap();

    ConfigCenter(){
        experiments.put("DefaultExp",70);
        experiments.put("Exp2",20);
        experiments.put("Exp3",10);

    }

}
