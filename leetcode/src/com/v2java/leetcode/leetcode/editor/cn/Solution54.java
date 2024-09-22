package com.v2java.leetcode.leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

public class Solution54 {

    public static void main(String[] arg) {
        System.out.println(new Solution());
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        List<Integer> ans = new ArrayList<>();

        public List<Integer> spiralOrder(int[][] matrix) {
            int x = 0;
            int y = -1;
            int dirction = 0;
            int[][] delta = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int total = matrix.length * matrix[0].length;
            while (ans.size() < total) {
                int nextX = x + delta[dirction][0];
                int nextY = y + delta[dirction][1];
                if (!can(matrix, nextX, nextY)) {
                    dirction = (dirction + 1) % 4;
                } else {
                    ans.add(matrix[x][y]);
                    matrix[x][y] = Integer.MAX_VALUE;
                }

            }
            return ans;
        }

        public boolean can(int[][] matrix, int x, int y) {
            if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length || matrix[x][y] == Integer.MAX_VALUE) {
                return false;
            }

            return true;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}