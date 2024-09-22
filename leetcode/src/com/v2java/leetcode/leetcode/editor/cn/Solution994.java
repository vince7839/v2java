package com.v2java.leetcode.leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

public class Solution994 {

    public static void main(String[] arg) {
        //System.out.println(new Solution().orangesRotting(new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}}));
        System.out.println(new Solution().orangesRotting(new int[][]{{2, 2, 2, 1, 1}}));

    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        int[][] m;
        int row;
        int col;
        boolean[][] visited;

        public int orangesRotting(int[][] grid) {
            m = grid;
            row = grid.length;
            col = grid[0].length;
            visited = new boolean[row][col];
            return bfs();
        }

        class Pos {

            int x;
            int y;

            public Pos(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        public int bfs() {
            int orange=0;
            List<Pos> list = new ArrayList<>();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if(m[i][j]==1){
                        orange += 1;
                    }
                    if (m[i][j] == 2) {
                        list.add(new Pos(i, j));
                    }
                }
            }
            if(orange == 0){
                return 0;
            }
            int ans = 0;
            if (list.isEmpty()){
                return -1;
            }
            while (!list.isEmpty()) {
                list.forEach(i -> m[i.x][i.y]=3);
                ans++;
                List<Pos> next = new ArrayList<>();
                for (Pos p : list) {

                    //左
                    if (p.y - 1 >= 0 && m[p.x][p.y - 1] != 0 && m[p.x][p.y - 1] != 3) {
                        next.add(new Pos(p.x, p.y - 1));
                    }

                    //右
                    if (p.y + 1 < col && m[p.x][p.y + 1] != 0 && m[p.x][p.y + 1] != 3) {
                        next.add(new Pos(p.x, p.y + 1));
                    }
                    //上
                    if (p.x - 1 >= 0 && m[p.x - 1][p.y] != 0 && m[p.x - 1][p.y] != 3) {
                        next.add(new Pos(p.x - 1, p.y));
                    }
                    //下
                    if (p.x + 1 < row && m[p.x + 1][p.y] != 0 && m[p.x + 1][p.y] != 3) {
                        next.add(new Pos(p.x + 1, p.y));
                    }
                }
                list = next;
            }
            boolean all = true;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (m[i][j] == 1) {
                        all = false;
                        break;
                    }
                }
            }
            return all ? ans - 1 : -1;
        }

//leetcode submit region end(Prohibit modification and deletion)

    }
}