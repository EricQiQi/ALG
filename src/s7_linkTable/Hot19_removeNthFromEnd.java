package s7_linkTable;

/**
 * 19. 删除链表的倒数第 N 个结点
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 *
 */
public class Hot19_removeNthFromEnd {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode temp = head;
        // 注意len初始是1
        int len = 1;
        while(temp != null && temp.next != null){
            temp = temp.next;
            len++;
        }

        temp = head;
        int index = 0;
        while(index < len-n-1){
            // 如有5个数，删除倒数第2个，即正数第4个元素，此时需要让temp指向正数第3个元素
            // index = 1的时候，temp就指向第三个元素了
            temp = temp.next;
            index++;
        }


        if(len > 1 && len-n > 0){
            temp.next = temp.next.next;
        }else{
            head = head.next != null ? head.next : null;
        }

        return head;
    }

    public static void main(String[] args) {
        Hot19_removeNthFromEnd hot19 = new Hot19_removeNthFromEnd();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        ListNode result = hot19.removeNthFromEnd(head, 2);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
