package com.v2java.leetcode;

import java.util.Arrays;
import java.util.Stack;

public class MoveZeroes {
    static class Solution {
        public void moveZeroes(int[] nums) {
            int right=nums.length-1;
            Stack<Integer> stack = new Stack<>();
            for (int i = nums.length-1; i >=0; i--) {
                if (nums[i] != 0){
                    stack.push(nums[i]);
                }else {
                    nums[right--]=0;
                }
            }
            int pos=0;
            while(!stack.isEmpty()){
                nums[pos++]=stack.pop();
            }
        }

    }

    public static void main(String[] args) {
        int[] m = new int[]{0,1,0,3,12};
        new Solution().moveZeroes(m);
        System.out.println(Arrays.toString(m));
    }
}
