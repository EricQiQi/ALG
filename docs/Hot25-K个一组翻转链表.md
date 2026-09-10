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

**每次处理 k 个节点：先检查够不够 k 个，够就翻转，不够就保持原样。翻转后处理下一组。**

```
k=3, 链表：1 → 2 → 3 → 4 → 5

第1组（够3个）：1→2→3 翻转为 3→2→1
第2组（只剩2个 < 3）：4→5 保持不动

结果：3 → 2 → 1 → 4 → 5
```

三种实现方式：**递归**（先数后翻）、**迭代**（O(1) 空间）、**递归**（边翻边接，最精简）。

---

## 方法一：递归（先数后翻）

### 代码实现

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

## 方法二：迭代

### 核心思路

用 **dummyNode + pre 指针** 串联每一组。每轮循环：先用 tail 探测够不够 k 个，够就局部翻转，翻转后把 pre、head、tail 三个指针推到下一组起点。

### 代码实现

```java
public ListNode reverseKGroup(ListNode head, int k) {
    ListNode dummyNode = new ListNode(0);
    dummyNode.next = head;
    ListNode pre = dummyNode;

    while (head != null) {
        ListNode tail = pre;
        // ① 探测剩余长度是否 ≥ k
        for (int i = 0; i < k; i++) {
            tail = tail.next;
            if (tail == null) return dummyNode.next;  // 不够 k 个，直接返回
        }

        ListNode nex = tail.next;           // ② 暂存下一组头
        ListNode[] reversed = myReverse(head, tail);  // ③ 翻转 [head, tail]
        head = reversed[0];                 //    翻转后 head/tail 互换
        tail = reversed[1];

        pre.next = head;                    // ④ 串联：pre → 新头
        tail.next = nex;                    //         新尾 → 下一组
        pre = tail;                         // ⑤ 指针推进到下一组
        head = tail.next;
    }
    return dummyNode.next;
}

// 翻转 [head, tail] 闭区间，返回 [新头, 新尾]
ListNode[] myReverse(ListNode head, ListNode tail) {
    ListNode prev = tail.next;   // prev 从 tail 后面开始，翻转后正好接上
    ListNode p = head;
    while (prev != tail) {
        ListNode nex = p.next;
        p.next = prev;
        prev = p;
        p = nex;
    }
    return new ListNode[]{tail, head};  // 翻转后头尾互换
}
```

> 关键点：
> - `myReverse` 翻转的是闭区间 `[head, tail]`，`prev` 初始化为 `tail.next` 使得翻转后尾部自然指向下一组
> - 返回 `[tail, head]`，因为翻转后原来的 tail 变成新头，原来的 head 变成新尾
> - `pre` 指针始终指向**上一组的尾部**（初始为 dummyNode），负责串联

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个节点被访问常数次 |
| 空间 | O(1) | 仅用常数个指针，无递归栈 |

### 图解

```
k=2, 链表：1 → 2 → 3 → 4 → 5, dummyNode(0) → 1 → 2 → 3 → 4 → 5

初始：pre = dummy, head = 1

第1轮：
  ① tail 从 pre 走2步 → tail = 2，nex = 3
  ② myReverse(1, 2)：翻转 1→2 为 2→1，返回 [2, 1]
  ③ pre(0).next = 2, tail(1).next = 3
     链表：dummy(0) → 2 → 1 → 3 → 4 → 5
  ④ pre = 1, head = 3

第2轮：
  ① tail 从 pre(1) 走2步 → tail = 4，nex = 5
  ② myReverse(3, 4)：翻转 3→4 为 4→3，返回 [4, 3]
  ③ pre(1).next = 4, tail(3).next = 5
     链表：dummy(0) → 2 → 1 → 4 → 3 → 5
  ④ pre = 3, head = 5

第3轮：
  ① tail 从 pre(3) 走 → tail = 5，再走 → tail = null < k
  ② 不够 k 个，返回 dummyNode.next

结果：2 → 1 → 4 → 3 → 5
```

