package s7_linkTable;

/**
 * 19. 删除链表的倒数第 N 个结点
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 *
 */
public class Hot19_removeNthFromEnd {

    /**
     * 方法：双指针（快慢指针，一次遍历）
     * 原理：fast 先走 n 步，与 slow 拉开 n 个节点的距离；
     *       然后两个指针同速前进，fast 走到末尾时，slow 正好停在待删节点的前一个
     * 时间复杂度 O(L)
     * 空间复杂度 O(1)
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;
        // 1. 快指针先走 n 步，与慢指针拉开 n 个节点的距离
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        // 2. fast 已经走出链表（走了 n 步就到 null），说明要删的就是头节点，直接返回下一个
        if (fast == null) return head.next;
        // 3. 两指针同速前进，fast 走到最后一个节点时（fast.next == null），
        //    slow 正好在倒数第 n+1 个节点（待删节点的前一个）
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        // 4. 跨过待删节点
        slow.next = slow.next.next;
        return head;
    }

    public static void main(String[] args) {
        Hot19_removeNthFromEnd hot19 = new Hot19_removeNthFromEnd();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        ListNode result = hot19.removeNthFromEnd(head, 2);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
