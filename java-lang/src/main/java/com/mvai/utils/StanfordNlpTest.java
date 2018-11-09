package com.mvai.utils;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

//https://nlp.stanford.edu/sentiment/trainDevTestTrees_PTB.zip
/**
 * You can run this code with our trained model on text files with the following command:
 *
 * java -cp "*" -mx5g edu.stanford.nlp.sentiment.SentimentPipeline -file foo.txt
 * java -cp "*" -mx5g edu.stanford.nlp.sentiment.SentimentPipeline -stdin
 *
 * An evaluation tool is included with the distribution:
 *
 * java edu.stanford.nlp.sentiment.Evaluate edu/stanford/nlp/models/sentiment/sentiment.ser.gz test.txt
 *
 * Models can be retrained using the following command using the PTB format dataset:
 *
 * java -mx8g edu.stanford.nlp.sentiment.SentimentTraining -numHid 25 -trainPath train.txt -devPath dev.txt -train -model model.ser.gz
 */
public class StanfordNlpTest {


    public static void main(String[] args) {
        String props = "StanfordCoreNLP-chinese.properties";
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation;
        //if  data from file
        //annotation = new Annotation(IOUtils.slurpFileNoExceptions(file));
        annotation = new Annotation("这家酒店很好，我很喜欢。");


        pipeline.annotate(annotation);
        pipeline.prettyPrint(annotation, System.out);

    }

}
