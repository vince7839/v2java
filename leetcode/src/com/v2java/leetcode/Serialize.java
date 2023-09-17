package com.v2java.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serialize {


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public static class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) {
                return "null";
            }
            String s = String.format("%d,%s,%s", root.val
                    , serialize(root.left), serialize(root.right));
            return s;
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {

            List<String> list = new ArrayList<>(Arrays.asList(data.split(",")));
            return f(list);

        }


        public TreeNode f(List<String> list){
            String str = list.remove(0);
            if ("null".equals(str)) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.valueOf(str));
            root.left = f(list);
            root.right = f(list);
            return root;
        }
    }

}
