package com.v2java.leetcode.leetcode.editor.cn;

import java.util.Comparator;
import java.util.Stack;

public class Solution394{

    public static void main(String[] arg){
        System.out.println(new Solution().decodeString("3[a]2[bc]"));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String decodeString(String s) {
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == ']'){
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
//leetcode submit region end(Prohibit modification and deletion)

}