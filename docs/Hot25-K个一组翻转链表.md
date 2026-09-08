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
    // ① 先数剩余节点够不够 k 个，同时 temp 停在第 k+1 个节点
    ListNode temp = head;
    int count = 0;
    while (temp != null && count < k) {
        temp = temp.next;
        count++;
    }
    if (count < k) return head;  // 不够 k 个，保持原样

    // ② 够 k 个，翻转前 k 个节点，返回翻转后的新头
    ListNode newHead = reverseList(head, k);

    // ③ temp 就是下一组的头，递归翻转后接到当前组的尾部(head)
    head.next = reverseKGroup(temp, k);
    return newHead;
}

// 翻转前 k 个节点，返回翻转后的新头
ListNode reverseList(ListNode head, int k) {
    ListNode prev = null, curr = head;
    int count = 0;
    while (curr != null && count < k) {
        ListNode temp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = temp;
        count++;
    }
    head.next = curr;  // 翻转后 head 变成尾部，接上剩余部分
    return prev;
}
```

> 关键点：第①步在数节点时，`temp` 已经走到了第 k+1 个节点，正好是下一组的头。所以 `reverseList` 只需返回翻转后的新头即可，无需再用数组回传下一组的位置。

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
  ① 数够 k 个：temp 从 1 走到 3，count=2 ≥ k ✓（temp 停在下一组头 3）
  ② 翻转前2个：reverseList(1→2, 2) → 新头=2
     链表变成：2→1→(3→4→5)
  ③ head(1).next = reverseKGroup(3→4→5, 2)
     → ① temp 走到 5，count=2 ≥ k ✓
     → ② reverseList(3→4, 2) → 新头=4，得 4→3→(5)
     → ③ head(3).next = reverseKGroup(5, 2)
          → ① count=1 < k → 返回 5（保持原样）
     → head(3).next = 5，得 4→3→5
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

1. **先数再翻**：必须先数够 k 个才翻转，不够就原样返回。不能边翻边数
2. **翻转后 head 变成尾部**：`head.next = curr`（curr 是第 k+1 个节点），这步不能漏
3. **复用 temp 指针**：第①步数数时 temp 已停在第 k+1 个节点，直接作为下一组头，无需额外返回
4. **递归拼接顺序**：`head.next = reverseKGroup(temp, k)` — 翻转后 head 是尾部，它的 next 要接递归结果

---

## 记忆口诀

```
K个一组翻：先数够不够，够就翻，不够就留，
翻转后头变尾，尾接递归下一组。
```
