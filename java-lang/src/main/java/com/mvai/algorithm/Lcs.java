package com.mvai.algorithm;

import java.util.Iterator;
import java.util.List;

/**
 * 其实这不是最长公共子序列，而是最长公共子串
 *
 */
public class Lcs {

    public static void main(String[] args) {
        System.out.println(maxSubstring("123456789","1212345456789"));
    }




    private void commSub(List<String> originStrArray, List<String> distStrArray, float threshold){
        int index = 0;
        Iterator<String> iterator = originStrArray.iterator();
        while(iterator.hasNext()){
            String curStr = iterator.next();
            for(String distStr : distStrArray){
                if(maxSubstring(curStr,distStr)/curStr.length() > threshold){
                    iterator.remove();
                }
            }
        }
    }

    // 求解两个字符号的最长公共子串
    public static int maxSubstring(String strOne, String strTwo){
        if(strOne==null || strTwo == null){
            return 0;
        }
        if(strOne.equals("") || strTwo.equals("")){
            return 0;
        }
        int len1 = strOne.length();
        int len2 = strTwo.length();

        int[] topLine = new int[len1];
        int[] currentLine = new int[len1];
        int maxLen = 0;
        int pos = 0;
        char ch = ' ';
        for(int i=0; i<len2; i++){
            ch = strTwo.charAt(i);
            for(int j=0; j<len1; j++){
                if( ch == strOne.charAt(j)){
                    if(j==0){
                        currentLine[j] = 1;
                    } else{
                        currentLine[j] = topLine[j-1] + 1;
                    }
                    if(currentLine[j] > maxLen){
                        maxLen = currentLine[j];
                        pos = j;
                    }
                }
            }
            for(int k=0; k<len1; k++){
                topLine[k] = currentLine[k];
                currentLine[k] = 0;
            }
        }
        return strOne.substring(pos-maxLen+1, pos+1).length();
    }
}
