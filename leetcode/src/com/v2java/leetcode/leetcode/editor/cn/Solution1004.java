package com.v2java.leetcode.leetcode.editor.cn;

public class Solution1004 {

    public static void main(String[] arg) {
        System.out.println(new Solution());
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int longestOnes(int[] nums, int k) {
            int lsum = 0, rsum = 0;
            int left = 0;
            int ans=0;
            for (int right = 0; right < nums.length; right++) {
                rsum += 1 - nums[right];
                while (rsum - lsum > k) {
                    lsum += 1-nums[left];
                    left++;
                }
                ans = Math.max(ans,right-left+1);
            }
            return ans;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}