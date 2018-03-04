package com.mvai.demo.shunt;

import com.mvai.demo.shunt.model.Request;
import com.mvai.demo.shunt.model.Response;

import java.util.UUID;

/**
 * @author timvai
 */
public class ShuntDemo {


    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            Runnable run = new Client(UUID.randomUUID().toString());

            new Thread(run).start();
        }
        
    }



}


class Client implements Runnable {
    String uuid;
    Client(String uuid){
        this.uuid = uuid;
    }

    public void run() {
        System.out.println(uuid+"  client start!");
        Request request = new Request.Builder().setUuid(uuid).setParam("").setUri("getCmt").build();

        Response response = ShuntController.doService(request);



    }
}