# 两两交换链表中的节点（Swap Nodes in Pairs）

## 题目描述

给定一个链表，**两两交换**其中相邻的节点，并返回交换后的链表。

要求：不能修改节点内部的值，只能进行实际的节点交换。

示例：
```
输入：1 → 2 → 3 → 4
输出：2 → 1 → 4 → 3

输入：1 → 2 → 3 → 4 → 5
输出：2 → 1 → 4 → 3 → 5

输入：null
输出：null
```

---

## 核心思路

两两交换 = 每两个节点一组，**把箭头掉个方向**：

```text
交换前：1 → 2 → 3 → 4
交换后：2 → 1 → 4 → 3
```

| 解法 | 思路 | 空间 |
|------|------|------|
| 迭代法 | 用哑节点从头开始，每次取两个节点交换指向 | O(1) |
| 递归法 | 先信任后面已交换好，再补当前两个节点的交换 | O(n) |

---

## 解法一：迭代法

### 原理图解

引入 `dummyNode` 统一处理头节点交换，`temp` 每次指向**待交换对的前一个节点**：

```text
初始：dummy → 1 → 2 → 3 → 4
        temp

第1轮交换（node1=1, node2=2）：
dummy → 2 → 1 → 3 → 4
                 ↑
               temp（移到 node1，即下一对的前一个）

第2轮交换（node1=3, node2=4）：
dummy → 2 → 1 → 4 → 3
                         ↑
                       temp（next 为 null，循环结束）
```

### 关键三步（交换 node1 和 node2）

```text
temp → node1 → node2 → X

① temp.next = node2       // temp 跳过 node1，直接指向 node2
② node1.next = node2.next // node1 接上 node2 后面的部分
③ node2.next = node1      // node2 指向 node1，完成交换

结果：temp → node2 → node1 → X
```

### 代码实现

```java
public ListNode swapPairs_1(ListNode head) {
    ListNode dummyNode = new ListNode(0);
    dummyNode.next = head;

    ListNode temp = dummyNode;
    while (temp.next != null && temp.next.next != null) {
        ListNode node1 = temp.next;
        ListNode node2 = temp.next.next;
        temp.next = node2;          // ① temp 指向 node2
        node1.next = node2.next;    // ② node1 接上后面的部分
        node2.next = node1;         // ③ node2 指向 node1
        temp = node1;               // temp 前进到下一对的前一个位置
    }
    return dummyNode.next;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个节点访问一次 |
| 空间 | O(1) | 只用了几个指针 |

---

## 解法二：递归法

### 原理图解

递归的思路是**从后往前看**：假设后面的已经交换好了，当前只需要交换前两个节点。

```text
递归展开（以 1→2→3→4 为例）：

swapPairs(1→2→3→4)
  newHead = 2
  1.next = swapPairs(3→4)     // 假设后面交换好：3→4 变成 4→3
  → 1.next = 4                // 所以 1 指向 4
  2.next = 1                  // 2 指向 1
  return 2                    // 返回 2→1→4→3 ✅

swapPairs(3→4)
  newHead = 4
  3.next = swapPairs(null)    // null → 返回 null
  → 3.next = null
  4.next = 3
  return 4                    // 返回 4→3
```

### 算法步骤

1. **终止条件**：`head == null || head.next == null`，剩余不足 2 个节点，直接返回
2. **保存新头**：`newHead = head.next`（第 2 个节点）
3. **递归处理后面**：`head.next = swapPairs(newHead.next)`（第 1 个节点接上后面交换好的结果）
4. **完成交换**：`newHead.next = head`（第 2 个节点指向第 1 个节点）
5. **返回新头**：`return newHead`

### 代码实现

```java
public ListNode swapPairs(ListNode head) {
    // ① 递归终止条件
    if (head == null || head.next == null) {
        return head;
    }

    // ② 保存新的头节点（第2个节点）
    ListNode newHead = head.next;

    // ③ 关键！先处理后面的部分，再连接
    head.next = swapPairs(newHead.next);
    // newHead.next 是节点3，swapPairs(节点3) 返回交换后的结果
    // 所以 head.next 指向交换后的第一个节点

    // ④ 完成交换
    newHead.next = head;

    // ⑤ 返回新头
    return newHead;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个节点访问一次 |
| 空间 | O(n) | 递归栈深度 n/2 |

### 递归法的关键一步

```java
head.next = swapPairs(newHead.next);
```

**翻译成人话**：第 1 个节点（head）的 next，指向「从第 3 个节点开始的链表交换后的结果」。

这一步**必须先做**，因为后面 `newHead.next = head` 会改掉 `newHead` 的 next 指针，如果不先处理后面，递归就找不到第 3 个节点了。

---

## 两种解法对比

| 维度 | 迭代法 | 递归法 |
|------|--------|--------|
| 时间 | O(n) | O(n) |
| 空间 | **O(1)** | O(n) |
| 难度 | 需要哑节点，指针操作多 | 代码简洁，但思维更抽象 |
| 适用 | 面试首选 | 练递归思维 |

**为什么递归代码更短？**
迭代法需要手动维护 `temp`、`node1`、`node2` 三个指针的接力，递归法把这些藏进了调用栈——每一层只需要关心「当前两个节点怎么交换」，后面的事交给递归。

---

## 易错点

1. **迭代法循环条件写错**：`temp.next != null && temp.next.next != null` 两个条件缺一不可，否则 `temp.next.next` 会 NPE
2. **交换三步顺序搞反**：必须先 `temp.next = node2`，再 `node1.next = node2.next`，最后 `node2.next = node1`，顺序错了就会断链或成环
3. **递归法没先处理后面**：如果先写 `newHead.next = head` 再递归，`newHead.next` 已经被改了，递归传入的节点就不对了
4. **忘记用哑节点**：头节点也会被交换，没有 `dummyNode` 的话需要单独处理第一对，容易出错

---

## 记忆口诀

```
两两交换两招：
迭代哑节点打头阵，三步交换 temp 前移
递归先信后面好，head 接递归 newHead 指 head
```
