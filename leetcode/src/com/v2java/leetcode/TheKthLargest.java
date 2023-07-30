package com.v2java.leetcode;

import java.util.PriorityQueue;

public class TheKthLargest {

    public static void main(String[] args) {
        int[] nums = new int[]{5,3,4,7,8,3};
        int r = new TheKthLargest().findKthLargest(nums,2);
        System.out.println(r);
    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(k);

        for(int i=0;i<nums.length;i++){
            if(queue.size() < k){
                queue.add(nums[i]);
            }else if(nums[i] > queue.peek()){
                queue.poll();
                queue.add(nums[i]);
            }
        }
        return queue.peek();
    }
}
