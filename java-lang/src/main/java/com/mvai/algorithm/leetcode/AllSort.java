package com.mvai.algorithm.leetcode;

import java.util.*;

public class AllSort {


    static int[] getArray(int length) {
        int[] array = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }

    static void print(int[] array) {
        for (int i : array) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println("");
    }

    static void check(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                System.out.println("error sort");
                return;
            }
        }
        System.out.println("great sort");
    }

    public static void main(String[] args) {
//        BubbleSort.sort();
        int[] a = getArray(10);
        print(a);
        QuickSort.sort(a);
        print(a);
        check(a);

    }


    /**
     * 冒泡排序 O(n^2)
     */
    static class BubbleSort {
        public static int[] sort(int[] array) {
            for (int i = 0; i < array.length; i++) {
                for (int j = i + 1; j < array.length; j++) {
                    if (array[i] > array[j]) {
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
    static class SelectionSort {
        public static int[] sort(int[] array) {
            for (int i = 0; i < array.length; i++) {
                int curMinIdx = i;
                for (int j = i + 1; j < array.length; j++) {
                    if (array[j] < array[curMinIdx]) {
                        curMinIdx = j;
                    }
                }

                if (array[i] > array[curMinIdx]) {
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
    static class InsertionSort {
        public static int[] sort(int[] array) {
            for (int i = 1; i < array.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (array[i] < array[j]) {
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
    static class ShellSort {
        public static int[] sort(int[] array) {
            int number = array.length / 2;
            int i;
            int j;
            int temp;
            while (number >= 1) {
                for (i = number; i < array.length; i++) {
                    temp = array[i];
                    j = i - number;
                    while (j >= 0 && array[j] > temp) {
                        array[j + number] = array[j];
                        j = j - number;
                    }
                    array[j + number] = temp;
                }
                number = number / 2;
            }
            return array;
        }
    }

    /**
     * 归并排序
     */
    static class MergeSort {
        public static int[] sort(int[] array) {
            return mergeSort(array, 0, array.length - 1);
        }

        public static int[] mergeSort(int[] array, int begin, int end) {
//            System.out.println("mergeSort func:"+begin+","+end);
            if (end - begin == 0) {
                return array;
            }

            int tmp;
            if (end - begin == 1) {
                if (array[begin] > array[end]) {
                    tmp = array[begin];
                    array[begin] = array[end];
                    array[end] = tmp;
                }
                return array;
            }

            int mid = (end + begin) / 2;
            mergeSort(array, begin, mid);
            mergeSort(array, mid + 1, end);
            merge(array, begin, mid, end);
            return array;
        }

        public static int[] merge(int[] array, int begin, int mid, int end) {
//            System.out.println(Arrays.toString(array));
//            System.out.println("merge func:"+begin+","+mid+","+end);
            int[] copy = Arrays.copyOf(array, array.length);
            int lp = begin, rp = mid + 1;
            int i = begin;
            while (lp <= mid && rp <= end) {
                if (copy[lp] < copy[rp]) {
                    array[i++] = copy[lp++];
                } else {
                    array[i++] = copy[rp++];
                }
            }
            while (lp <= mid) {
                array[i++] = copy[lp++];
            }
            while (rp <= end) {
                array[i++] = copy[rp++];
            }
            System.out.println(Arrays.toString(array));
            return array;
        }
    }

    /**
     * 快排
     */
    static class QuickSort {
        public static int[] sort(int[] array) {
            quickSort(array,0,array.length - 1);
            return array;
        }

        private static int[] quickSort(int[] array,int left,int right){
            int tmp;
            int base = left;
            left += 1;
            while(left<right){
                while(left < right && array[left] < array[base]){
                    left++;
                }
                while(left < right && array[base] < array[right]){
                    right--;
                }

                tmp = array[left];
                array[left] = array[right];
                array[right] = tmp;
            }
            if(array[left] > array[base]){

            }else {

            }
            return array;
        }
    }
}




