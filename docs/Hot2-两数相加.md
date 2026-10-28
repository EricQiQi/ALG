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

### 写法一：三段循环（结构最直白）

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode();  // 哑节点，简化头部处理
    ListNode curr = dummy;
    int pre = 0;                        // 进位

    // 1. 两链都非空：一起走
    while (l1 != null && l2 != null) {
        int sum = l1.val + l2.val + pre;
        pre = sum / 10;
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
        l1 = l1.next;
        l2 = l2.next;
    }
    // 2. l1 有剩余
    while (l1 != null) {
        int sum = l1.val + pre;
        pre = sum / 10;
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
        l1 = l1.next;
    }
    // 3. l2 有剩余
    while (l2 != null) {
        int sum = l2.val + pre;
        pre = sum / 10;
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
        l2 = l2.next;
    }
    // 4. 最高位还有进位，补一个节点
    if (pre > 0) {
        curr.next = new ListNode(pre % 10);
    }
    return dummy.next;
}
```

### 写法二：合并成一段循环（更短）

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode();
    ListNode curr = dummy;
    int carry = 0;

    // 三个条件任意一个成立就继续：l1 没走完 / l2 没走完 / 还有进位没写出去
    while (l1 != null || l2 != null || carry != 0) {
        int x = l1 != null ? l1.val : 0;   // 短链走完就当作 0
        int y = l2 != null ? l2.val : 0;

        int sum = x + y + carry;
        carry = sum / 10;
        curr.next = new ListNode(sum % 10);
        curr = curr.next;

        if (l1 != null) l1 = l1.next;      // 必须先判空，否则 NPE
        if (l2 != null) l2 = l2.next;
    }

    return dummy.next;
}
```

合并后只用记住三个点：

1. **`carry != 0` 塞进循环条件**——最高位的进位在循环里就写出去了，末尾的 `if (pre > 0)` 可以整个删掉。
2. **`x` / `y` 用三元表达式补零**——「两链都非空」「l1 剩余」「l2 剩余」三种情况被压成同一段代码。
3. **推进指针前必须判空**——写成 `l1 = l1.next` 而不加 `if (l1 != null)`，短链走完那一步直接空指针，这是合并写法最容易踩的坑。

> 两种写法的取舍：写法二行数少一半，但靠三元补零和 `||` 短路来「兜」边界，读的人要多想一层；写法一把三种情况摊开，看一遍就知道在干什么。刷题追求短选写法二，面试讲思路选写法一。

---

## ❗关键易错点

**1. 最后一定要检查进位 `pre > 0`**
如 `5 + 5 = 10`，两链都走完后 carry=1，不补节点就丢了最高位，结果错成 `0` 而非 `0→1`。

**2. 短的走完，长的要继续（且带着 carry）**
两链长度可能不同，剩余部分仍需逐位加进位，不能直接拼上去。

**3. 用哑节点 `dummy` 起手**
避免对"第一个节点"做特殊判断，最后返回 `dummy.next`。

---

## 复杂度分析

* **时间复杂度**：$O(\max(m, n))$。遍历较长链表一遍。
* **空间复杂度**：$O(1)$（不算返回的结果链表）。

---

## 💡 记忆口诀

> **逆序存储正好从个位加，逐位求和记得带进位；两链一长一短分别收尾，最高位有进位别忘补一个。**

> 关联：哑节点技巧贯穿链表题，见 [合并两个有序链表](Hot21-合并两个有序链表.md)、[反转链表](Hot206-反转链表.md)。
