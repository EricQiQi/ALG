package s7_linkTable;

/**
 * 142. 环形链表 II
 * 给定一个链表，返回链表开始入环的第一个节点。 如果链表中没有环，则返回 null。
 */
public class Hot142_detectCycle {

    public ListNode detectCycle(ListNode head) {
        if(head == null) return null;

        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;

            // 快慢指针相遇的位置
            if(slow == fast){
                // 定义新的指针，指向链表头部
                ListNode curr = head;
                // 当慢指针和curr指针相遇时，即为环形链表的入口节点
                while(slow != curr){
                    slow = slow.next;
                    curr = curr.next;
                }
                return curr;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Hot142_detectCycle hot142 = new Hot142_detectCycle();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = head.next;
        System.out.println(hot142.detectCycle(head));
    }
}
