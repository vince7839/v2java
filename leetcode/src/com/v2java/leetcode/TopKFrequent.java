package com.v2java.leetcode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequent {
    static class Solution {
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer,Integer> map = new HashMap<>();
            for(int i=0;i<nums.length;i++){
                int count = map.getOrDefault(nums[i],0);
                map.put(nums[i],count+1);
            }
            PriorityQueue<int[]> q = new PriorityQueue(k,new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[1]-o2[1];
                }
            });

            for(Map.Entry<Integer,Integer> entry:map.entrySet()){
                if(q.size()<k||q.peek()[1]<entry.getValue()){
                    if (q.size() == k){
                        q.poll();
                    }
                    int[] arr = new int[]{entry.getKey(),entry.getValue()};
                    q.add(arr);
                }
            }

            int[] result = new int[k];
            for(int i=0;i<k;i++){
                result[i] = q.poll()[0];
            }
            return result;
        }
    }

    public static void main(String[] args) {
        int[] r = new Solution().topKFrequent(new int[]{-1,-1},2);
        System.out.println(r);
    }
}
