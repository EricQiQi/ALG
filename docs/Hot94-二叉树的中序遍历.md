# 二叉树的中序遍历（Binary Tree Inorder Traversal）

## 题目描述

给定一个二叉树的根节点 `root`，返回它的**中序遍历**（左→根→右）。

```
       1
        \
         2
        /
       3

中序遍历：[1, 3, 2]
```

> 本文同时总结**前序、中序、后序**的递归和迭代写法，因为它们是同一套模板。

---

## 三种遍历顺序

```
前序（根左右）：先访问根，再左，再右
中序（左根右）：先左，再访问根，再右  ← 本题
后序（左右根）：先左，再右，最后访问根

       1
      / \
     2   3
    / \
   4   5

前序：1, 2, 4, 5, 3
中序：4, 2, 5, 1, 3
后序：4, 5, 2, 3, 1
```

---

## 方法1：递归

### 代码

```java
// 前序：根 左 右
public void preOrder(TreeNode root, List<Integer> res) {
    if (root == null) return;
    res.add(root.val);         // 先访问根
    preOrder(root.left, res);
    preOrder(root.right, res);
}

// 中序：左 根 右
public void inOrder(TreeNode root, List<Integer> res) {
    if (root == null) return;
    inOrder(root.left, res);
    res.add(root.val);         // 再访问根
    inOrder(root.right, res);
}

// 后序：左 右 根
public void postOrder(TreeNode root, List<Integer> res) {
    if (root == null) return;
    postOrder(root.left, res);
    postOrder(root.right, res);
    res.add(root.val);         // 最后访问根
}
```

> 三种遍历只差一行 `res.add(root.val)` 的位置。

---

## 方法2：迭代（栈模拟）

### 核心模板

所有迭代遍历共享同一个骨架：

```java
while (!stack.isEmpty() || curr != null) {
    while (curr != null) {       // 一路向左，全部压栈
        stack.push(curr);
        curr = curr.left;
    }
    curr = stack.pop();          // 弹出 = 访问当前节点
    // ★ 在这里 add（前序/中序/后序的区别）
    curr = curr.right;           // 转向右子树
}
```

**区别只在于什么时候 `add`：**

| 遍历 | add 时机 | 说明 |
|------|---------|------|
| 前序 | 压栈时 add | 入栈就记录（第一次遇到就记录） |
| 中序 | 弹出时 add | 左子树处理完才记录 |
| 后序 | 需要额外标记 | 见下方说明 |

### 前序迭代

```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (!stack.isEmpty() || curr != null) {
        while (curr != null) {
            res.add(curr.val);      // ★ 压栈时就 add
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        curr = curr.right;
    }
    return res;
}
```

### 中序迭代

```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (!stack.isEmpty() || curr != null) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        res.add(curr.val);          // ★ 弹出时才 add
        curr = curr.right;
    }
    return res;
}
```

### 后序迭代（根右左 + 反转）

```java
public List<Integer> postorderTraversal(TreeNode root) {
    LinkedList<Integer> res = new LinkedList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (!stack.isEmpty() || curr != null) {
        while (curr != null) {
            res.addFirst(curr.val);  // ★ 头插法，相当于"根右左"的反转
            stack.push(curr);
            curr = curr.right;       // ★ 先走右（和前序相反）
        }
        curr = stack.pop();
        curr = curr.left;            // ★ 再走左
    }
    return res;
}
```

> 后序技巧：前序是"根左右"，把左右颠倒变成"根右左"，再反转就是"左右根" = 后序。
> 实现上：前序往右走改成往左走，`add` 改成 `addFirst`（头插法自动反转）。

---

## 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个节点访问一次 |
| 空间 | O(n) | 栈最多存 n 个节点（退化为链表时） |

---

## 递归 vs 迭代对比

| | 递归 | 迭代 |
|--|------|------|
| 代码量 | 3行，最简洁 | 需要手动管理栈 |
| 思维难度 | 直观 | 需要理解栈模拟过程 |
| 面试建议 | 必须会 | 中序必须会（Hot98、Hot230 都在用这个模板） |

---

## 易错点

1. **中序迭代 add 的位置**：在 `stack.pop()` 之后 add，不是在压栈时 add（压栈时 add 就变成前序了）
2. **后序迭代的两个反转**：① 先走 `right` 再走 `left`（和前序相反）② 用 `addFirst` 头插（自动反转结果）
3. **内层 while 不能漏**：`while (curr != null)` 一路向左压栈是核心，漏了就遍历不全

---

## 记忆口诀

```
递归三种序，add 换位置；
迭代同模板，一路向左压栈底，
弹出转向右，前序压时加，中序弹时加，
后序反过来：先右后左头插法。
```
