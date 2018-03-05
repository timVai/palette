package com.mvai.demo.shunt;

import com.mvai.demo.shunt.model.Context;
import com.mvai.demo.shunt.model.Request;
import com.mvai.demo.shunt.model.Response;
import com.mvai.demo.shunt.strategy.ShuntStrategy;
import com.mvai.demo.shunt.strategy.ShuntStrategyFactory;
import com.mvai.demo.shunt.strategy.Token;
import com.mvai.demo.util.ClassUtil;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ShuntController {


    public static Response doService(Request request)   {

        try {
            Response response = new Response();
            Context context = new Context();
            context.request = request;
            context.response = response;


            ShuntStrategy shuntStrategy = ShuntStrategyFactory.getShuntStrategy(ConfigCenter.strategy);

            Token token = shuntStrategy.getToken(request, context);//hash bucket

            String railExper = null;
            for (Map.Entry<String,Integer> experiment : ConfigCenter.experiments.entrySet()) {
                String experimentName = experiment.getKey();
                Integer experimentRang = experiment.getValue();//0-70,71-90,91-100
                if(Integer.valueOf(token.getKey()) < experimentRang){
                    railExper = experimentName;
                    break;
                }
            }

            // find experiment config or not
            if(StringUtils.isEmpty(railExper)){
                //none experiment
                Class c = ClassUtil.getClassByAnnotationVal(request.uri);
                Method m = ClassUtil.getMethodByAnnotationVal(request.uri);
                Object o = c.newInstance();
                m.invoke(o,context);
                return context.response;
            }else{
                //load experiment code
//                Class c = ClassUtil.getClassByAnnotationVal(request.uri);
                Class experimentClz = ClassUtil.getClassByAnnotationVal(railExper);
                Method m = ClassUtil.getMethodByAnnotationVal(request.uri);

                Object o = experimentClz.newInstance();
//                context.currentExperimentParameterMap = ConfigCenter.experiments.get(railExper);
                m.invoke(o,context);
                return context.response;
            }




        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
        }

        return null;
    }


}
