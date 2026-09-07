# 算法笔记：删除链表的倒数第 N 个结点（力扣 19 题）

## 题目描述
给你一个链表，删除链表的**倒数第 n 个**结点，并返回链表的头结点。

```
输入: head = 1→2→3→4→5, n = 2
输出: 1→2→3→5   （删掉倒数第 2 个，即值为 4 的节点）
```

---

## 核心思想

难点在于链表**不能随机访问**，"倒数第 n 个"没法直接定位。有两种思路：

### 方法一：两次遍历（先算长度）
先遍历一遍得到长度 `len`，倒数第 n 个 = 正数第 `len - n + 1` 个。要删除它，需走到它的**前一个**节点（正数第 `len - n` 个），改指针跨过它。

### 方法二：快慢指针（一次遍历，推荐）
让 `fast` 先走 n 步，与 `slow` 拉开 n 个节点的间距；然后两者**同速前进**，当 `fast` 到达末尾时，`slow` 正好停在**待删节点的前一个**。

```
1 → 2 → 3 → 4 → 5,  n=2

fast 先走 2 步：fast 在 3，slow 在 1（间距 2）
同速走，直到 fast.next == null（fast 在 5）：
   slow: 1 → 2 → 3   （停在待删节点 4 的前一个）
slow.next = slow.next.next  → 跨过 4
结果：1 → 2 → 3 → 5 ✅
```

---

## 代码实现 (Java)

### 方法一：先算长度

```java
public ListNode removeNthFromEnd_1(ListNode head, int n) {
    ListNode temp = head;
    int len = 0;
    while (temp != null) { len++; temp = temp.next; }

    if (len == n) return head.next;   // 删的是头节点，特判

    temp = head;                      // 关键：统计长度后 temp 已到尾部，必须重置回头
    int index = 0;
    while (index < len - n - 1) {     // 走到待删节点的前一个
        temp = temp.next;
        index++;
    }
    temp.next = temp.next.next;
    return head;
}
```

### 方法二：快慢指针（一次遍历）

```java
public ListNode removeNthFromEnd_2(ListNode head, int n) {
    ListNode fast = head, slow = head;
    for (int i = 0; i < n; i++) {     // 1. fast 先走 n 步
        fast = fast.next;
    }
    if (fast == null) return head.next; // 2. fast 走出链表 → 删的是头节点
    while (fast.next != null) {       // 3. 同速前进，fast 到尾时 slow 在待删前一个
        fast = fast.next;
        slow = slow.next;
    }
    slow.next = slow.next.next;       // 4. 跨过待删节点
    return head;
}
```

---

## ❗关键易错点

**1. 方法一：统计长度后指针已到尾部，必须 `temp = head` 重置**
第一趟 while 结束时 `temp` 已经是 `null`，若不重置直接用它定位，会 NPE。这是最容易漏的一步。

**2. 删头节点要特判**
当 `len == n`（方法一）或 `fast == null`（方法二）时，要删的就是头节点，直接返回 `head.next`。

**3. 要停在待删节点的"前一个"**
删除靠的是 `前一个.next = 待删.next`，所以定位目标是**前驱**，不是待删节点本身。方法一的循环条件 `len - n - 1`、方法二的 `fast.next != null`（而非 `fast != null`）都是为了让 slow 停在前驱。

> **一劳永逸的写法**：加一个哑节点 `dummy.next = head`，让 slow 从 dummy 出发，就不用对"删头节点"单独特判了。

---

## 两种方法权衡

| | 方法一 两次遍历 | 方法二 快慢指针 |
| :--- | :--- | :--- |
| **遍历次数** | 2 趟 | 1 趟 |
| **时间复杂度** | $O(L)$ | $O(L)$ |
| **代码复杂度** | 需重置指针 + 定位前驱 | 拉开间距后同步走 |
| **适用** | 直观好懂 | 面试更优，只遍历一次 |

两者时间复杂度都是 $O(L)$，但方法二只走一趟，是更被推崇的答案。

---

## 复杂度分析

* **时间复杂度**：$O(L)$，L 为链表长度。
* **空间复杂度**：$O(1)$。

---

## 💡 记忆口诀

> **倒数第 n 不好找，快指针先走 n 步拉开距离；两指针同速走，快到尾时慢指针正好在待删的前一个。删头节点记得特判（或用哑节点免特判）。**

> 关联：快慢指针是链表题的核心技巧，见 [环形链表](Hot141-环形链表.md)、[回文链表](Hot234-回文链表.md)（找中点）。
