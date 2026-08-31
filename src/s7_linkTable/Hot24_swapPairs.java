package s7_linkTable;

/**
 * 24. 两两交换链表中的节点
 */
public class Hot24_swapPairs {
    /**
     * 迭代法
     * @param head
     * @return
     */
    public ListNode swapPairs_1(ListNode head) {
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;

        ListNode temp = dummyNode;
        while(temp.next != null && temp.next.next != null){
            ListNode node1 = temp.next;
            ListNode node2 = temp.next.next;
            temp.next = node2;
            node1.next = node2.next;
            node2.next = node1;
            temp = node1;
        }
        return dummyNode.next;
    }

    /**
     * 递归法
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        // ① 递归终止条件
        if (head == null || head.next == null) {
            return head;
        }

        // ② 保存新的头节点（第2个节点）
        ListNode newHead = head.next;  // newHead = 节点2

        // ③ 关键！先处理后面的部分，再连接
        head.next = swapPairs(newHead.next);
        // newHead.next 是节点3，swapPairs(节点3) 返回 4->3（交换后的结果）
        // 所以 head.next = 4（节点1指向节点4）

        // ④ 完成交换
        newHead.next = head;  // 节点2指向节点1

        // ⑤ 返回新头
        return newHead;  // 返回节点2
    }


    public static void main(String[] args) {
        Hot24_swapPairs hot24 = new Hot24_swapPairs();
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        ListNode result = hot24.swapPairs_1(listNode);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }

    }
}
