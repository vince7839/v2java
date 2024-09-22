package com.v2java.leetcode.leetcode.editor.cn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution131 {

    public static void main(String[] arg) {
        System.out.println(new Solution().partition("cbbbcc"));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        List<List<String>> ans = new ArrayList<>();

        public List<List<String>> partition(String s) {
            f(s, new ArrayList<>());
            return ans;
        }

        public void f(String s, List<String> list) {
           // System.out.println("s:"+s);
            if (s.length() == 0) {
                if (!list.isEmpty()) {
                    List tmp = new ArrayList();
                    tmp.addAll(list);
                    ans.add(tmp);
                }
                return;
            }

            for (int len = 1; len <= s.length(); len++) {
                String str = s.substring(0, len);
                System.out.println(""+str);
                if (!is(str)) {
                    continue;
                }
                list.add(str);
                String r = s.substring(len);
                f(r, list);
                list.remove(list.size()-1);
            }
        }

        public boolean is(String s) {
            if (s.length() == 1) {
                return true;
            }
            int len = s.length();
            int left = 0;
            int right = len - 1;
            while (left <= right) {
                if (s.charAt(left) != s.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}