package com.v2java.leetcode.leetcode.editor.cn;

import java.util.Stack;

public class Solution151 {

    public static void main(String[] arg) {
        System.out.println(new Solution().reverseWords("the sky is blue"));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        String ans = "";

        public String reverseWords(String s) {
            int start = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != ' ' && (i == 0 || s.charAt(i - 1) == ' ')) {
                    start = i;
                }
                if (s.charAt(i) != ' ' &&  (i==s.length()-1||s.charAt(i + 1) == ' ')) {
                    ans = s.substring(start, i+1) + " " + ans;
                }
            }
            return ans;
        }
    }

//leetcode submit region end(Prohibit modification and deletion)

}