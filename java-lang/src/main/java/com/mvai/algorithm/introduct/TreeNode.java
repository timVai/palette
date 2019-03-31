package com.mvai.algorithm.introduct;

public class TreeNode {

    public TreeNode(Integer value) {
        v = value;
    }
    public TreeNode(Integer value,TreeNode left,TreeNode right){
        this.v = value;
        this.left = left;
        this.right = right;
    }
    public Integer v;
    public TreeNode left;
    public TreeNode right;
}
