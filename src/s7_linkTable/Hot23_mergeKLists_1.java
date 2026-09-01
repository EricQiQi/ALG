package s7_linkTable;

/**
 * 23.合并K个升序链表
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组中的第一个链表：1->4->5, 第二个链表：1->3->4, 第三个链表：2->6。
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 */
public class Hot23_mergeKLists_1 {

    /**
     * 方法1：顺序合并
     * 时间复杂度：O(kn)
     * 空间复杂度：O(1)
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode ans = null;
        for(int i=0; i<lists.length; i++){
            ans = mergeTwoLists(ans, lists[i]);
        }
        return ans;
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
        Hot23_mergeKLists_1 solution = new Hot23_mergeKLists_1();
    
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
