# K 个一组翻转链表（Reverse Nodes in k-Group）

## 题目描述

给你一个链表，每 **k** 个节点一组进行翻转，请你返回翻转后的链表。

- k 是一个正整数，它的值小于或等于链表的长度
- 如果节点总数不是 k 的整数倍，**请将最后剩余的节点保持原有顺序**
- 进阶：请尝试使用 **O(1)** 额外空间解决此问题

示例：
```
输入：1 → 2 → 3 → 4 → 5, k=2
输出：2 → 1 → 4 → 3 → 5

输入：1 → 2 → 3 → 4 → 5, k=3
输出：3 → 2 → 1 → 4 → 5
```

---

## 核心思路

这道题是 [反转链表（Hot206）](Hot206-反转链表.md) 的升级版，本质是**分段反转**：

```text
k=2 时：
原链表：  1 → 2  |  3 → 4  |  5
反转后：  2 → 1  |  4 → 3  |  5     ← 最后不足2个，保持原样
```

| 步骤 | 做什么 | 关键细节 |
|------|--------|---------|
| ① 数节点 | 检查剩余节点是否够 k 个 | **先数再翻**，不够就直接返回 |
| ② 反转 k 个 | 复用 206 题的迭代反转，只走 k 步 | 反转后 `head` 变成这组的尾节点 |
| ③ 递归连接 | 把当前组的尾节点 `next` 指向下一组的反转结果 | 递归处理后续分组 |

---

## 原理图解

以 `1→2→3→4→5, k=2` 为例：

```text
第1层：reverseKGroup(1→2→3→4→5, 2)
  ① 数2个 → 够
  ② reverseList → 反转前2个：2→1，1.next 暂接 3
     返回 [2→1, 3→4→5]
  ③ 1.next = reverseKGroup(3→4→5, 2)   ← 递归

第2层：reverseKGroup(3→4→5, 2)
  ① 数2个 → 够
  ② reverseList → 反转前2个：4→3，3.next 暂接 5
     返回 [4→3, 5]
  ③ 3.next = reverseKGroup(5, 2)        ← 递归

第3层：reverseKGroup(5, 2)
  ① 数1个 → 不够！直接返回 5           ← 终止条件

回溯拼接：
  3.next = 5   →  4→3→5
  1.next = 4→3→5  →  2→1→4→3→5 ✅
```

---

## 代码实现

```java
public ListNode reverseKGroup(ListNode head, int k) {
    // ① 先检查剩余节点是否够 k 个，不够直接返回（不反转）
    ListNode check = head;
    int count = 0;
    while (check != null && count < k) {
        check = check.next;
        count++;
    }
    if (count < k) {
        return head;  // 不足 k 个，保持原顺序
    }

    // ② 够 k 个，执行反转
    ListNode[] result = reverseList(head, k);
    // result[0] = 反转后的新头，result[1] = 下一组的头
    head.next = reverseKGroup(result[1], k);
    return result[0];
}

public ListNode[] reverseList(ListNode head, int k) {
    ListNode prev = null;
    ListNode curr = head;
    int count = 0;
    while (curr != null && count < k) {
        ListNode temp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = temp;
        count++;
    }
    // 连接反转后的链表和剩余的链表
    head.next = curr;
    ListNode[] result = {prev, curr};
    return result;
}
```

---

## 关键细节解析

### 为什么必须「先数再翻」？

如果先反转再判断，当最后一组不足 k 个时：
- 已经反转了的节点需要**再翻回去**才能恢复原顺序
- 而且递归到 `null` 时没有终止条件，会触发 NPE

先数后翻，天然避免了这两个问题。

### `reverseList` 中 `head.next = curr` 的含义

```text
反转前：head = 节点1（反转后它会变成这组的最后一个）
反转后：head.next 需要接上「下一组的第一个节点」= curr

即：节点1 → curr（下一组还没反转的头）
后续递归会把 curr 开始的 k 个节点反转，接在节点1后面
```

### 递归调用的执行顺序

```java
head.next = reverseKGroup(result[1], k);
```

**必须先执行** `reverseList`，再递归——因为 `head` 在反转后变成了当前组的尾节点，它的 `next` 要等递归返回后才知道接什么。

---

## 复杂度分析

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个节点被访问常数次（数一次 + 反转一次） |
| 空间 | O(n/k) | 递归栈深度 = 分组数 ⌈n/k⌉ |

> **进阶 O(1) 空间**：把递归改成迭代，用 `dummyNode + temp` 指针逐组处理，可消除递归栈。但逻辑更复杂，面试中递归版已足够展示思路。

---

## 与 Hot206 反转链表的对比

| 维度 | Hot206 反转链表 | Hot25 K个一组反转 |
|------|----------------|------------------|
| 反转范围 | 整条链表 | 每 k 个节点一组 |
| 终止条件 | `head == null \|\| head.next == null` | 剩余节点不足 k 个 |
| 核心操作 | `curr.next = prev` 逐节点掉头 | 同左，但只走 k 步 |
| 连接方式 | 直接返回 `prev` | 递归连接各组 |

---

## 易错点

1. **没有「先数再翻」**：最后一组不足 k 个时，已反转的节点无法恢复，且递归到 null 触发 NPE
2. **`reverseList` 里漏掉 `head.next = curr`**：反转后当前组的尾节点（原 head）没有接上后续链表，导致断链
3. **递归调用顺序搞反**：必须先 `reverseList` 拿到 `result[1]`，再递归——不能先递归再反转
4. **`reverseList` 循环条件写 `count <= k`**：多反转了一个节点，分组边界错位

---

## 记忆口诀

```
K个一组翻转：
先数后翻是前提，不够k个原样还
reverseList 走k步，head接递归连下段
```
