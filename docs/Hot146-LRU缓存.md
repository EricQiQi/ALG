# LRU 缓存（LRU Cache）

## 题目描述

设计一个 LRU（最近最少使用）缓存，实现 `get` 和 `put` 操作，均为 **O(1)** 时间复杂度。

- `get(key)`：存在返回值并标记为"刚使用"，不存在返回 -1
- `put(key, value)`：已存在则更新，不存在则插入。超容量时逐出**最久未使用**的

```
LRUCache cache = new LRUCache(2);
cache.put(1, 1);    // 缓存：{1=1}
cache.put(2, 2);    // 缓存：{1=1, 2=2}
cache.get(1);       // 返回 1，1 变为最近使用
cache.put(3, 3);    // 容量满，逐出最久未用的 2 → {1=1, 3=3}
cache.get(2);       // 返回 -1（已被逐出）
cache.put(4, 4);    // 容量满，逐出 1 → {3=3, 4=4}
```

---

## 核心思路

**要同时满足 O(1) 查找 + O(1) 删除/插入，需要 HashMap + 双向链表。**

```
HashMap：key → 节点      → O(1) 定位节点
双向链表：维护使用顺序     → O(1) 删除/移动/插入

        HashMap
        ┌──────────┐
   key1 │→ Node1    │
   key2 │→ Node2    │
   key3 │→ Node3    │
        └──────────┘

双向链表（带哨兵）：
head ↔ Node1 ↔ Node2 ↔ Node3 ↔ tail
 ↑ 最久未用                ↑ 最近使用
```

**规则：**
- `get` 命中 → 把节点移到链表尾部（标记为最近使用）
- `put` 新节点 → 插入链表尾部 + 放入 HashMap
- 超容量 → 删除链表头部（最久未用）+ 从 HashMap 移除

---

## 方法1：HashMap + 双向链表（手写）

### 代码

```java
class LRUCache {
    class DLinkedNode {
        int key, value;
        DLinkedNode prev, next;
        DLinkedNode(int key, int value) { this.key = key; this.value = value; }
    }

    private HashMap<Integer, DLinkedNode> map;
    private DLinkedNode head, tail;
    private int capacity, size;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        map = new HashMap<>();
        head = new DLinkedNode(0, 0);  // 哨兵
        tail = new DLinkedNode(0, 0);  // 哨兵
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = map.get(key);
        if (node == null) return -1;
        moveToTail(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = map.get(key);
        if (node == null) {
            DLinkedNode newNode = new DLinkedNode(key, value);
            map.put(key, newNode);
            addToTail(newNode);
            size++;
            if (size > capacity) {
                DLinkedNode oldest = removeHead();
                map.remove(oldest.key);  // 用 node.key 从 map 中移除
                size--;
            }
        } else {
            node.value = value;
            moveToTail(node);
        }
    }

    // ---- 链表操作 ----
    void addToTail(DLinkedNode node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }

    void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    void moveToTail(DLinkedNode node) {
        removeNode(node);
        addToTail(node);
    }

    DLinkedNode removeHead() {
        DLinkedNode oldest = head.next;
        removeNode(oldest);
        return oldest;
    }
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(1) | get/put 均为 HashMap 查找 + 链表操作 |
| 空间 | O(capacity) | HashMap + 链表各存 capacity 个节点 |

---

## 方法2：LinkedHashMap（一行搞定）

```java
class LRUCache extends LinkedHashMap<Integer, Integer> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);  // true = 按访问顺序排序
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
```

> LinkedHashMap 内部就是 HashMap + 双向链表，和手写方法1 原理完全一样，只是封装好了。

---

## 关键设计点

### 为什么用双向链表而不是单向？

`moveToTail` 需要先删除节点再插入尾部。删除节点需要修改**前驱节点**的 next 指针，单向链表需要从头遍历找前驱，双向链表直接 `node.prev` 拿到。

### 为什么节点要存 key？

超容量时，要从 HashMap 中移除被淘汰的节点。`map.remove(key)` 需要 key，所以节点里必须存 key。

### 为什么用哨兵（dummy head/tail）？

避免处理空链表、头尾为 null 的边界情况。所有插入删除都在 `head.next` 和 `tail.prev` 位置操作，代码统一。

---

## 执行过程图解

```
capacity = 2

put(1,1):  map={1:N1},  list: head↔N1↔tail
put(2,2):  map={1:N1,2:N2},  list: head↔N1↔N2↔tail
get(1):    N1移到尾部,  list: head↔N2↔N1↔tail
put(3,3):  超容量! 删头部N2,  map={1:N1,3:N3},  list: head↔N1↔N3↔tail
get(2):    map中没有2 → -1
put(4,4):  超容量! 删头部N1,  map={3:N3,4:N4},  list: head↔N3↔N4↔tail
```

---

## 易错点

1. **节点要存 key**：淘汰时需要用 `oldest.key` 从 HashMap 中移除，不存 key 就找不到
2. **moveToTail 不是直接插入**：要先 `removeNode` 再 `addToTail`（先断开再重接）
3. **put 已存在的 key**：要更新 value 并 `moveToTail`，不是新建节点
4. **size 的维护**：只在新增节点时 `size++`，淘汰时 `size--`，更新已有节点时不变

---

## 记忆口诀

```
LRU = HashMap 查得快 + 双向链表移得快。
get 命中移尾部，put 满了删头部，
节点存 key 方便 map 删，哨兵兜底省边界。
```
