# 合并K个升序链表（Merge K Sorted Lists）

## 题目描述

给你一个链表数组，每个链表都已按**升序**排列，将所有链表合并到一个升序链表中。

```
输入：lists = [[1,4,5], [1,3,4], [2,6]]
输出：[1,1,2,3,4,4,5,6]
```

---

## 三种方法对比

| | 方法1 顺序合并 | 方法2 分治 | 方法3 优先队列 |
|--|-------------|----------|-------------|
| 思路 | 一个一个合并 | 两两配对，二分递归 | 最小堆取最小 |
| 时间 | O(k²n) | O(kn log k) | O(kn log k) |
| 空间 | O(1) | O(log k) 递归栈 | O(k) 堆 |
| 推荐度 | 思路直观但慢 | **推荐** | **推荐** |

---

## 方法1：顺序合并

### 思路

把第一个链表当结果，依次和后面的链表两两合并。

```
ans = null
ans = merge(null, [1,4,5])     → [1,4,5]
ans = merge([1,4,5], [1,3,4])  → [1,1,3,4,4,5]
ans = merge([1,1,3,4,4,5], [2,6]) → [1,1,2,3,4,4,5,6]
```

### 代码

```java
public ListNode mergeKLists(ListNode[] lists) {
    ListNode ans = null;
    for (ListNode list : lists) {
        ans = mergeTwoLists(ans, list);
    }
    return ans;
}
```

> 问题：每次合并 ans 都在变长，第 i 次合并要遍历 i*n 个节点，总时间 O(k²n)。

---

## 方法2：分治合并（推荐）

### 思路

**和归并排序一样的思路：k 个链表二分递归，两两合并。**

```
第0轮：[1,4,5]  [1,3,4]  [2,6]
         \      /           |
第1轮：  [1,1,3,4,4,5]   [2,6]
           \              /
第2轮：   [1,1,2,3,4,4,5,6]
```

每层合并的总节点数都是 kn（只是分成更少的组），共 log k 层 → O(kn log k)。

### 代码

```java
public ListNode mergeKLists(ListNode[] lists) {
    return merge(lists, 0, lists.length - 1);
}

public ListNode merge(ListNode[] lists, int left, int right) {
    if (left == right) return lists[left];
    if (left > right) return null;

    int mid = (left + right) / 2;
    ListNode leftList = merge(lists, left, mid);
    ListNode rightList = merge(lists, mid + 1, right);
    return mergeTwoLists(leftList, rightList);
}
```

---

## 方法3：优先队列（最小堆）

### 思路

**维护一个大小为 k 的最小堆，堆顶永远是当前最小值。**

```
初始堆：[1(来自list1), 1(来自list2), 2(来自list3)]

poll 1(list1) → 接上 → list1的下一个4入堆
堆：[1(list2), 2(list3), 4(list1)]

poll 1(list2) → 接上 → list2的下一个3入堆
堆：[2(list3), 3(list2), 4(list1)]

...直到堆空
```

### 代码

```java
public ListNode mergeKLists(ListNode[] lists) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;

    PriorityQueue<ListNode> pq = new PriorityQueue<>(
        (a, b) -> a.val - b.val);

    // 初始化：k 个链表的头节点入堆
    for (ListNode list : lists) {
        if (list != null) pq.add(list);
    }

    while (!pq.isEmpty()) {
        ListNode node = pq.poll();    // 取最小
        curr.next = node;             // 接到结果
        if (node.next != null) {
            pq.add(node.next);        // 下一个入堆
        }
        curr = curr.next;
    }
    return dummy.next;
}
```

---

## 公共模板：合并两个有序链表

三种方法底层都用到了这个：

```java
ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0), curr = dummy;
    while (l1 != null && l2 != null) {
        curr.next = (l1.val < l2.val) ? l1 : l2;
        if (l1.val < l2.val) l1 = l1.next; else l2 = l2.next;
        curr = curr.next;
    }
    curr.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```

---

## 易错点

1. **顺序合并的时间复杂度**：不是 O(kn) 而是 O(k²n)，因为 ans 在变长
2. **分治的终止条件**：`left == right` 返回 `lists[left]`（只剩一个链表），`left > right` 返回 null（空区间）
3. **优先队列初始化**：只加入非 null 的头节点，`if (list != null)` 不能漏
4. **优先队列取出后要补入下一个**：`node.next != null` 时 `pq.add(node.next)`，否则堆会越来越小提前结束

---

## 记忆口诀

```
合并K个链表三种招：
顺序合并最朴素，分治二分最快，
优先队列堆顶取，每次最小接上去。
```
