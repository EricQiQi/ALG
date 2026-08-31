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
     * 方法：递归反转链表
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // ① 先检查剩余节点是否够 k 个，不够直接返回（不反转）
        ListNode check = head;
        int count = 0;
        while (check != null && count < k) {
            check = check.next;
            count++;
        }
        if (count < k) {
            return head;  // 不足 k 个，保持原顺序
        }

        // ② 够 k 个，执行反转
        ListNode[] result = reverseList(head, k);
        // result[0] = 反转后的新头，result[1] = 下一组的头
        head.next = reverseKGroup(result[1], k);
        return result[0];
    }

    public ListNode[] reverseList(ListNode head, int k) {
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
        ListNode[] result = {prev, curr};
        return result;
    }




    public static void main(String[] args) {
        Hot25_reverseKGroup hot25 = new Hot25_reverseKGroup();
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        ListNode result = hot25.reverseKGroup(listNode, 2);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
