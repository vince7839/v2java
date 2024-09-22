package com.v2java.leetcode.leetcode.editor.cn;

import java.util.PriorityQueue;

public class Solution2336 {

    public static void main(String[] arg) {
        System.out.println();
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class SmallestInfiniteSet {

        PriorityQueue<Integer> q = new PriorityQueue();

        public SmallestInfiniteSet() {

        }

        public int popSmallest() {
            return q.poll();
        }

        public void addBack(int num) {
            if (!q.contains(num)){
                q.offer(num);
            }
        }
    }

/**
 * Your SmallestInfiniteSet object will be instantiated and called as such:
 * SmallestInfiniteSet obj = new SmallestInfiniteSet();
 * int param_1 = obj.popSmallest();
 * obj.addBack(num);
 */
//leetcode submit region end(Prohibit modification and deletion)

}