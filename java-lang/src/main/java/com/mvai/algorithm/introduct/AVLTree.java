package com.mvai.algorithm.introduct;

import java.util.Stack;

/**
 * 遍历树
 */
public class AVLTree {


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

        System.out.println(recursionCheck(root));
    }


    public static boolean check(TreeNode root){
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack();
        while(cur != null || !stack.empty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();
            System.out.println(cur.v);

            cur = cur.right;
        }
        return true;
    }

    public static boolean recursionCheck(TreeNode root){
        if(root == null){
            return true;
        }
        recursionCheck(root.left);

        Integer cur = root.v;
        System.out.println(cur);

        recursionCheck(root.right);
        return false;
    }

}
