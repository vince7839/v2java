package com.v2java.leetcode.leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

public class Solution1431{

    public static void main(String[] arg){
        System.out.println(new Solution());
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int max = 0;
        List<Boolean> ans = new ArrayList<>();
        for (int i = 0; i < candies.length; i++) {
            max = Math.max(max,candies[i]);
        }
        for (int i = 0; i < candies.length; i++) {
            boolean tmp = candies[i]+extraCandies>=max;
            ans.add(tmp);
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}