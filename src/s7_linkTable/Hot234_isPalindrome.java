package s7_linkTable;

/**
 * 234. 回文链表
 * 给你一个单链表的头节点 head ，请你判断是否为回文链表。如果是，返回 true ；否则，返回 false 。
 * <p>
 * 1->2->2->1 -> true
 * 1->2->3->2->1 -> true
 * 1->2->3->4->5 -> false
 */
public class Hot234_isPalindrome {

    /**
     * 方法一：数组
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param head
     * @return
     */
    public boolean isPalindrome_1(ListNode head) {
        int len = 0;
        for(ListNode curr = head; curr != null; curr = curr.next){
            len++;
        }

        int[] arr = new int[len];
        int i=0;
        for(ListNode curr = head; curr != null; curr = curr.next){
            arr[i++] = curr.val;
        }

        int left = 0;
        int right = len-1;
        while(left<right){
            if(arr[left] == arr[right]){
                left++;
                right--;
            }else{
                return false;
            }
        }
        return true;
    }

    /**
     * 方法二：
     * 1、找到前半部分链表的尾节点。
     * 2、反转后半部分链表。
     * 3、判断是否回文。
     * 4、恢复链表。
     * 5、返回结果。
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param head
     * @return
     */
    public boolean isPalindrome_2(ListNode head) {
        if(head == null) return true;

        // 1.找到前半部分链表的尾节点。 慢指针1次走1步；快指针1次走2步，当快指针到达链表末尾时，慢指针正好到达链表中间
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2.反转后半部分链表
        ListNode right = reverseList(slow);

        ListNode p1 = head;
        ListNode p2 = right;
        while(p1 != null){
            if(p1.val == p2.val){
                p1 = p1.next;
                p2 = p2.next;
            }else{
                return false;
            }
        }
        return true;
    }

    public ListNode reverseList(ListNode head){
        ListNode prev = null;
        ListNode curr = head;
        while(curr != null){
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

    public static void main(String[] args) {
        Hot234_isPalindrome hot234 = new Hot234_isPalindrome();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(1);
//        System.out.println(hot234.isPalindrome_1(head));
        System.out.println(hot234.isPalindrome_2(head));
    }
}
