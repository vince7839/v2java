package com.v2java.leetcode.leetcode.editor.cn;

public class Solution605 {

    public static void main(String[] arg) {
        System.out.println(new Solution().canPlaceFlowers(new int[]{1,0,0,0,1,0,0}, 2));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public boolean canPlaceFlowers(int[] flowerbed, int n) {
            int prev = 0;
            int count = 0;
            for (int i = 0; i < flowerbed.length; i++) {
                if (flowerbed[i] == 1) {
                    prev = 1;
                } else {
                    if (prev == 0) {
                        if (i == flowerbed.length - 1 || flowerbed[i + 1] == 0) {
                            count++;
                            prev = 1;
                        } else {
                            prev = 0;
                        }
                    } else {
                        prev = 0;
                    }
                }
            }
            return count >= n;
        }
    }

//leetcode submit region end(Prohibit modification and deletion)

}