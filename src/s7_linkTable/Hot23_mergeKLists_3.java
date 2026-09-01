package s7_linkTable;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 23.合并K个升序链表
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组中的第一个链表：1->4->5, 第二个链表：1->3->4, 第三个链表：2->6。
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 */
public class Hot23_mergeKLists_3 {

    /**
     * 方法3：优先队列（最小堆）
     * 时间复杂度：O(kn log k)
     * 空间复杂度：O(k)
     *
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // dummy 虚拟头，简化结果链表的拼接
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        // 最小堆：按节点值排序，堆顶始终是最小值节点
        PriorityQueue<ListNode> pq = new PriorityQueue<>(
                (a, b) -> (a.val - b.val));

        // 初始化：将 k 个链表的头节点（非 null）加入堆中
        for (ListNode list : lists) {
            if (list != null) {
                pq.add(list);
            }
        }

        // 每次取出最小值节点，接到结果链表末尾
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();      // 取出当前最小
            curr.next = node;               // 接到结果链表
            if (node.next != null) {
                pq.add(node.next);          // 该节点的下一个入堆
            }
            curr = curr.next;               // 结果指针后移
        }
        return dummy.next;
    }


    public static void main(String[] args) {
        Hot23_mergeKLists_3 solution = new Hot23_mergeKLists_3();

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