---

## 方法三：递归（边翻边接）

### 核心思路

方法一需要**先数一遍**够不够 k 个，再调 `reverseList` 翻一遍，两趟扫描。方法三更紧凑：**一趟完成翻转**，翻转过程中用 prev/curr 指针反转，翻转结束后 head 自然变成尾部，直接递归接下一组。

### 代码实现

```java
public ListNode reverseKGroup(ListNode head, int k) {
    ListNode prev = null, curr = head;
    // ① 翻转 k 个节点
    for (int i = 0; i < k; i++) {
        if (curr == null) return head;  // 不足 k 个，返回原头（已翻部分无法恢复）
        ListNode temp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = temp;
    }
    // ② 翻转完成：prev = 新头，head = 新尾，curr = 下一组头
    // head.next 接递归结果
    head.next = reverseKGroup(curr, k);
    return prev;
}
```

> 关键点：
> - 翻转结束后，`prev` 停在第 k 个节点（新头），`head` 是原来的第一个节点（现在是尾部），`curr` 在第 k+1 个节点（下一组头）
> - `head.next = reverseKGroup(curr, k)` 一行完成拼接
> - 与方法一的区别：**不需要单独的 reverseList 函数**，翻转逻辑直接写在递归函数内部
>
> **注意**：此版本在不足 k 个时，已翻转的部分不会恢复。完整解法仍需先数后翻（方法一/二），这里展示的是**翻转逻辑最精简**的写法，帮助理解递归本质。

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个节点访问一次 |
| 空间 | O(n/k) | 递归栈深度 = 组数 |

---

## 三种方法对比

| | 方法一：递归（先数后翻） | 方法二：迭代 | 方法三：递归（边翻边接） |
|--|----------------------|------------|----------------------|
| 空间 | O(n/k) 递归栈 | O(1) 纯指针 | O(n/k) 递归栈 |
| 扫描次数 | 两趟（数 + 翻） | 两趟（探测 + 翻） | 一趟（边翻边接） |
| 不足k个处理 | 正确保留原序 | 正确保留原序 | 已翻部分不恢复 |
| 代码量 | 需 reverseList 辅助 | 需 myReverse + 数组返回 | 最精简，无辅助函数 |
| 适用场景 | 完整正确解法 | O(1) 进阶要求 | 理解递归本质 |

---

## 和 Hot24 的关系

**Hot24（两两交换）就是 k=2 的 Hot25。** 思路完全一样：

| | Hot24 两两交换 | Hot25 K个一组翻转 |
|--|--------------|------------------|
| 每次处理 | 2个节点 | k个节点 |
| 不够时 | 不足2个直接返回 | 不足k个直接返回 |
| 翻转方式 | 交换指针 | 翻转k个节点 |
| 后续处理 | 递归 | 递归/迭代 |

---

## 易错点

1. **先数再翻**：必须先数够 k 个才翻转，不够就原样返回。不能边翻边数
2. **翻转后 head 变成尾部**：`head.next = curr`（curr 是第 k+1 个节点），这步不能漏
3. **复用 temp 指针**：第①步数数时 temp 已停在第 k+1 个节点，直接作为下一组头，无需额外返回
4. **迭代法 myReverse 的 prev 初始值**：`prev = tail.next` 而非 null，这样翻转后尾部自然指向下一组，不用额外拼接
5. **迭代法指针更新顺序**：先 `pre.next = head`，再 `tail.next = nex`，最后推 `pre = tail, head = tail.next`，顺序不能乱

---

## 记忆口诀

```
K个一组翻：先数够不够，够就翻，不够就留。
方法一（先数后翻）：temp 数一遍，reverseList 翻一遍，尾接递归下一组。
方法二（迭代）：dummy 打头阵，pre 串联每组，tail 探路够不够，翻转后推指针。
方法三（边翻边接）：prev/curr 翻 k 个，head 变尾接递归，最精简。
```
