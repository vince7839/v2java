package com.v2java.leetcode.leetcode.editor.cn;

public class Solution74 {

    public static void main(String[] arg) {
        System.out.println(new Solution());
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            int x = matrix.length;
            int y = matrix[0].length;
            while (x >= 0 && y >= 0) {
                if (target==matrix[x][y]){
                    return true;
                }else if(target < matrix[x][y]){
                    y--;
                }else if(target > matrix[x][y]){
                    x++;
                }
            }
            return false;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}