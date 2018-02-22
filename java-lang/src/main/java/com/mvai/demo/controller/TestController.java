package com.mvai.demo.controller;

import com.mvai.demo.model.Entity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fanfengshi on 2017/12/29.
 */
@RestController
//@Controller
//@ResponseBody
public class TestController {
    private static final String template = "hello,%s";

    @RequestMapping("/entity")
    public Entity tryRequest(@RequestParam(value = "name",defaultValue = "defaultName") String name){
        return new Entity(name);
    }


    @RequestMapping("/entity2")
    public Entity tryRequest(Entity entity){
        return entity;
    }
}
