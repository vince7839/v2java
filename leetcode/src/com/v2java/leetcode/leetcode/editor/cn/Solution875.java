package com.v2java.leetcode.leetcode.editor.cn;

import java.util.Arrays;

public class Solution875 {

    public static void main(String[] arg) {
        System.out.println(new Solution().minEatingSpeed(new int[]{805306368,805306368,805306368},1000000000));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        //其实就是寻找左边界
        public int minEatingSpeed(int[] piles, int h) {
            int left = 1;
            int right = piles[0];
            for (int i = 0; i < piles.length; i++) {
                right = Math.max(right,piles[i]);
            }

            while (left < right) {
                int mid = left + (right - left) / 2;
                int total = getTotal(piles, mid);
                //System.out.println(String.format("mid=%d,left=%d,right=%d,total=%d",mid,left,right,total));
                if (total == h) {
                    right = mid;
                } else if (total < h) {
                    right = mid;
                } else if (total > h) {
                    left = mid + 1;
                }
            }
            return left;
        }

        public int getTotal(int[] piles, int num) {
            int total = 0;
            for (int i = 0; i < piles.length; i++) {
                boolean tmp = piles[i] % num == 0;
                total += (piles[i] / num + (tmp ? 0 : 1));
            }
            return total;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}