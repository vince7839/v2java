package com.v2java.leetcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecodeString {
    static class Solution {

        private static Pattern pattern = Pattern.compile("([1-9]+)\\[(.+?)\\]");

        public String decodeString(String s) {

            Matcher matcher = pattern.matcher(s);
            int prev = 0;
            String r = "";
            while (matcher.find()) {
                int index = matcher.start();
                int count = Integer.parseInt(matcher.group(1));
                String sub = matcher.group(2);
                String tmp = decodeString(sub);
                r = r + s.substring(prev, index) + f(tmp, count);
                prev = matcher.end();
            }
            //拼接剩余的
            r = r + s.substring(prev);
            return r;

        }

        String f(String s, int k) {
            String r = "";
            for (int i = 0; i < k; i++) {
                r = r + s;
            }
            return r;
        }
    }

    public static void main(String[] args) {
        String s = new Solution().decodeString("3[a2[c]]");
        System.out.println(s);
    }
}
