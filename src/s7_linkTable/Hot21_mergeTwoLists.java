package s7_linkTable;

/**
 * 21. 合并两个有序链表
 */
public class Hot21_mergeTwoLists {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        ListNode l1 = list1, l2 = list2;
        while(l1 != null && l2 != null){
            if(l1.val <= l2.val){
                curr.next = l1;
                l1 = l1.next;
            }else{
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        curr.next = l1 == null ? l2 : l1;
        return dummy.next;
    }

    public static void main(String[] args) {
        Hot21_mergeTwoLists hot21 = new Hot21_mergeTwoLists();
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(4);
        head1.next.next.next = new ListNode(5);
        head1.next.next.next.next = new ListNode(6);
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(3);
        head2.next.next = new ListNode(4);

        ListNode head = hot21.mergeTwoLists(head1, head2);

        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }

    }
}
