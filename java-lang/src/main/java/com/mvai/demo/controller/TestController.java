package com.mvai.demo.controller;

import com.hankcs.hanlp.model.crf.CRFNERecognizer;
import com.mvai.demo.model.Entity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by fanfengshi on 2017/12/29.
 */
@RestController
//@Controller
//@ResponseBody
public class TestController {

    org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private static final String template = "hello,%s";

    @RequestMapping("/entity")
    public Entity tryRequest(@RequestParam(value = "name",defaultValue = "defaultName") String name){
        return new Entity(name);
    }


    @RequestMapping("/entity2")
    public Entity tryRequest(Entity entity){
        return entity;
    }


    CRFNERecognizer ner = null;

    @Value("${modelFile}")
    String modelFile;
    private static String[] cover(String word){
        String[] wordArray = new String[word.length()];
        for(int index=0;index<word.toCharArray().length;index++){
            wordArray[index] = String.valueOf(word.charAt(index));
        }
        return wordArray;
    }
    @RequestMapping("/hanlpClassifier")
    public String hanlpClassifier(@RequestParam("word") String s) throws IOException {
        if(ner == null){
            synchronized (this){
                if(ner == null){
                    ner = new CRFNERecognizer(modelFile);
                }
            }
        }

        StringBuffer buf = new StringBuffer();
        String[] result = ner.recognize(cover(s), null);
        for(String r : result){
            buf.append(r);
        }
        LOGGER.warn("hanlpClassifier result:"+buf.toString());
        return buf.toString();
    }


    class CrfEntity{
        String s;

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }
}
