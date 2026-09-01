package s7_linkTable;

/**
 * 147. 对链表进行插入排序
 * 题目：使用插入排序对链表进行排序，并返回排序后的链表头节点。
 */
public class LeetCode147_sortList {


    /**
     * 插入排序（优化版）
     * 思路：维护已排序部分，curr 逐个处理
     *   - 若 curr >= lastSorted，说明已在正确位置，直接后移
     *   - 若 curr < lastSorted，从 dummyHead 找插入位置
     * 时间复杂度：O(n²) 最坏，O(n) 最好（已排序时）
     * 空间复杂度：O(1)
     */
    public static ListNode insertionSortList(ListNode head)  {
        if (head == null) return head;

        // dummyHead 是虚拟头，简化插入到首位的逻辑
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        
        // lastSorted: 已排序部分的最后一个节点
        // curr: 当前待处理的节点
        ListNode lastSorted = head, curr = head.next;
        
        while (curr != null) {
            if (lastSorted.val <= curr.val) {
                // curr 已经在正确位置（>= 已排序末尾），不用移动
                lastSorted = lastSorted.next;
            } else {
                // curr 比已排序末尾小，需要找插入位置
                ListNode prev = dummyHead;
                // 找到第一个 > curr.val 的节点前面
                while (prev.next.val <= curr.val) {
                    prev = prev.next;
                }
                // 将 curr 从原位置摘除，插入到 prev 后面
                lastSorted.next = curr.next;  // 摘除 curr
                curr.next = prev.next;        // curr 指向 prev 的下一个
                prev.next = curr;             // prev 指向 curr
            }
            // 处理下一个节点（lastSorted 的下一个就是未处理的第一个）
            curr = lastSorted.next;
        }
        return dummyHead.next;
    }


    public static void main(String[] args) {
        LeetCode147_sortList solution = new LeetCode147_sortList();

        // 测试用例：4 -> 2 -> 1 -> 3
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);

        System.out.print("排序前: ");
        printList(head);

        ListNode sorted = solution.insertionSortList(head);

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
