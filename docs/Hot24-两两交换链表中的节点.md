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
  temp

第1轮（temp=dummy）：
  node1=1, node2=2
  temp.next = 2, 1.next = 3, 2.next = 1
  dummy → 2 → 1 → 3 → 4
  temp 移到 1（交换后的第二个，即下一轮的前一个）

第2轮（temp=1）：
  node1=3, node2=4
  1.next = 4, 3.next = null, 4.next = 3
  dummy → 2 → 1 → 4 → 3
```

### 代码

```java
public ListNode swapPairs(ListNode head) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode temp = dummy;

    while (temp.next != null && temp.next.next != null) {
        ListNode node1 = temp.next;       // 第1个
        ListNode node2 = temp.next.next;  // 第2个

        temp.next = node2;    // temp 指向第2个
        node1.next = node2.next;  // 第1个接上后面的
        node2.next = node1;   // 第2个指向第1个

        temp = node1;         // temp 移到下一对的前一个
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
| 核心操作 | temp 每次指向交换后的第二个节点 | 第2个变新头，第1个接递归结果 |
| 空间 | O(1) | O(n) |
| 代码量 | 稍多（3步指针操作） | 3行，更简洁 |

---

## 关键图解

```
交换前：temp → A → B → C → ...
交换后：temp → B → A → C → ...
              ↑newHead  ↑接递归

三步操作：
  ① temp.next = B       （temp 指向 B）
  ② A.next = C          （A 接上后面的）
  ③ B.next = A          （B 指向 A）
```

---

## 易错点

1. **迭代法 temp 的移动**：交换后 `temp = node1`（不是 node2），因为 node1 现在是这对的第二个，正好是下一对的前一个
2. **递归法三行顺序**：先存 `newHead`，再 `head.next = 递归结果`，最后 `newHead.next = head`。顺序不能乱
3. **奇数个节点**：最后一个节点不交换，`head.next == null` 时直接返回 head

---

## 记忆口诀

```
两两交换三步走：temp指B，A接后，B指A。
递归更简洁：二变头，一接递归，二指一。
```
