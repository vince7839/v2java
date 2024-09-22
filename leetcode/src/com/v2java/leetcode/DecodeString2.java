package com.v2java.leetcode;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecodeString2 {
    static class Solution {
        public String decodeString(String s) {

            Stack<Character> stack = new Stack<>();

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == ']'){
                    boolean used = false;
                    String tmp = "";
                    while(!stack.isEmpty()){
                        char out = stack.pop();
                        if ( out <= 'z' && out >= 'a'){
                            tmp = out + tmp;
                        }else if(out >= '0' && out <= '9'){
                            stack.push(out);
                            int count = readCount(stack);
                            tmp = f(tmp,count);
                            pushStack(stack,tmp);
                            break;

                        }else if(out == '['){
                          if (used){
                              stack.push('[');
                              break;
                          }
                          used = true;
                        }
                    }

                }else{
                    stack.push(c);
                }
            }

            String r = "";
            while(!stack.isEmpty()){
                r = stack.pop() + r;
            }

            return r;
        }

        void pushStack(Stack<Character> stack,String s){
            for (int i = 0; i < s.length(); i++) {
                stack.push(s.charAt(i));
            }
        }

        String f(String s, int k) {
            String r = "";
            for (int i = 0; i < k; i++) {
                r = r + s;
            }
            return r;
        }

        int readCount(Stack<Character> stack){
           String s = "";
           while(!stack.isEmpty() && stack.peek()>='0' && stack.peek()<='9'){
               s = stack.pop()+s;
           }
           return Integer.valueOf(s);
        }
    }

    public static void main(String[] args) {
        //3[a]2[c]
        //3[a2[c]]
        //2[abc]3[cd]ef
        String s = new Solution().decodeString("100[leetcode]");
        System.out.println(s);
    }
}
