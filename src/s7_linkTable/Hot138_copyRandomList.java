package s7_linkTable;

import java.util.HashMap;
import java.util.Map;

/**
 * 138. 复制带随机指针的链表
 * 题目：请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
 */
public class Hot138_copyRandomList {

    // 方法2：回溯法使用缓存，缓存已经访问过的节点
    private static Map<Node, Node> cachedMap = new HashMap<>();

    /**
     * 方法1：哈希表
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     * @param head
     * @return
     */
    public static Node copyRandomList_1(Node head) {
        if (head == null) return null;

        Map<Node, Node> map = new HashMap<>();
        for(Node curr = head; curr != null; curr = curr.next){
            map.put(curr, new Node(curr.val));
        }
        for(Node curr = head; curr != null; curr = curr.next){
            // map.get(curr) 获取的是新节点，给新结点设置next和random
            map.get(curr).next = map.get(curr.next);
            map.get(curr).random = map.get(curr.random);
        }
        return map.get(head);
    }

    /**
     * 方法2：回溯法
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     * @param head
     * @return
     */
    public static Node copyRandomList_2(Node head) {
        if (head == null) return null;
        if(!cachedMap.containsKey(head)){
            Node newNode = new Node(head.val);
            cachedMap.put(head, newNode);
            newNode.next = copyRandomList_2(head.next);
            newNode.random = copyRandomList_2(head.random);
        }
        return cachedMap.get(head);
    }

    /**
     * 方法3：迭代法
     * 步骤：
     * 1. 复制每个节点并插入到原节点之后
     * 2. 设置复制节点的随机指针
     * 3. 分离原节点和复制节点
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     * @return
     */
    public static Node copyRandomList_3(Node head) {
        if (head == null) return null;

        // 1. 复制每个节点并插入到原节点之后
        Node p = head;
        while(p != null){
            Node copy = new Node(p.val);
            copy.next = p.next;
            p.next = copy;
            p = copy.next; // p 指向下一个原节点
        }
        // 2. 设置复制节点的随机指针
        p = head;
        while(p != null){
            if(p.random != null){
                // 设置复制节点的随机指针
                p.next.random = p.random.next;
            }
            p = p.next.next; // p 指向下一个原节点
        }
        // 3. 分离原节点和复制节点
        Node newHead = head.next;
        Node p1 = head, p2 = newHead;
        while(p2.next != null){
            // 恢复原链表
            p1.next = p2.next;
            // 连接新链表
            p2.next = p2.next.next;
            // 移动指针
            p1 = p1.next;
            p2 = p2.next;
        }
        // 最后一个原节点的next要指向null
        p1.next = null;
        return newHead;
    }

    public static void main(String[] args) {
        // 构建测试链表：7 -> 13 -> 11 -> 10 -> 1
        Node node1 = new Node(7);
        Node node2 = new Node(13);
        Node node3 = new Node(11);
        Node node4 = new Node(10);
        Node node5 = new Node(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node2.random = node1;
        node3.random = node5;
        node4.random = node3;
        node5.random = node1;

        // 方法1：哈希表
        Node copy1 = copyRandomList_1(node1);
        printList(copy1);

        // 方法2：回溯法（先清空静态缓存）
        cachedMap.clear();
        Node copy2 = copyRandomList_2(node1);
        printList(copy2);
    }

    private static void printList(Node head) {
        for (Node curr = head; curr != null; curr = curr.next) {
            System.out.print(curr.val + "(" + (curr.random == null ? "null" : curr.random.val) + ") ");
        }
        System.out.println();
    }


}
