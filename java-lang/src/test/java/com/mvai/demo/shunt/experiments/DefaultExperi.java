package com.mvai.demo.shunt.experiments;

import com.mvai.demo.shunt.annotation.Experiment;
import com.mvai.demo.shunt.annotation.Predict;
import com.mvai.demo.shunt.model.Context;
import com.mvai.demo.shunt.service.impl.AlpsServiceImpl;

import java.util.Map;

@Experiment(name = "default")
public class DefaultExperi extends AlpsServiceImpl {

    @Predict("getSomeData")
    @Override
    public void getSomeData(Context context) {
        System.out.println("before");

        doExperiment(context.currentExperimentParameterMap);

        this.getSomeData(context);


        System.out.println("after");
    }

    private void doExperiment(Map param){

    }

}
