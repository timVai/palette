package com.mvai.algorithm.leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AllSort {


    static int[] getArray(int length){
        int[] array = new int[length];
        Random random = new Random();
        for(int i = 0;i<length;i++){
            array[i] = random.nextInt(100);
        }
        return array;
    }

    static void print(int[] array){
        for(int i : array){
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
//        BubbleSort.sort();
        int[] a = getArray(10);
        print(a);
        InsertionSort.sort(a);
        print(a);

    }


    /**
     * 冒泡排序 O(n^2)
     */
    static class BubbleSort{
        public static int[] sort(int[] array){
            for(int i = 0 ; i < array.length ; i++){
                for(int j = i + 1 ; j < array.length ; j++){
                    if(array[i] > array[j]){
                        int tmp = array[i];
                        array[i] = array[j];
                        array[j] = tmp;
                    }
                }
            }
            return array;
        }
    }

    /**
     * 选择排序 O(n^2)
     */
    static class SelectionSort{
        public static int[] sort(int[] array){
            for(int i = 0 ;i < array.length ; i++){
                int curMinIdx = i;
                for(int j = i + 1 ; j < array.length ; j++){
                    if(array[j] < array[curMinIdx]){
                        curMinIdx = j;
                    }
                }

                if(array[i] > array[curMinIdx]){
                    int tmp = array[i];
                    array[i] = array[curMinIdx];
                    array[curMinIdx] = tmp;
                }
                //else i== curMinIdx
            }
            return array;
        }
    }

    /**
     * 插入排序 O(n^2)
     */
    static class InsertionSort{
        public static int[] sort(int[] array){
            for(int i = 1 ;i < array.length ; i++){
                for(int j = 0 ; j < i ; j++){
                    if(array[i] < array[j]){
                        int tmp = array[i];
                        array[i] = array[j];
                        array[j] = tmp;
                    }
                }

            }
            return array;
        }
    }

    /**
     * 希尔排序
     */
    static class ShellSort{
        public static int[] sort(int[] array){
            for(int i = 1 ;i < array.length ; i++){
                for(int j = 0 ; j < i ; j++){
                }
            }
            return array;
        }
    }









}




