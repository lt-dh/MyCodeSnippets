package com.lt;

import java.util.ArrayList;

/**
 * 给你一个单链表的头节点 head ，请你判断该链表是否为
 * 回文链表
 * 如果是，返回 true ；否则，返回 false 。
 * <p>
 * 示例 1：
 * 输入：head = [1,2,2,1]
 * 输出：true
 * <p>
 * 示例 2：
 * 输入：head = [1,2]
 * 输出：false
 * <p>
 * 提示：
 * 链表中节点数目在范围[1, 105] 内
 * 0 <= Node.val <= 9
 * <p>
 * 进阶：你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 */
public class Hot100_链表_回文链表 {

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

    public boolean isPalindrome(ListNode head) {
        ArrayList<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int size = list.size();
        for (int i = 0; i < size / 2; i++) {
            if (!list.get(i).equals(list.get(size - 1 - i))) {
                return false;
            }
        }
        return true;
    }
}
