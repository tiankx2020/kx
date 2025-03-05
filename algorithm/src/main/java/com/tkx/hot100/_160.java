package com.tkx.hot100;

import com.tkx.ListNode;

/**
 * @Author tkx
 * @Date 2025 02 24 19 44
 **/
public class _160 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = 0;
        int lenB = 0;
        ListNode tmp = headA;
        // 找长度
        while (tmp != null) {
            lenA++;
            tmp = tmp.next;
        }
        tmp = headB;
        while (tmp != null) {
            lenB++;
            tmp = tmp.next;
        }
        // 提出到前面一截
        ListNode tempA = headA;
        ListNode tempB = headB;
        while (lenA>lenB){
            tempA = tempA.next;
            lenA--;
        }
        while (lenB>lenA){
            tempB = tempB.next;
            lenB--;
        }
        // 逐步便利
        while (headA!=headB){
            headA =  headA.next;
            headB =  headB.next;
        }
        return headA;
    }
}
