package com.mvai.algorithm.other;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 输入 升序偏移后的数据，找到最小值。
 * ex：找topK的话，可以先找到min，直接向后取K位的元素。
 */
public class FindOne {

    public static Integer[] genArray(int len) {
        Integer[] tmp = new Integer[len];
        int offset = new Random().nextInt(len);
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = i;
        }
        Integer[] array = new Integer[len];
        System.arraycopy(tmp, 0, array, len - offset, offset);
        System.arraycopy(tmp, offset, array, 0, len - offset);
        for (Integer i : array) {
//            System.out.print(i + "\t");
        }
//        System.out.println("start up :");
        return array;
    }


    public static int findMin(List<Integer> list) {
        System.out.println("input : ");
        for (Integer i : list) {
            System.out.print(i + "\t");
        }
        int mid = list.size() / 2;
        System.out.println("mid=" + mid);

        if (list.get(0) < list.get(list.size() - 1)) {
            return list.get(0);
        }

        if (list.size() == 2) {
            if (list.get(0) > list.get(1)) {
                return list.get(1);
            } else {
                return list.get(0);
            }
        }
        if (list.get(0) > list.get(mid)) {
            return findMin(list.subList(0, mid+1));
        } else {
            return findMin(list.subList(mid, list.size()));
        }

    }

    public static void main(String[] args) {

        int i = 0;
        while(i++<20000){
            Integer[] array = genArray(100);
            List<Integer> list = Arrays.asList(array);

            if(findMin(list)!=0){
                System.out.println("error");
                break;
            }
        }
    }
}
