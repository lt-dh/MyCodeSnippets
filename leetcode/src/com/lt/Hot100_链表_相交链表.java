package com.lt;

import java.util.HashSet;

/**
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
 * 图示两个链表在节点 c1 开始相交：
 * <img src="https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/14/160_statement.png"></img>
 * 题目数据 保证 整个链式结构中不存在环。
 */
public class Hot100_链表_相交链表 {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

//    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
//        HashSet<ListNode> set = new HashSet<>();
//        ListNode res = null;
//        while (headA != null) {
//            set.add(headA);
//            headA = headA.next;
//        }
//        while (headB != null) {
//            if (set.contains(headB)) {
//                res = headB;
//                break;
//            }
//            headB = headB.next;
//        }
//        return res;
//    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode headA1 = headA;
        ListNode headB1 = headB;
        boolean firstA = true;
        boolean firstB = true;
        while (headA != headB) {
            headA = headA.next;
            if (headA == null) {
                if (!firstA) {
                    break;
                }
                firstA = false;
                headA = headB1;
            }
            headB = headB.next;
            if (headB == null) {
                if (!firstB) {
                    break;
                }
                firstB = false;
                headB = headA1;
            }
        }
        return headA == headB ? headA : null;
    }
}
