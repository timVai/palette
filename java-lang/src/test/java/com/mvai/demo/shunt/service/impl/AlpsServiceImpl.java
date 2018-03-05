package com.mvai.demo.shunt.service.impl;

import com.mvai.demo.shunt.annotation.Predict;
import com.mvai.demo.shunt.model.Context;
import com.mvai.demo.shunt.service.AlpsService;

public class AlpsServiceImpl  implements AlpsService {



    @Predict("getSomeData")
    @Override
    public void getSomeData(Context context) {
        System.out.println("");
    }

}
