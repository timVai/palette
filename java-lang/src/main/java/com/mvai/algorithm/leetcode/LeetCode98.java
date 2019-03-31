package com.mvai.algorithm.leetcode;

import com.mvai.algorithm.introduct.TreeNode;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * 98. Validate Binary Search Tree
 */
public class LeetCode98 {

    public static void main(String[] args) {

//        5
//       /\
//      1 4
//        /\
//       3  6


        TreeNode rl1 = new TreeNode(3);
        TreeNode rr1 = new TreeNode(6);
        TreeNode left1 = new TreeNode(1);
        TreeNode right1 = new TreeNode(4,rl1,rr1);
        TreeNode root = new TreeNode(5,left1,right1);

        System.out.println(check(root));
    }

    public static boolean check(TreeNode root){
        Integer lastValue = Integer.MIN_VALUE;
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack();
        while(cur != null || !stack.empty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();
            if(cur.v < lastValue){
                return false;
            }
            lastValue = cur.v;
            cur = cur.right;
        }
        return true;
    }


}

