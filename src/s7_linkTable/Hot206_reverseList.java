package s7_linkTable;

/**
 * 206. 反转链表
 *
 */
public class Hot206_reverseList {

    /**
     * 迭代法
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param head
     * @return
     */
    public ListNode reverseList_1(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    /**
     * 递归法
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param head
     * @return
     */
    public ListNode reverseList_2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList_2(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * 迭代法
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param head
     * @return
     */
    public ListNode reverseList_3(ListNode head) {
        ListNode prev = null;
        for(ListNode curr = head; curr != null; curr = curr.next){
            prev = new ListNode(curr.val, prev);
        }
        return prev;
    }


    public static void main(String[] args) {
        Hot206_reverseList hot206 = new Hot206_reverseList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        ListNode result = hot206.reverseList_2(head);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}