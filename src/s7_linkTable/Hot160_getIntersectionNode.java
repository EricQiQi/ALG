package s7_linkTable;

/**
 * 160. 相交链表
 * 编写一个程序，找到两个单链表的交点。
 */
public class Hot160_getIntersectionNode {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode tempA= headA;
        ListNode tempB = headB;

        int lenA = 0;
        while(tempA != null){
            lenA++;
            tempA = tempA.next;
        }

        int lenB = 0;
        while(tempB != null){
            lenB++;
            tempB = tempB.next;
        }

        // 重置tempA和tempB
        tempA = headA;
        tempB = headB;

        int gap = Math.abs(lenA-lenB);
        if(lenA > lenB){
            for(int i=0; i<gap; i++){
                tempA = tempA.next;
            }
        }else if(lenA < lenB){
            for(int i=0; i<gap; i++){
                tempB = tempB.next;
            }
        }

        while(tempA != null){
            // 直接比较引用
            if(tempA == tempB){
                return tempA;
            }
            tempA = tempA.next;
            tempB = tempB.next;
        }

        return null;
    }

    public static void main(String[] args) {
        Hot160_getIntersectionNode hot160 = new Hot160_getIntersectionNode();
    
        // 构造两个相交的链表：
        // listA: 1 -> 2 ->
        //                 3 -> 4 -> 5
        // listB:   9 -> 10 ->
        ListNode common1 = hot160.new ListNode(3);
        ListNode common2 = hot160.new ListNode(4);
        ListNode common3 = hot160.new ListNode(5);
        common1.next = common2;
        common2.next = common3;
    
        ListNode headA = hot160.new ListNode(1);
        headA.next = hot160.new ListNode(2);
        headA.next.next = common1;
    
        ListNode headB = hot160.new ListNode(9);
        headB.next = hot160.new ListNode(10);
        headB.next.next = common1;
    
        ListNode result = hot160.getIntersectionNode(headA, headB);
        System.out.println(result != null ? "交点值为: " + result.val : "无交点");
    
        // 构造两个不相交的链表：
        // listC: 1 -> 2 -> 3
        // listD: 4 -> 5
        ListNode headC = hot160.new ListNode(1);
        headC.next = hot160.new ListNode(2);
        headC.next.next = hot160.new ListNode(3);
    
        ListNode headD = hot160.new ListNode(4);
        headD.next = hot160.new ListNode(5);
    
        ListNode result2 = hot160.getIntersectionNode(headC, headD);
        System.out.println(result2 != null ? "交点值为: " + result2.val : "无交点");
    }
}
