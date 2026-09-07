# 算法笔记：两数相加（力扣 2 题）

## 题目描述
给你两个**非空**链表，表示两个非负整数。每位数字按**逆序**存储，每个节点存一位数字。将两数相加，以相同形式返回表示和的链表。

```
输入: l1 = 2→4→3, l2 = 5→6→4   （表示 342 + 465）
输出: 7→0→8                     （表示 807）
```

> **逆序存储是题目的"善意"**：链表头正好是个位，可以从低位到高位逐位相加，天然符合竖式加法。

---

## 核心思想

> **模拟竖式加法：逐位相加 + 进位 carry。**

- 用一个 `pre`（carry）记录进位，初始 0；
- 每一位 `sum = l1.val + l2.val + pre`，本位写 `sum % 10`，进位更新为 `sum / 10`；
- 三条腿：**两链都非空**时一起走；**某一条先走完**，另一条继续（还要带上进位）；
- 最后若 `pre > 0`，**补一个最高位节点**。

---

## 图解：竖式模拟

```
    3 4 2   (l1: 2→4→3 逆序)
  + 4 6 5   (l2: 5→6→4 逆序)
  -------
    8 0 7   (结果: 7→0→8 逆序)

逐位（从链表头=个位开始）：
  2+5=7        → 写7, carry=0
  4+6=10       → 写0, carry=1
  3+4+1(carry)=8 → 写8, carry=0
结果链表 7→0→8
```

---

## 代码实现 (Java)

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode preHead = new ListNode();  // 哑节点，简化头部处理
    ListNode head = preHead;
    int pre = 0;                        // 进位

    // 1. 两链都非空：一起走
    while (l1 != null && l2 != null) {
        int sum = l1.val + l2.val + pre;
        pre = sum / 10;
        head.next = new ListNode(sum % 10);
        head = head.next;
        l1 = l1.next;
        l2 = l2.next;
    }
    // 2. l1 有剩余
    while (l1 != null) {
        int sum = l1.val + pre;
        pre = sum / 10;
        head.next = new ListNode(sum % 10);
        head = head.next;
        l1 = l1.next;
    }
    // 3. l2 有剩余
    while (l2 != null) {
        int sum = l2.val + pre;
        pre = sum / 10;
        head.next = new ListNode(sum % 10);
        head = head.next;
        l2 = l2.next;
    }
    // 4. 最高位还有进位，补一个节点
    if (pre > 0) {
        head.next = new ListNode(pre % 10);
    }
    return preHead.next;
}
```

> 三段循环可合并成一段 `while (l1 != null || l2 != null || pre != 0)`，用 `int x = l1 != null ? l1.val : 0` 补零，代码更短。本实现拆成三段，逻辑更清晰。

---

## ❗关键易错点

**1. 最后一定要检查进位 `pre > 0`**
如 `5 + 5 = 10`，两链都走完后 carry=1，不补节点就丢了最高位，结果错成 `0` 而非 `0→1`。

**2. 短的走完，长的要继续（且带着 carry）**
两链长度可能不同，剩余部分仍需逐位加进位，不能直接拼上去。

**3. 用哑节点 `preHead` 起手**
避免对"第一个节点"做特殊判断，最后返回 `preHead.next`。

---

## 复杂度分析

* **时间复杂度**：$O(\max(m, n))$。遍历较长链表一遍。
* **空间复杂度**：$O(1)$（不算返回的结果链表）。

---

## 💡 记忆口诀

> **逆序存储正好从个位加，逐位求和记得带进位；两链一长一短分别收尾，最高位有进位别忘补一个。**

> 关联：哑节点技巧贯穿链表题，见 [合并两个有序链表](Hot21-合并两个有序链表.md)、[反转链表](Hot206-反转链表.md)。
