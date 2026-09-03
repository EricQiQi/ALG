# 验证二叉搜索树（Validate Binary Search Tree）

## 题目描述

给你一个二叉树的根节点 `root`，判断其是否是一个有效的二叉搜索树。

BST 的性质：
- 左子树所有节点 **< 根节点**
- 右子树所有节点 **> 根节点**
- 左右子树也必须是 BST

```
       5
      / \
     4   6
        / \
       3   7

  3 是 6 的左孩子 ✓，但 3 < 5（应该在右子树里 > 5）✗ → 不是 BST
```

---

## 方法1：递归（传递合法范围）

### 思路

朴素想法：每个节点和左右孩子比大小。但反例说明**光比孩子不够，还要知道祖先的约束**。

→ 每个节点需要知道自己合法的取值范围 `(min, max)`，往下递归时不断收紧。

```
         5  (-∞, +∞)
        / \
       4    6
      /    / \
 (-∞, 5)  (5,+∞)
             ↓
           3  ∈ (5, +∞)？  3 < 5 → false！
```

**规则：往左走收紧 max，往右走收紧 min。**

### 代码

```java
public boolean isValidBST(TreeNode root) {
    return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

public boolean isValidBST(TreeNode root, long min, long max) {
    if (root == null) return true;
    if (root.val <= min || root.val >= max) return false;  // 自己先过关
    return isValidBST(root.left, min, root.val)            // 往左收紧 max
        && isValidBST(root.right, root.val, max);          // 往右收紧 min
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个节点访问一次 |
| 空间 | O(h) | 递归栈深度 = 树高 |

---

## 方法2：迭代（中序遍历）

### 思路

BST 的中序遍历（左→根→右）一定是**严格递增**的。

→ 用栈模拟中序遍历，每次检查当前节点的值是否 > 前一个节点的值，不是就 false。

```
中序遍历顺序：4 → 5 → 3 → 6 → 7
              4 < 5 ✓，5 > 3 ✗ → 不是 BST
```

### 代码

```java
public boolean isValidBST(TreeNode root) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    long pre = Long.MIN_VALUE;

    TreeNode curr = root;
    while (!stack.isEmpty() || curr != null) {
        while (curr != null) {       // 一路向左，全部压栈
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();          // 弹出 = 访问
        if (curr.val <= pre) return false;  // 不递增 → 不是 BST
        pre = curr.val;
        curr = curr.right;           // 转向右子树
    }
    return true;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个节点访问一次 |
| 空间 | O(h) | 栈深度 = 树高 |

---

## 两种方法对比

| | 递归 | 迭代（中序遍历） |
|--|------|----------------|
| 核心思想 | 每个节点带范围下去检查 | BST 中序遍历严格递增 |
| 思维难度 | 需要想到加 min/max 参数 | 需要知道中序遍历性质 |
| 代码量 | 更少 | 稍多（栈模拟模板） |

---

## 易错点

1. **min/max 要用 long**：节点值可能是 `Integer.MIN_VALUE` 或 `Integer.MAX_VALUE`，用 `int` 会导致边界值判断错误
2. **递归顺序**：先检查自己是否合法，再递归子树（不要先递归再检查）
3. **中序遍历迭代模板**：内层 while 一路向左压栈，弹出后转向右子树 — 这是所有二叉树迭代遍历的通用模板

---

## 记忆口诀

```
验证 BST 有两种招：
递归带范围，往左收紧 max，往右收紧 min；
中序必递增，前一个比当前小，否则不是 BST。
```
