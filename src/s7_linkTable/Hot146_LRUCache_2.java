package s7_linkTable;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 146. LRU 缓存
 * 请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构。
 * 实现 LRUCache 类：
 * LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 */
public class Hot146_LRUCache_2{
    // 1. 定义双向链表节点类
    class DLinkedNode{
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
        public DLinkedNode(){}
        public DLinkedNode(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    // 2.核心数据结构
    private HashMap<Integer, DLinkedNode> cachedHashMap; // hash表，快速查找
    private DLinkedNode head, tail;                      // 双向链表，头尾指针
    private int capacity;                                // 缓存容量
    private int size;                                    // 缓存当前大小

    // 3.初始化
    public Hot146_LRUCache_2(int capacity){
        this.capacity = capacity;
        this.size = 0;
        cachedHashMap = new HashMap<>();

        // 创建哑节点（哨兵），简化边界操作
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    // 4.获取缓存
    public int get(int key) {
        DLinkedNode node = cachedHashMap.get(key);
        if(node == null){
            return -1;  // 未命中缓存
        }
        // 关键点：只要节点被访问，就移动到链表尾部（代表最近使用）
        moveToTail(node);
        return node.value;
    }

    // 5.更新缓存
    public void put(int key, int value) {
        DLinkedNode node = cachedHashMap.get(key);
        if(node == null){
            // 情况1：key不存在
            DLinkedNode newNode = new DLinkedNode(key, value);
            cachedHashMap.put(key, newNode);
            addToTail(newNode); // 新节点添加到链表尾部
            size++;

            // 如果超出容量，移除链表头部（最久未使用）
            if(size > capacity){
                DLinkedNode oldest = removeHead();
                // 注意：remove的使用方法。这里需要移除的是最久未使用的节点，即链表头部
                cachedHashMap.remove(oldest.key);
                size--;
            }
        }else{
            // 情况2：key存在，更新value，并移动到尾部
            node.value = value;
            moveToTail(node);
        }
    }

    // **************链表底层操作方法**************
    // 6.将节点添加到尾部（紧挨着tail哑节点之前）
    public void addToTail(DLinkedNode node){
        // 新节点的prev和next
        node.prev = tail.prev;
        node.next = tail;
        // tail的前面节点
        tail.prev.next = node;
        // tail
        tail.prev = node;
    }

    // 7.移除某个节点（断开前后连接）
    public void removeNode(DLinkedNode node){
        // node的前置节点
        node.prev.next = node.next;
        // node的后置节点
        node.next.prev = node.prev;
    }

    // 8.将某个已存在的节点移动到尾部（代表刚被使用过）
    public void moveToTail(DLinkedNode node){
        // 将该节点先从链表中移除
        removeNode(node);
        // 将节点添加到尾部
        addToTail(node);
    }

    // 9.移除头部节点（即最久未使用的那个，紧挨着head哑节点之后）
    public DLinkedNode removeHead(){
        DLinkedNode oldest = head.next;
        removeNode(oldest);
        return oldest;
    }


    public static void main(String[] args) {
        Hot146_LRUCache_2 cache = new Hot146_LRUCache_2(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));    // 1
        cache.put(3, 3);                     // 逐出 key=2
        System.out.println(cache.get(2));    // -1
        cache.put(4, 4);                     // 逐出 key=1
        System.out.println(cache.get(1));    // -1
        System.out.println(cache.get(3));    // 3
        System.out.println(cache.get(4));    // 4
    }
}
