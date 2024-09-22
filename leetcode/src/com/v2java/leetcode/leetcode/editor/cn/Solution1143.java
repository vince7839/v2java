package com.v2java.leetcode.leetcode.editor.cn;

public class Solution1143 {

    public static void main(String[] arg) {
        System.out.println(new Solution().longestCommonSubsequence("abcde","ace"));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int longestCommonSubsequence(String text1, String text2) {
            int m = text1.length();
            int n = text2.length();
            int[][] dp = new int[m + 1][n + 1];


            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (text1.charAt(i-1) == text2.charAt(j-1)) {
                        dp[i][j] = dp[i-1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                    }
                }

            }
            return dp[m][n];
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}