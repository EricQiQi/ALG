package s7_linkTable;

/**
 * 148. 链表排序
 * 题目：给你链表的头节点 head，将其升序排列并返回排序后的链表。
 * 要求：O(n log n) 时间复杂度，O(1) 空间复杂度（进阶）
 */
public class Hot148_sortList {

    /**
     * 归并排序
     * 思路：快慢指针找中点 → 递归排序两半 → 合并两个有序链表
     * 时间复杂度：O(n log n)
     * 空间复杂度：O(log n) 递归栈（自底向上可优化到 O(1)）
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        // 1. 快慢指针找中点
        ListNode slow = head;
        ListNode fast = head.next;  // fast 先走一步，偶数长度时 slow 停在前半段
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. 断开链表，分成两半
        ListNode mid = slow.next;
        slow.next = null;

        // 3. 递归排序两半
        ListNode left = sortList(head);
        ListNode right = sortList(mid);

        // 4. 合并两个有序链表
        return merge(left, right);
    }

    /**
     * 合并两个有序链表
     */
    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }

        // 拼接剩余部分
        curr.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    public static void main(String[] args) {
        Hot148_sortList solution = new Hot148_sortList();

        // 测试用例：4 -> 2 -> 1 -> 3
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);

        System.out.print("排序前: ");
        printList(head);

        ListNode sorted = solution.sortList(head);

        System.out.print("排序后: ");
        printList(sorted);
    }

    private static void printList(ListNode head) {
        for (ListNode curr = head; curr != null; curr = curr.next) {
            System.out.print(curr.val + " ");
        }
        System.out.println();
    }
}
