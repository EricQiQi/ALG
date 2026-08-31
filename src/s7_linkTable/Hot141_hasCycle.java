package s7_linkTable;

/**
 * 141. 环形链表
 * 给定 head 节点，返回链表中第一个入环节点。如果链表中没有环，则返回 null。
 *
 */
public class Hot141_hasCycle {

    public boolean hasCycle(ListNode head) {
        if(head == null) return false;

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Hot141_hasCycle hot141 = new Hot141_hasCycle();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = head.next;
        System.out.println(hot141.hasCycle(head));
    }
}
