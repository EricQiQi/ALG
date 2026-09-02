package test;

import s7_linkTable.Hot146_LRUCache_2;

import java.util.HashMap;

public class LRU {
    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;

        public DLinkedNode() {}
        public DLinkedNode(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer, DLinkedNode> cachedHashMap;
    private int capacity;
    private int size;
    private DLinkedNode head, tail;

    public LRU(int capacity){
        this.capacity = capacity;
        this.size = 0;
        this.cachedHashMap = new HashMap<>();
        this.head = new DLinkedNode();
        this.tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key){
        DLinkedNode node = cachedHashMap.get(key);
        if(node == null){
            return -1;
        }
        moveToTail(node);
        return node.value;
    }

    public void put(int key, int value){
        DLinkedNode node = cachedHashMap.get(key);
        if(node == null){
            DLinkedNode newNode = new DLinkedNode(key, value);
            cachedHashMap.put(key, newNode);
            addToTail(newNode);
            size++;

            if(size > capacity){
                DLinkedNode oldest = removeHead();
                cachedHashMap.remove(oldest.key);
                size--;
            }
        }else{
            node.value = value;
            moveToTail(node);
        }
    }

    public void moveToTail(DLinkedNode node){
        removeNode(node);
        addToTail(node);
    }

    public void removeNode(DLinkedNode node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void addToTail(DLinkedNode node){
        node.prev = tail.prev;
        node.next = tail;

        tail.prev.next = node;
        tail.prev = node;
    }

    public DLinkedNode removeHead(){
        DLinkedNode oldest = head.next;
        removeNode(oldest);
        return oldest;
    }

    public static void main(String[] args) {
        LRU cache = new LRU(2);
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
