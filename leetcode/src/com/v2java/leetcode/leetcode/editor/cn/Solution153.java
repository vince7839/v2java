package com.v2java.leetcode.leetcode.editor.cn;

public class Solution153 {

    public static void main(String[] arg) {
        System.out.println(new Solution().findMin(new int[]{4,5,6,7,0,1,2}));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int findMin(int[] nums) {
            int left = 0;
            int right = nums.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] > nums[right]) {
                    left = mid + 1;
                } else if (nums[mid] < nums[left]) {
                    right = mid;
                }else if(nums[mid] < nums[right]){
                    right = mid;
                } else if (mid == 0 || nums[mid - 1] > nums[mid]) {
                    return nums[mid];
                }
            }
            return nums[left];
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}