# 两两交换链表中的节点（Swap Nodes in Pairs）

## 题目描述

给你一个链表，**每两个相邻节点交换**，返回交换后的链表。要求不能修改节点内部的值，只能进行节点交换。

```
输入：1 → 2 → 3 → 4
输出：2 → 1 → 4 → 3
```

---

## 方法1：迭代

### 思路

用 `dummy` 虚拟头简化操作，每次处理一对节点：

```
dummy → 1 → 2 → 3 → 4
  curr

第1轮（curr=dummy）：
  node1=1, node2=2
  curr.next = 2, 1.next = 3, 2.next = 1
  dummy → 2 → 1 → 3 → 4
  curr 移到 1（交换后的第二个，即下一轮的前一个）

第2轮（curr=1）：
  node1=3, node2=4
  1.next = 4, 3.next = null, 4.next = 3
  dummy → 2 → 1 → 4 → 3
```

### 代码

```java
public ListNode swapPairs(ListNode head) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode curr = dummy;

    while (curr.next != null && curr.next.next != null) {
        ListNode node1 = curr.next;       // 第1个
        ListNode node2 = curr.next.next;  // 第2个

        curr.next = node2;    // curr 指向第2个
        node1.next = node2.next;  // 第1个接上后面的
        node2.next = node1;   // 第2个指向第1个

        curr = node1;         // curr 移到下一对的前一个
    }
    return dummy.next;
}
```

### 复杂度

| 类型 | 复杂度 |
|------|--------|
| 时间 | O(n) |
| 空间 | O(1) |

---

## 方法2：递归

### 思路

**我只管前两个节点的交换，后面的递归帮我处理。**

```
swapPairs(1→2→3→4)：
  newHead = 2（第2个变成新头）
  1.next = swapPairs(3→4) = 4→3
  2.next = 1
  结果：2→1→4→3
```

### 递归三步思考法

写递归不用在脑子里把整条链表展开，只问自己三个问题：

| 步骤 | 问自己 | 本题的答案 |
|------|-------|-----------|
| **① 什么时候停** | 剩下的节点还够不够凑成一对？ | `head == null`（一个都没有）或 `head.next == null`（只剩一个，奇数个的尾巴）→ 直接返回 `head` |
| **② 这一层做什么** | 不管后面，眼前这两个怎么换？ | 让第 2 个当新头（`newHead`），把第 1 个挂到后面 |
| **③ 递归结果怎么用** | 子问题返回的东西接在哪？ | 子问题处理的是后面的 `3→4`，返回 `4→3`，把它接在第 1 个节点的 `next` 上 |

把这三步画成指针图更直观（A 是当前这一对，B 是丢给递归的部分）：

```
当前层：head=A1 → A2 → B1 → B2 → ...

  newHead = A2                 ← 先把第2个攥在手里
  A1.next = swapPairs(B1)      ← 后面的活交给递归，结果接在A1屁股上
  A2.next = A1                 ← 第2个反过来指第1个
  返回 A2

结果：A2 → A1 → (B 部分已两两交换好)
```

**为什么代码三行必须按这个顺序？**

1. `newHead = head.next` 必须最先：一旦执行了 `head.next = 递归结果`，第 2 个节点就找不到了。
2. `head.next = swapPairs(newHead.next)` 要在 `newHead.next = head` 之前：因为 `newHead.next` 马上会被改成指向 A1，这时候递归的入口就丢了。
3. `newHead.next = head` 放最后：把这一对的最后一根线接上，然后返回 `newHead` 给上一层用。

> 这三问也是链表递归的通用套路，只是每题答案不同，参见 [递归相关的题目](Tip4-递归相关的题目.md) 里的「递归解题 Checklist」。

### 代码

```java
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) return head;

    ListNode newHead = head.next;       // 第2个变新头
    head.next = swapPairs(newHead.next); // 第1个接上递归结果
    newHead.next = head;                // 第2个指向第1个
    return newHead;
}
```

### 复杂度

| 类型 | 复杂度 |
|------|--------|
| 时间 | O(n) |
| 空间 | O(n) 递归栈 |

---

## 两种方法对比

| | 迭代 | 递归 |
|--|------|------|
| 核心操作 | curr 每次指向交换后的第二个节点 | 第2个变新头，第1个接递归结果 |
| 空间 | O(1) | O(n) |
| 代码量 | 稍多（3步指针操作） | 3行，更简洁 |

---

## 关键图解

```
交换前：curr → A → B → C → ...
交换后：curr → B → A → C → ...
              ↑newHead  ↑接递归

三步操作：
  ① curr.next = B       （curr 指向 B）
  ② A.next = C          （A 接上后面的）
  ③ B.next = A          （B 指向 A）
```

---

## 易错点

1. **迭代法 curr 的移动**：交换后 `curr = node1`（不是 node2），因为 node1 现在是这对的第二个，正好是下一对的前一个
2. **递归法三行顺序**：先存 `newHead`，再 `head.next = 递归结果`，最后 `newHead.next = head`。顺序不能乱
3. **奇数个节点**：最后一个节点不交换，`head.next == null` 时直接返回 head

---

## 记忆口诀

```
两两交换三步走：curr指B，A接后，B指A。
递归更简洁：二变头，一接递归，二指一。
```
