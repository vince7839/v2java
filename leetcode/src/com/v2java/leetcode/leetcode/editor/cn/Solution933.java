package com.v2java.leetcode.leetcode.editor.cn;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Solution933 {

    public static void main(String[] arg) {
        new ArrayList<Integer>().stream().mapToInt(Integer::intValue).sum();
        RecentCounter counter = new RecentCounter();
        for (int i = 1; i <= 10000; i++) {
            counter.ping(i);
        }
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class RecentCounter {

        Queue<Integer> q = new ArrayDeque<>(3001);
        public RecentCounter() {

        }

        public int ping(int t) {
            q.offer(t);
            while(!q.isEmpty() && q.peek()<t-3000){
                q.poll();
            }
            return q.size();
        }
    }

/**
 * Your RecentCounter object will be instantiated and called as such:
 * RecentCounter obj = new RecentCounter();
 * int param_1 = obj.ping(t);
 */
//leetcode submit region end(Prohibit modification and deletion)

}