package com.v2java.leetcode.leetcode.editor.cn;

public class Solution25 {

    public static void main(String[] arg) {
        System.out.println(new Solution());
    }

    static //leetcode submit region begin(Prohibit modification and deletion)

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    static class Solution {
        public ListNode reverseKGroup(ListNode head, int k) {
            int i = 0;
            ListNode ans = new ListNode(0);
            ListNode prev = ans;
            ListNode cur = head;
            ListNode slow = head;
            ListNode fast = head;
            while (cur != null) {
                cur = cur.next;
                i++;
                if (i == k) {
                    ListNode tmp = cur.next;
                    reverse(slow,k);
                    i=0;
                    prev.next = fast;
                    prev = slow;
                    slow.next = tmp;
                    cur=slow;
                }
            }
            return ans.next;
        }

        public void reverse(ListNode node,int k){
            ListNode prev = null;
            ListNode cur = node;
            int i=0;
            while(i<k){
                System.out.println(""+cur.val);
                ListNode tmp = cur.next;
                cur.next = prev;
                prev = cur;
                cur = tmp;
                i++;
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}