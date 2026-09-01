package s7_linkTable;

/**
 * 148. 链表排序
 * 题目：给你链表的头节点 head，将其升序排列并返回排序后的链表。
 * 要求：O(n log n) 时间复杂度，O(1) 空间复杂度（进阶）
 */
public class Hot148_sortList_bottom2top {

    /**
     * 归并排序
     * 自底向上
     * 时间复杂度：O(n log n)
     * 空间复杂度：O(1)
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
    
        // 1. 求链表长度
        int len = 0;
        for (ListNode p = head; p != null; p = p.next) {
            len++;
        }
    
        // 2. 虚拟头节点，简化拼接逻辑
        ListNode dummy = new ListNode(0);
        dummy.next = head;
    
        // 3. 步长 step 从 1 开始，每次翻倍：1 → 2 → 4 → 8 → ...
        for (int step = 1; step < len; step <<= 1) {
            ListNode prev = dummy;
            ListNode curr = dummy.next;
    
            // 按 step 大小切分成若干段，每两段合并
            while (curr != null) {
                // 切出左半段（step 个节点）
                ListNode left = curr;
                ListNode right = split(left, step);
                // 切出右半段（step 个节点），并返回下一轮的起始位置
                curr = split(right, step);
    
                // 合并左右两段，接到 prev 后面
                prev.next = merge(left, right);
    
                // prev 移到合并结果的末尾
                while (prev.next != null) {
                    prev = prev.next;
                }
            }
        }
    
        return dummy.next;
    }
    
    /**
     * 从 head 开始走 step 步，断开，返回下一段的头节点
     */
    private ListNode split(ListNode head, int step) {
        for (int i = 1; head != null && i < step; i++) {
            head = head.next;
        }
        if (head == null) return null;
        ListNode next = head.next;
        head.next = null;  // 断开
        return next;
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
        curr.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }



    public static void main(String[] args) {
        Hot148_sortList_bottom2top solution = new Hot148_sortList_bottom2top();

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
