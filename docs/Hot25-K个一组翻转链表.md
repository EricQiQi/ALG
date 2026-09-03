# K个一组翻转链表（Reverse Nodes in k-Group）

## 题目描述

给你一个链表，每 **k** 个节点一组进行翻转，返回翻转后的链表。如果节点总数不是 k 的整数倍，最后剩余的节点**保持原有顺序**。

```
输入：1 → 2 → 3 → 4 → 5, k = 2
输出：2 → 1 → 4 → 3 → 5

输入：1 → 2 → 3 → 4 → 5, k = 3
输出：3 → 2 → 1 → 4 → 5
```

---

## 核心思路

**每次处理 k 个节点：先检查够不够 k 个，够就翻转，不够就保持原样。翻转后递归处理下一组。**

```
k=3, 链表：1 → 2 → 3 → 4 → 5

第1组（够3个）：1→2→3 翻转为 3→2→1
第2组（只剩2个 < 3）：4→5 保持不动

结果：3 → 2 → 1 → 4 → 5
```

---

## 代码实现

```java
public ListNode reverseKGroup(ListNode head, int k) {
    // ① 检查剩余节点是否够 k 个
    ListNode check = head;
    int count = 0;
    while (check != null && count < k) {
        check = check.next;
        count++;
    }
    if (count < k) return head;  // 不够 k 个，保持原样

    // ② 够 k 个，翻转前 k 个节点
    ListNode[] result = reverseList(head, k);
    // result[0] = 翻转后的新头，result[1] = 下一组的头

    // ③ 递归处理下一组，拼接
    head.next = reverseKGroup(result[1], k);
    return result[0];
}

// 翻转 k 个节点，返回 [新头, 下一组头]
ListNode[] reverseList(ListNode head, int k) {
    ListNode prev = null, curr = head;
    for (int i = 0; i < k; i++) {
        ListNode temp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = temp;
    }
    head.next = curr;  // 翻转后 head 变成尾部，接上剩余部分
    return new ListNode[]{prev, curr};
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个节点被访问常数次 |
| 空间 | O(n/k) | 递归栈深度 = 组数 |

---

## 图解

```
k=2, 链表：1 → 2 → 3 → 4 → 5

reverseKGroup(1→2→3→4→5, 2)：
  检查：count=2 ≥ k ✓
  翻转前2个：reverseList(1→2, 2)
    → [新头=2, 下一组=3]
    链表变成：2→1, 3→4→5
  head(1).next = reverseKGroup(3→4→5, 2)
    → 检查 count=2 ≥ k ✓
    → 翻转：reverseList(3→4, 2) → [4, 5]
    → head(3).next = reverseKGroup(5, 2)
      → 检查 count=1 < k → 返回 5
    → head(3).next = 5 → 4→3→5
  head(1).next = 4→3→5
  返回 2→1→4→3→5
```

---

## 和 Hot24 的关系

**Hot24（两两交换）就是 k=2 的 Hot25。** 思路完全一样：

| | Hot24 两两交换 | Hot25 K个一组翻转 |
|--|--------------|-----------------|
| 每次处理 | 2个节点 | k个节点 |
| 不够时 | 不足2个直接返回 | 不足k个直接返回 |
| 翻转方式 | 交换指针 | 翻转k个节点 |
| 后续处理 | 递归 | 递归 |

---

## 易错点

1. **先检查再翻转**：必须先数够 k 个才翻转，不够就原样返回。不能边翻边数
2. **翻转后 head 变成尾部**：`head.next = curr`（curr 是第 k+1 个节点），这步不能漏
3. **返回值数组**：`reverseList` 返回 `[新头, 下一组头]`，用数组一次返回两个值
4. **递归拼接顺序**：`head.next = reverseKGroup(result[1], k)` — 翻转后 head 是尾部，它的 next 要接递归结果

---

## 记忆口诀

```
K个一组翻：先数够不够，够就翻，不够就留，
翻转后头变尾，尾接递归下一组。
```
