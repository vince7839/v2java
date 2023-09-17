package com.v2java.leetcode;

public class FindDuplicate {

    static class Solution {
        public int findDuplicate(int[] nums) {
            int slow = 0;
            int fast = 0;
            do {
                slow = nums[slow];
                fast = nums[nums[fast]];
            }while(slow != fast);
            System.out.println(slow);
            fast = 0;
            while(slow != fast){
                slow = nums[slow];
                fast = nums[fast];
            }
            return slow;
        }
    }

    public static void main(String[] args) {
        //    |-|
        //1 3 2-4
        //      |-|
        //0-1-3-2-4
        int r = new Solution().findDuplicate(new int[]{1,3,4,2,2});
        System.out.println(r);
    }
}
