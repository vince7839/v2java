package com.v2java.leetcode.leetcode.editor.cn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution2215 {

    public static void main(String[] arg) {
        System.out.println(new Solution());
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
            Set<Integer> set1 = new HashSet<>();
            Set<Integer> set2 = new HashSet<>();
            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();
            for (int i:nums1){
                set1.add(i);
            }
            for (int i:nums1){
                set2.add(i);
                if (!set1.contains(i)){
                    list2.add(i);
                }
            }

            for (int i:nums1){
                if (!set2.contains(i)){
                    list1.add(i);
                }
            }
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(list1);
            ans.add(list2);
            return ans;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}