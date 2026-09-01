# 合并 K 个升序链表（Merge K Sorted Lists）

## 题目描述

给你一个链表数组，每个链表都已按**升序**排列。请将所有链表合并到一个升序链表中，返回合并后的链表。

示例：
```
输入：lists = [[1,4,5],[1,3,4],[2,6]]
输出：[1,1,2,3,4,4,5,6]

解释：
  L1: 1 → 4 → 5
  L2: 1 → 3 → 4
  L3: 2 → 6
  合并：1 → 1 → 2 → 3 → 4 → 4 → 5 → 6
```

---

## 核心思路

三种解法围绕同一个问题：**怎么高效找到 k 个链表中的最小值？**

| 解法 | 怎么找最小值 | 时间 | 空间 |
|------|-------------|------|------|
| 顺序合并 | 每次线性扫描已合并结果 | O(k²n) | O(1) |
| 分治合并 | 二分后两两合并（归并思想） | O(kn log k) | O(log k) |
| 优先队列 | 最小堆，堆顶即最小值 | O(kn log k) | O(k) |

---

## 解法一：顺序合并

### 原理图解

```text
ans = null
第1轮：ans = merge(null, L1)        = [1 → 4 → 5]
第2轮：ans = merge([1→4→5], L2)     = [1 → 1 → 3 → 4 → 4 → 5]
第3轮：ans = merge([1→1→3→4→4→5], L3) = [1 → 1 → 2 → 3 → 4 → 4 → 5 → 6]
```

逐个将链表合并到 `ans` 上，第 i 轮合并时 `ans` 的长度是 i×n，所以总比较次数 = n + 2n + 3n + ... + kn = O(k²n)。

### 代码实现

```java
public ListNode mergeKLists(ListNode[] lists) {
    ListNode ans = null;
    for (int i = 0; i < lists.length; i++) {
        ans = mergeTwoLists(ans, lists[i]);
    }
    return ans;
}

public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null || l2 == null) {
        return l1 == null ? l2 : l1;
    }
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    while (l1 != null && l2 != null) {
        if (l1.val < l2.val) {
            curr.next = l1;
            l1 = l1.next;
        } else {
            curr.next = l2;
            l2 = l2.next;
        }
        curr = curr.next;
    }
    curr.next = l1 == null ? l2 : l1;
    return dummy.next;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(k²n) | 第 i 轮合并 O(in)，共 k 轮 |
| 空间 | O(1) | 只用了几个指针 |

---

## 解法二：分治合并

### 原理图解

```text
lists = [L1, L2, L3, L4]

第1层递归：
  merge(0,3)
  ├── merge(0,1) → merge(L1, L2)
  └── merge(2,3) → merge(L3, L4)

第2层递归：
  merge(合并结果) → 最终合并成一个有序链表
```

类似归并排序：把 k 个链表二分成两半，递归合并后再合并结果。每层合并总量 O(kn)，共 log k 层。

### 代码实现

```java
public ListNode mergeKLists(ListNode[] lists) {
    return merge(lists, 0, lists.length - 1);
}

public ListNode merge(ListNode[] lists, int left, int right) {
    // 递归终止：只有一个链表，直接返回
    if (left == right) {
        return lists[left];
    }
    // 边界情况：空区间
    if (left > right) {
        return null;
    }
    // 二分：将区间分成两半
    int mid = (left + right) / 2;
    ListNode leftList = merge(lists, left, mid);
    ListNode rightList = merge(lists, mid + 1, right);
    return mergeTwoLists(leftList, rightList);
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(kn log k) | 每层合并 O(kn)，共 log k 层 |
| 空间 | O(log k) | 递归栈 |

---

## 解法三：优先队列（最小堆）

### 原理图解

```text
初始化堆：[L1头=1, L2头=1, L3头=2]

第1轮：poll 1(L1) → 结果: [1]     → 将 L1.next=4 入堆 → 堆: [1(L2), 2(L3), 4(L1)]
第2轮：poll 1(L2) → 结果: [1,1]   → 将 L2.next=3 入堆 → 堆: [2(L3), 3(L2), 4(L1)]
第3轮：poll 2(L3) → 结果: [1,1,2] → 将 L3.next=6 入堆 → 堆: [3(L2), 4(L1), 6(L3)]
...
```

堆里始终维护 k 个链表的"当前最小节点"，每次取堆顶接到结果末尾，再把它的 next 入堆。

### 代码实现

```java
public ListNode mergeKLists(ListNode[] lists) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;

    // 最小堆：按节点值排序，堆顶始终是最小值节点
    PriorityQueue<ListNode> pq = new PriorityQueue<>(
            (a, b) -> (a.val - b.val));

    // 初始化：将 k 个链表的头节点（非 null）加入堆中
    for (ListNode list : lists) {
        if (list != null) {
            pq.add(list);
        }
    }

    // 每次取出最小值节点，接到结果链表末尾
    while (!pq.isEmpty()) {
        ListNode node = pq.poll();      // 取出当前最小
        curr.next = node;               // 接到结果链表
        if (node.next != null) {
            pq.add(node.next);          // 该节点的下一个入堆
        }
        curr = curr.next;               // 结果指针后移
    }
    return dummy.next;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(kn log k) | 共 kn 个节点，每次 poll/add O(log k) |
| 空间 | O(k) | 堆中最多同时存 k 个节点 |

---

## 三种解法对比

| 维度 | 顺序合并 | 分治合并 | 优先队列 |
|------|---------|---------|---------|
| 时间 | **O(k²n)** | O(kn log k) | O(kn log k) |
| 空间 | O(1) | O(log k) | O(k) |
| 代码量 | 最短 | 中等 | 中等 |
| 核心思想 | 暴力逐个合并 | 归并排序思想 | 堆维护最小值 |

**为什么顺序合并最慢？** 第 i 轮合并时 ans 已经有 i×n 个节点，每次合并都要遍历整个 ans。分治和堆通过"分而治之"或"堆结构"避免了重复遍历。

---

## 易错点

1. **顺序合并的复杂度陷阱**：看似每轮合并是 O(n)，但 ans 在不断增长，实际是 O(in)，总复杂度 O(k²n)
2. **分治合并的 `left > right` 判断**：空数组 `lists = []` 时，`left=0, right=-1`，不处理会越界
3. **优先队列的 null 判断**：`lists` 中可能有 null 链表，入堆前必须判空
4. **优先队列的比较器**：`(a, b) -> (a.val - b.val)` 按节点值排序，不是按节点引用

---

## 记忆口诀

```
合并K表三招破：
顺序逐个接，简单但慢 O(k²n)
分治二分拆，归并思想 O(kn log k)
最小堆取头，堆顶永远最小值
```
