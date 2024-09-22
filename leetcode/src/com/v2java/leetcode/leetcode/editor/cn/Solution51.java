package com.v2java.leetcode.leetcode.editor.cn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution51 {

    public static void main(String[] arg) {
        System.out.println(new Solution().solveNQueens(4));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        char[][] visited;
        List<List<String>> ans = new ArrayList<>();

        public List<List<String>> solveNQueens(int n) {
            visited = new char[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(visited[i],'.');
            }
            f(n,0);
            return ans;
        }

        public void f(int n,int row) {
            if (n == 0) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < visited.length; i++) {
                    list.add(new String(visited[i]));
                }
                ans.add(list);
                return;
            }
            for (int i = row; i < visited.length; i++) {
                for (int j = 0; j < visited.length; j++) {
                    if (!can(i, j)) {
                        continue;
                    }
                    visited[i][j] = 'Q';
                    f(n - 1,i);
                    visited[i][j] = '.';
                }
            }
        }

        public boolean can(int x, int y) {
            for (int i = 0; i < visited.length; i++) {
                for (int j = 0; j < visited.length; j++) {
                    if (visited[i][j] == '.') {
                        continue;
                    }
                    if (i == x || j == y) {
                        return false;
                    }
                    if (Math.abs(x - i) ==  Math.abs(y - j)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}