package s7_linkTable;

/**
 * 25. K 个一组翻转链表
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * 进阶：
 *  请尝试使用 O(1) 额外空间解决此问题。
 */
public class Hot25_reverseKGroup {

    /**
     * 方法1：递归反转链表
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public ListNode reverseKGroup_1(ListNode head, int k) {
        // ① 先检查剩余节点是否够 k 个，不够直接返回（不反转）
        ListNode temp = head;
        int count = 0;
        while (temp != null && count < k) {
            temp = temp.next;
            count++;
        }
        if (count < k) {
            return head;  // 不足 k 个，保持原顺序
        }

        // ② 够 k 个，执行反转
        ListNode newHead = reverseList(head, k);
        // temp 下一个要反转的开始节点
        head.next = reverseKGroup_1(temp, k);
        return newHead;
    }

    public ListNode reverseList(ListNode head, int k) {
        ListNode prev = null;
        ListNode curr = head;
        int count = 0;
        while(curr != null && count<k){
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
            count++;
        }
        // 连接反转后的链表和剩余的链表
        head.next = curr;
        return prev;
    }


    /**
     * 方法2：迭代反转链表
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public ListNode reverseKGroup_2(ListNode head, int k) {
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        ListNode pre = dummyNode;

        while(head != null){
            ListNode tail = pre;
            // 查看剩余部分长度是否大于等于k
            for(int i = 0; i < k; i++){
                tail = tail.next;
                if(tail == null){
                    return dummyNode.next;
                }
            }

            ListNode nex = tail.next;
            ListNode[] reversed = myReverse(head, tail);
            head = reversed[0];
            tail = reversed[1];

            pre.next = head;
            tail.next = nex;
            pre = tail;
            head = tail.next;
        }
        return dummyNode.next;
    }

    public ListNode[] myReverse(ListNode head, ListNode tail) {
        ListNode prev = tail.next;
        ListNode p = head;
        while(prev != tail){
            ListNode nex = p.next;
            p.next = prev;
            prev = p;
            p = nex;
        }
        return new ListNode[]{tail, head};
    }

    public static void main(String[] args) {
        Hot25_reverseKGroup hot25 = new Hot25_reverseKGroup();
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);

        // 调用方法1：递归反转链表
        ListNode result = hot25.reverseKGroup_1(listNode, 2);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }

        // 调用方法2：迭代反转链表
//        ListNode result2 = hot25.reverseKGroup_2(listNode, 2);
//        while (result2 != null) {
//            System.out.print(result2.val + " ");
//            result2 = result2.next;
//        }
    }


}
