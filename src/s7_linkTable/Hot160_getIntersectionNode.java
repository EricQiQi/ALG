package s7_linkTable;

/**
 * 160. 相交链表
 * 编写一个程序，找到两个单链表的交点。
 */
public class Hot160_getIntersectionNode {

    /**
     * 方法1： 长度差对齐法
     * 原理：先统计两条链表长度，长链表指针先走 gap 步对齐尾部，再同步前进，第一个引用相同的节点就是交点
     * 时间复杂度 O(m+n)
     * 空间复杂度 O(1)
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode_1(ListNode headA, ListNode headB) {
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

    /**
     * 方法2： 双指针法
     * 原理：两个指针分别遍历两条链表，当一个指针遍历完一条链表时，切换到另一条链表的头节点继续遍历
     * 时间复杂度 O(m+n)
     * 空间复杂度 O(1)
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode_2(ListNode headA, ListNode headB) {
        ListNode tempA = headA;
        ListNode tempB = headB;

        while (tempA != tempB) {
            // 注意                   是headB不能写成tempB
            tempA = (tempA == null) ? headB : tempA.next;
            tempB = (tempB == null) ? headA : tempB.next;
        }
        return tempA;
    }

    public static void main(String[] args) {
        Hot160_getIntersectionNode hot160 = new Hot160_getIntersectionNode();
    
        // 构造两个相交的链表：
        // listA: 1 -> 2 ->
        //                 3 -> 4 -> 5
        // listB:   9 -> 10 ->
        ListNode common1 = new ListNode(3);
        ListNode common2 = new ListNode(4);
        ListNode common3 = new ListNode(5);
        common1.next = common2;
        common2.next = common3;
    
        ListNode headA = new ListNode(1);
        headA.next = new ListNode(2);
        headA.next.next = common1;
    
        ListNode headB = new ListNode(9);
        headB.next = new ListNode(10);
        headB.next.next = common1;
    
        ListNode result = hot160.getIntersectionNode_1(headA, headB);
        System.out.println(result != null ? "交点值为: " + result.val : "无交点");
    
        // 构造两个不相交的链表：
        // listC: 1 -> 2 -> 3
        // listD: 4 -> 5
        ListNode headC = new ListNode(1);
        headC.next = new ListNode(2);
        headC.next.next = new ListNode(3);
    
        ListNode headD = new ListNode(4);
        headD.next = new ListNode(5);
    
        ListNode result2 = hot160.getIntersectionNode_1(headC, headD);
        System.out.println(result2 != null ? "交点值为: " + result2.val : "无交点");
    }
}
