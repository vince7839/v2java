package com.v2java.leetcode.leetcode.editor.cn;

public class Solution41{

    public static void main(String[] arg){
        System.out.println(new Solution().firstMissingPositive(new int[]{1,2,0}));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]<=0){
                nums[i]=nums.length+1;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            int num = Math.abs(nums[i]);
            if (num<=nums.length){
                nums[num] = -nums[num];
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i]>0){
                return nums[i];
            }
        }
        return nums.length+1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}