package com.v2java.leetcode.leetcode.editor.cn;

import java.util.Arrays;
import java.util.Comparator;

public class Solution452 {

    public static void main(String[] arg) {
        System.out.println(new Solution().findMinArrowShots(new int[][]{{10,16},{2,8},{1,6},{7,12}}));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int findMinArrowShots(int[][] points) {
            Arrays.sort(points, Comparator.comparingInt(i -> i[0]));

            int right = -1;
            int ans = 0;
            for (int i = 0; i < points.length; i++) {
                if (points[i][0] > right) {
                    right = points[i][1];
                    ans++;
                }else{
                    //ans++;
                    right = Math.min(points[i][1], right);
                }

            }
            return ans;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}