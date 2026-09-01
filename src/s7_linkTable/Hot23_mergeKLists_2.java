package s7_linkTable;

/**
 * 23.合并K个升序链表
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组中的第一个链表：1->4->5, 第二个链表：1->3->4, 第三个链表：2->6。
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 */
public class Hot23_mergeKLists_2 {

    /**
     * 方法2：分治合并
     * 时间复杂度：O(kn)
     * 空间复杂度：O(1)
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        return merge(lists, 0, lists.length-1);
    }

    /**
     * 分治合并：将 k 个链表二分递归，两两合并
     * 类似归并排序的思路，把大问题拆成小问题
     * 时间复杂度：O(kn log k) 每层合并 O(kn)，共 log k 层
     * 空间复杂度：O(log k) 递归栈
     */
    public ListNode merge(ListNode[] lists, int left, int right) {
        // 递归终止条件：只有一个链表，直接返回
        if (left == right) {
            return lists[left];
        }
    
        // 边界情况：空区间
        if (left > right) {
            return null;
        }
    
        // 二分：将区间分成两半
        int mid = (left + right) / 2;
        // 递归合并左半部分
        ListNode leftList = merge(lists, left, mid);
        // 递归合并右半部分
        ListNode rightList = merge(lists, mid + 1, right);
        // 合并两个有序链表
        return mergeTwoLists(leftList, rightList);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }

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
        curr.next = l1 == null ? l2 : l1;
        return dummy.next;
    }

    public static void main(String[] args) {
        Hot23_mergeKLists_2 solution = new Hot23_mergeKLists_2();
    
        // 构建测试用例：[[1,4,5],[1,3,4],[2,6]]
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(4);
        list1.next.next = new ListNode(5);
    
        ListNode list2 = new ListNode(1);
        list2.next = new ListNode(3);
        list2.next.next = new ListNode(4);
    
        ListNode list3 = new ListNode(2);
        list3.next = new ListNode(6);
    
        ListNode[] lists = {list1, list2, list3};
    
        ListNode result = solution.mergeKLists(lists);
    
        System.out.print("合并结果: ");
        for (ListNode curr = result; curr != null; curr = curr.next) {
            System.out.print(curr.val + " ");
        }
        System.out.println();
    }
}
