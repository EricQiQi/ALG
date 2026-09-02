# LRU 缓存（Least Recently Used Cache）

## 题目描述

请你设计并实现一个满足 LRU (最近最少使用) 缓存约束的数据结构。

实现 `LRUCache` 类：
- `LRUCache(int capacity)` 以正整数作为容量初始化 LRU 缓存
- `int get(int key)` 如果关键字存在于缓存中，则返回关键字的值，否则返回 -1
- `void put(int key, int value)` 如果关键字已经存在，则变更其数据值；如果不存在，则插入该组 key-value。如果插入导致数量超过 capacity，则应该逐出最久未使用的关键字

> 函数 `get` 和 `put` 必须以 **O(1)** 的平均时间复杂度运行。

示例：
```
输入：
["LRUCache", [2], "put", [1,1], "put", [2,2], "get", [1], "put", [3,3], "get", [2], "put", [4,4], "get", [1], "get", [3], "get", [4]]

输出：
[null, null, null, 1, null, -1, null, 3, 4]
```

---

## 核心思路

要求 O(1) 的 get 和 put，需要同时满足两个条件：

| 需求 | 数据结构 | 作用 |
|------|---------|------|
| O(1) 查找 key | HashMap | 通过 key 直接定位节点 |
| O(1) 删除/插入节点 | 双向链表 | 维护访问顺序，头尾操作 O(1) |

**`LinkedHashMap` = HashMap + 双向链表**，天然具备这两个能力。

---

## 解法：LinkedHashMap

### 原理图解

```text
底层结构：HashMap + 双向链表（accessOrder = true）

头 ←→ [2,2] ←→ [1,1] ←→ [3,3] ←→ 尾
       最久未使用               最近使用

get/put 访问某节点 → 自动移到尾部（最近使用端）
超容量 → 自动淘汰头部（最久未使用端）
```

### 三个关键机制

**1. `accessOrder = true`**

构造方法 `super(capacity, 0.75f, true)` 开启**访问顺序模式**：每次 `get` 或 `put` 都会把被访问的节点移到链表尾部。

```text
put(1,1) put(2,2) 后：     get(1) 后：
头 ←→ [1] ←→ [2] ←→ 尾    头 ←→ [2] ←→ [1] ←→ 尾
```

**2. `removeEldestEntry` 自动淘汰**

每次 `put` 之后，LinkedHashMap 内部自动调用此方法，返回 true 就删除链表头节点：

```java
protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
    return size() > capacity;  // 超容量就淘汰头部
}
```

**3. 完整流程**

```text
操作              链表状态（头→尾）       说明
put(1,1)         1                      插入
put(2,2)         1 → 2                  插入
get(1)           2 → 1                  访问1，移到尾部
put(3,3)         2→1→3 → 淘汰头 → 1→3    超容量，淘汰头部2
get(2)           1 → 3                  2已淘汰，返回-1
put(4,4)         1→3→4 → 淘汰头 → 3→4    超容量，淘汰头部1
```

### 代码实现

```java
public class LRUCache extends LinkedHashMap<Integer, Integer> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);  // accessOrder = true，开启访问顺序
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
        return size() > capacity;  // 超容量自动淘汰最久未使用的元素
    }
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(1) | HashMap 查找 + 链表移动/删除，均 O(1) |
| 空间 | O(capacity) | HashMap + 双向链表存储 |

---

## 易错点

1. **第三个参数忘记传 `true`**：默认 `false` 是插入顺序，不会在访问时移动节点到尾部，LRU 逻辑直接失效
2. **`removeEldestEntry` 用 `>=` 而非 `>`**：`size() > capacity` 才淘汰，如果用 `>=` 会在刚好满容量时多淘汰一个
3. **`get` 用 `getOrDefault` 而非 `get`**：`HashMap.get()` 返回 null，需要的是不存在的 key 返回 -1

---

## 记忆口诀

```
LRU 缓存 LinkedHashMap，
HashMap 查得快，双向链表排顺序，
accessOrder 开 true，访问自动移尾部，
removeEldest 判容量，超了淘汰最老的。
```
