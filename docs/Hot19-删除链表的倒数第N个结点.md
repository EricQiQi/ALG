# 算法笔记：删除链表的倒数第 N 个结点（力扣 19 题）

## 题目描述
给你一个链表，删除链表的**倒数第 n 个**结点，并返回链表的头结点。

```
输入: head = 1→2→3→4→5, n = 2
输出: 1→2→3→5   （删掉倒数第 2 个，即值为 4 的节点）
```

---

## 核心思想

难点在于链表**不能随机访问**，"倒数第 n 个"没法直接定位，只能靠两个指针拉开固定间距来找。

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

### 快慢指针（一次遍历）

源码中对应 `Hot19_removeNthFromEnd` 的 `removeNthFromEnd_2`。

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

**1. 删头节点要特判**
`fast` 先走完 n 步后如果 `fast == null`，说明 n 恰好等于链表长度，要删的就是头节点，直接返回 `head.next`。

**2. 要停在待删节点的"前一个"**
删除靠的是 `前一个.next = 待删.next`，所以定位目标是**前驱**，不是待删节点本身。循环条件写 `fast.next != null`（而非 `fast != null`）就是为了让 slow 正好停在前驱。

> **一劳永逸的写法**：加一个哑节点 `dummy.next = head`，让 slow 从 dummy 出发，就不用对"删头节点"单独特判了。

---

## 复杂度分析

* **时间复杂度**：$O(L)$，L 为链表长度。
* **空间复杂度**：$O(1)$。

---

## 💡 记忆口诀

> **倒数第 n 不好找，快指针先走 n 步拉开距离；两指针同速走，快到尾时慢指针正好在待删的前一个。删头节点记得特判（或用哑节点免特判）。**

> 关联：快慢指针是链表题的核心技巧，见 [环形链表](Hot141-环形链表.md)、[回文链表](Hot234-回文链表.md)（找中点）。
