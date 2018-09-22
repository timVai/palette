package com.mvai.utils;

import com.hankcs.hanlp.model.crf.CRFNERecognizer;
import java.io.*;
import java.util.*;

/**
 * hanlp需要hanlp.properties，文件中配置hanlp官方模型文件路径信息，当然也可以不配置任何内容。
 *
 * hanlp方案， 使用crf++模型，训练时需要添加-t参数，输出model的同时，也输出model.txt，
 *
 * CRFNERecognizer加载model.txt时，会缓存为model.txt.bin文件，*.bin文件可以下次直接加载使用
 */
public class TestCrf {

    public static void main(String[] args) throws Exception {
        System.out.println("-------start-------");

//        test("/Users/fanfengshi/train/crf/model.txt");

        if("test".equals(args[0])){
            test(args[1]);

        }else if("test1".equals(args[0])){
            test1(args[1],args[2],args[3]);

        }
        System.out.println("-------finish-------");
    }

    private static String[] cover(String word){
        String[] wordArray = new String[word.length()];
        for(int index=0;index<word.toCharArray().length;index++){
            wordArray[index] = String.valueOf(word.charAt(index));
        }
        return wordArray;
    }

    private static void test(String modelPath) throws IOException {
        long startTime = Calendar.getInstance().getTimeInMillis();
        String word = "我们今天去钓鱼吧,这商品140元,我操你妈,这商品140元.物美价廉，性价比非常高！";
        String[] wordArray = cover(word);

        /**
         * 27m data,200m model,359 model.txt, 144 model.txt.bin 16.64 min
         * 33m data,228m model,416 model.txt, 166 model.txt.bin 17.33 min
         * 71m data,385m model,726 model.txt, 287 model.txt.bin 33.82 min  load 956ms
         * 28m data,457m model,1.0Gmodel.txt, 356 model.txt.bin 41.64 min
         */
        CRFNERecognizer ner = new CRFNERecognizer(modelPath);
//        CRFNERecognizer ner = new CRFNERecognizer("/Users/fanfengshi/train/crf/model.txt");
        System.out.println(Calendar.getInstance().getTimeInMillis() - startTime);

        for(int n=0;n<100;n++){
            long itTime = Calendar.getInstance().getTimeInMillis();

            String[] result = ner.recognize(wordArray,null);
            for(int i=0;i<result.length;i++){

                System.out.print(word.charAt(i));
                System.out.print("\t");
                System.out.println(result[i]);
            }

            System.out.println(Calendar.getInstance().getTimeInMillis() - itTime);
        }



    }

    private static void test1(String modelPath,String resultFile,String inputFile) throws IOException {
        BufferedWriter bw = null;
        BufferedReader br = null;

        try{
            CRFNERecognizer ner = new CRFNERecognizer(modelPath);
//            CRFNERecognizer ner = new CRFNERecognizer("/Users/fanfengshi/train/crf/model.txt");

            bw = new BufferedWriter(new FileWriter(resultFile));
//            bw = new BufferedWriter(new FileWriter("/Users/fanfengshi/train/crf/test_result_java"));

            br = new BufferedReader(new FileReader(inputFile));
//            br = new BufferedReader(new FileReader("/Users/fanfengshi/train/crf/test_data"));
            String str = null;
            StringBuffer realLine = new StringBuffer();

            int a=0;
            while((str = br.readLine()) != null){
                if(str.length()>2){
                    realLine.append(str.charAt(0));
                    continue;
                }
                String[] result = ner.recognize(cover(realLine.toString()),null);
                realLine.delete(0,realLine.length());
                for(String w : result){
                    bw.write(w);
                    bw.write("\n");
                }
                bw.write("\n");
                if(a++ % 10000 == 0){
                    System.out.println("already process data : "+a);
                }
            }
        }finally {
            if(bw!=null)bw.close();
            if(br!=null)br.close();
        }

    }


}
