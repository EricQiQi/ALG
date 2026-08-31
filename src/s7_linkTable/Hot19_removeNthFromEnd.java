package s7_linkTable;

/**
 * 19. 删除链表的倒数第 N 个结点
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 *
 */
public class Hot19_removeNthFromEnd {

    /**
     * 方法1：先计算链表长度，再删除倒数第n个结点
     * 时间复杂度 O(L)
     * 空间复杂度 O(1)
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd_1(ListNode head, int n) {
        ListNode temp = head;
        int len = 0;
        while(temp != null){
            len++;
            temp = temp.next;
        }

        // 要删的就是头节点，直接返回下一个
        if (len == n) return head.next;

        temp = head;
        int index = 0;
        while(index < len-n-1){
            // 如有5个数，删除倒数第2个，即正数第4个元素，此时需要让temp指向正数第3个元素
            // index = 1的时候，temp就指向第三个元素了
            temp = temp.next;
            index++;
        }

        temp.next = temp.next.next;

        return head;
    }

    /**
     * 方法2：双指针（快慢指针，一次遍历）
     * 原理：fast 先走 n 步，与 slow 拉开 n 个节点的距离；
     *       然后两个指针同速前进，fast 走到末尾时，slow 正好停在待删节点的前一个
     * 时间复杂度 O(L)
     * 空间复杂度 O(1)
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd_2(ListNode head, int n) {
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
        ListNode result = hot19.removeNthFromEnd_2(head, 2);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
