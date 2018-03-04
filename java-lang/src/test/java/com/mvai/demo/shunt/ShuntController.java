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


    public static Response doService(Request request)  {

        try {
            Response response = new Response();
            Context context = new Context();
            context.request = request;
            context.response = response;


            ShuntStrategy shuntStrategy = ShuntStrategyFactory.getShuntStrategy(ConfigCenter.strategy);

            Token token = shuntStrategy.getToken(request, context);

            String railExper = null;
            for (Map.Entry<String,Integer> experiment : ConfigCenter.experiments.entrySet()) {
                String experimentName = experiment.getKey();
                Integer experimentRang = experiment.getValue();
                if(Integer.valueOf(token.getKey()) < experimentRang){
                    railExper = experimentName;
                    break;
                }
            }

            // if none experiment config
            if(StringUtils.isEmpty(railExper)){
                Class c = ClassUtil.getClassByAnnotationVal(request.uri);
                Method m = ClassUtil.getMethodByAnnotationVal(request.uri);
                Object o = c.newInstance();
                m.invoke(o,context);
                return context.response;
            }else{

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
