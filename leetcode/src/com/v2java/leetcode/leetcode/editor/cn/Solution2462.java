package com.v2java.leetcode.leetcode.editor.cn;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution2462 {

    public static void main(String[] arg) {
        System.out.println(new Solution().totalCost(
                new int[]{31, 25, 72, 79, 74, 65, 84, 91, 18, 59, 27, 9, 81, 33, 17, 58}, 11, 2
                //new int[]{17, 12, 10, 2, 7, 2, 11, 20, 8}, 3, 4
                //new int[]{1,2,4,1},3,3
        ));
    }

    static //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public long totalCost(int[] costs, int k, int candidates) {
            int p = candidates;
            int q = costs.length - candidates - 1;
            PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    if (o2[0] == o1[0]) {
                        return o1[1] - o2[1];
                    } else {
                        return o1[0] - o2[0];
                    }
                }
            });

            for (int i = 0; i < candidates; i++) {
                queue.offer(new int[]{costs[i], i});
            }
            for (int i = 0; i < candidates; i++) {
                int index = costs.length - 1 - i;
                if (index >= p) {
                    queue.offer(new int[]{costs[index], index});
                }
            }
            Object[] arr = queue.toArray();
            String s = "";
            for (Object item : arr) {
                s += (Arrays.toString((int[]) item) + ",");
            }
            System.out.println("初始队列：" + s);
            long ans = 0;
            for (int i = 0; i < k; i++) {
                int[] out = queue.poll();
                System.out.println("第" + i + "轮选择：" + out[0]);
                ans += out[0];
                if (p <= q) {
                    if (out[1] < p) {
                        System.out.println("左进"+costs[p]);
                        queue.offer(new int[]{costs[p], p});
                        p++;
                    }
                    if (out[1] > q) {
                        System.out.println("右进"+costs[q]);
                        queue.offer(new int[]{costs[q], q});
                        q--;
                    }
                }
            }
            return ans;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}