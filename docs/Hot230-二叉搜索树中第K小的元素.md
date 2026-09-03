# 二叉搜索树中第K小的元素（Kth Smallest Element in a BST）

## 题目描述

给定一个二叉搜索树的根节点 `root` 和一个整数 `k`，找树中第 `k` 小的元素（从 1 开始计数）。

---

## 核心思路

**BST 的中序遍历（左→根→右）是严格递增的。**

→ 中序遍历到第 k 个节点，就是第 k 小的元素。

```
       3
      / \
     1   4
      \
       2

中序遍历：1 → 2 → 3 → 4
           ↑1  ↑2  ↑3  ↑4
k=3 → 走到第3个节点 → 答案是 3
```

---

## 代码实现

```java
public int kthSmallest(TreeNode root, int k) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (!stack.isEmpty() || curr != null) {
        while (curr != null) {          // 一路向左，全部压栈
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();             // 弹出 = 访问
        k--;
        if (k == 0) break;             // 第 k 个，提前退出
        curr = curr.right;             // 转向右子树
    }
    return curr.val;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(H + k) | H 是树高，先走到最左叶子 O(H)，再弹 k 次 |
| 空间 | O(H) | 栈深度 = 树高 |

---

## 图解

```
树：           栈（从顶到底）：      k    当前弹出
               ↑顶
       3       3                   3    -
      / \      1 3                 2    1
     1   4     2 1 3               1    2
      \                            0    3 → k==0，break！
       2

返回 3
```

---

## 易错点

1. **k 从 1 开始计数**：每次弹出后 `k--`，`k == 0` 时就是答案（不是 `k == 1`）
2. **提前退出**：找到第 k 个就 `break`，不需要遍历完整棵树
3. **中序遍历迭代模板**：内层 while 一路向左压栈，弹出后转向右子树 — 和 Hot98 方法2 是同一个模板

---

## 记忆口诀

```
BST 中序即排序，数到第 k 个就是答案。
```
