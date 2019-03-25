package com.mvai.algorithm.other;

import java.util.LinkedHashMap;
import java.util.Map;

public class FindChar {


    public static void main(String[] args) {
        findChar("rftgyhrftgy");
    }

    static void findChar(String str){
        char[] cs = str.toCharArray();
        Map<Character,Integer> map = new LinkedHashMap<>();
        for(char c : cs){
            map.put(c,map.getOrDefault(c,0)+1);
        }
        for(Map.Entry<Character,Integer> entry : map.entrySet()){
            if(entry.getValue().equals(new Integer(1))){
                System.out.println(entry.getKey());
                break;
            }
        }
    }
}
