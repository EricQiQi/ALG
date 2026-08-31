package s7_linkTable;

/**
 * 2. 两数相加
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 */
public class Hot2_addTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode preHead = new ListNode();
        ListNode head = preHead;

        int pre = 0;
        ListNode temp1 = l1;
        ListNode temp2 = l2;
        while(temp1 != null && temp2 != null){
            int sum = temp1.val + temp2.val + pre;
            pre = sum / 10;
            head.next = new ListNode(sum%10);
            head = head.next;

            temp1 = temp1.next;
            temp2 = temp2.next;
        }

        while(temp1 != null){
            int sum = temp1.val + pre;
            pre = sum / 10;
            head.next = new ListNode(sum%10);
            head = head.next;
            temp1 = temp1.next;
        }
        while(temp2 != null){
            int sum = temp2.val + pre;
            pre = sum / 10;
            head.next = new ListNode(sum%10);
            head = head.next;
            temp2 = temp2.next;
        }

        if(pre > 0){
            head.next = new ListNode(pre % 10);
        }

        return preHead.next;
    }

    public static void main(String[] args) {
        Hot2_addTwoNumbers hot2 = new Hot2_addTwoNumbers();
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        ListNode result = hot2.addTwoNumbers(l1, l2);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }

    }
}
