package com.v2java.leetcode.leetcode.editor.cn;

public class Solution1071 {

    public static void main(String[] arg) {
        System.out.println(new Solution().gcdOfStrings("ABABAB","ABAB"));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String gcdOfStrings(String str1, String str2) {
            String str = str1.length() < str2.length() ? str1 : str2;
            String ans = "";
            for (int i = 1; i <= str.length(); i++) {
                String s = str.substring(0,i);
                if (isGcd(s,str1) && isGcd(s,str2)){
                    ans = s;
                }
            }
            return ans;
        }

        public boolean isGcd(String str,String target){
            if (!target.startsWith(str)){
                return false;
            }
            if (str.length()==target.length()){
                return true;
            }
            String next = target.substring(str.length());
            return isGcd(str,next);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}