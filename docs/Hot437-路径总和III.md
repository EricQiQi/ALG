# 路径总和 III

## 题目描述

二叉树每个节点存放一个整数值，找出路径和等于 `targetSum` 的路径总数。
- 路径不需要从根节点开始，也不需要在叶子节点结束
- 方向必须向下（父 → 子）

```
         10
        /  \
       5   -3
      / \    \
     3   2   11
    / \   \
   3  -2   1

targetSum = 8，满足条件的路径：
  5 → 3       = 8
  5 → 2 → 1   = 8
 -3 → 11      = 8
共 3 条
```

---

## 方法1：双重 DFS（从每个节点出发）

### 思路

两层递归：
- **外层 `pathSum`**：遍历每个节点，把它当作路径起点
- **内层 `rootSum`**：从该节点出发，向下 DFS 统计有多少条路径和 == targetSum

```
以节点 5 为起点：
  5          → 5 ≠ 8
  5→3        → 8 ✓
  5→3→3      → 11
  5→3→-2     → 6
  5→2        → 7
  5→2→1      → 8 ✓
```

### 代码

```java
public int pathSum(TreeNode root, long targetSum) {
    if (root == null) return 0;
    int ret = rootSum(root, targetSum);           // 从当前节点出发的路径数
    ret += pathSum(root.left, targetSum);         // 左子树每个节点都当一次起点
    ret += pathSum(root.right, targetSum);        // 右子树每个节点都当一次起点
    return ret;
}

public int rootSum(TreeNode root, long targetSum) {
    if (root == null) return 0;
    int ret = 0;
    if (root.val == targetSum) ret++;              // 当前节点刚好等于剩余目标
    ret += rootSum(root.left, targetSum - root.val);
    ret += rootSum(root.right, targetSum - root.val);
    return ret;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n²) | 每个节点都做一次 DFS |
| 空间 | O(n) | 递归栈深度 |

---

## 方法2：前缀和 + 回溯

### 思路

类比数组的"和为 K 的子数组"：用前缀和 + HashMap 一次遍历解决。

- 从根到当前节点的路径前缀和为 `prefixSum`
- 如果存在某个祖先节点，其前缀和 == `prefixSum - targetSum`，说明两者之间的路径和恰好是 `targetSum`
- 用 HashMap 记录每条路径上前缀和出现的次数
- **回溯**：离开一个节点时，把它的前缀和计数 -1（因为不能影响其他分支）

```
路径 10 → 5 → 3：
  prefixSum 依次为：10, 15, 18
  在节点 3 处：prefixSum=18, 查 prefixSum-8=10 → map 中有 10（出现 1 次）
  → 说明存在一条路径和为 8（从节点 5 到节点 3）
```

### 代码

```java
public int pathSum(TreeNode root, long targetSum) {
    Map<Long, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0L, 1);   // 空前缀和为 0，出现 1 次
    return dfs(root, 0L, targetSum, prefixCount);
}

public int dfs(TreeNode root, long prefixSum, long targetSum, Map<Long, Integer> prefixCount) {
    if (root == null) return 0;

    prefixSum += root.val;
    int ret = prefixCount.getOrDefault(prefixSum - targetSum, 0);  // 查账
    prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);  // 记账

    ret += dfs(root.left, prefixSum, targetSum, prefixCount);
    ret += dfs(root.right, prefixSum, targetSum, prefixCount);

    prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) - 1);  // 回溯
    return ret;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个节点访问一次，HashMap 操作 O(1) |
| 空间 | O(n) | HashMap + 递归栈 |

---

## 两种方法对比

| | 方法1（双重 DFS） | 方法2（前缀和 + 回溯） |
|--|------------------|---------------------|
| 核心思想 | 每个节点当起点，暴力搜索 | 前缀和查账，一次遍历 |
| 时间 | O(n²) | O(n) |
| 类比 | 无 | 和为 K 的子数组（Hot10） |
| 关键点 | 简单直观 | 必须先查账再记账 + 回溯 |

---

## 易错点

1. **targetSum 要用 long**：节点值范围 [-10⁶, 10⁶]，累加过程中 int 可能溢出
2. **前缀和必须先查账再记账**：和 Hot10 "和为 K 的子数组" 同一个陷阱，顺序反了会把空路径也算进去
3. **回溯不能忘**：树有分支，离开节点时必须把前缀和计数 -1，否则会污染其他分支的统计

---

## 记忆口诀

```
路径总和 III 两招：
暴力：每个节点当起点，往下 DFS 数路径；
优化：前缀和查账，和数组"和为K的子数组"一个套路，记得回溯。
```
