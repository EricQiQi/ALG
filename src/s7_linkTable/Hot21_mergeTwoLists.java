package s7_linkTable;

/**
 * 21. 合并两个有序链表
 */
public class Hot21_mergeTwoLists {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode preHead = new ListNode();
        ListNode head = preHead;

        while(list1 != null && list2 != null){
            if(list1.val < list2.val){
                head.next = list1;
                list1 = list1.next;
            }else{
                head.next = list2;
                list2 = list2.next;
            }
            head = head.next;
        }
        head.next = list1 == null ? list2 : list1;
        return head.next;
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
