package com.v2java.leetcode.leetcode.editor.cn;

import java.util.Arrays;
import java.util.Comparator;

public class Solution435{

    public static void main(String[] arg){

        System.out.println(new Solution().eraseOverlapIntervals(new int[][]{{1,2},{1,2},{1,2}}));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(i -> -i[0]));
        int left = Integer.MAX_VALUE;
        int count=0;
        for (int i = 0; i < intervals.length; i++) {
            if (intervals[i][1]<=left){
                left = intervals[i][0];
                count++;
            }
        }
        return intervals.length-count;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}