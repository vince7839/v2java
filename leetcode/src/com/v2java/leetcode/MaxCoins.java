package com.v2java.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

class Solution {

    int max = 0;

    public int maxCoins(int[] nums) {
        int[] used = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            dfs(nums, used, i, 0);
        }
        return max;
    }

    public void dfs(int[] nums, int[] used, int index, int current) {
        used[index] = 1;
        int coins = calcTotal(nums, used, index);
        current = current + coins;
        max = Math.max(current, max);
        for (int i = 0; i < nums.length; i++) {
            if (used[i] == 1) {
                continue;
            }
            dfs(nums, used, i, current);
        }
        used[index] = 0;
    }

    public int calcTotal(int[] nums, int[] used, int index) {
        int i = index;
        PriorityQueue<int[]> queue = new PriorityQueue(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1]-o2[1];
            }
        });
        queue.poll();
        int total = nums[index];
        while (--i >= 0) {
            if (used[i] == 0) {
                total *= nums[i];
                break;
            }
        }
        i = index;
        while (++i < nums.length) {
            if (used[i] == 0) {
                total *= nums[i];
                break;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        int s = new Solution().maxCoins(new int[]{7,9,8,0,7,1,3,5,5,2,3});
        System.out.println(s);
    }
}