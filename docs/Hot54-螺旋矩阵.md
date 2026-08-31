# 螺旋矩阵（Spiral Matrix）

## 题目描述

给定一个 `m x n` 的矩阵 `matrix`，按**螺旋的顺序**返回矩阵中的所有元素。

示例 1：
```
输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
输出：[1,2,3,6,9,8,7,4,5]

遍历顺序：
1 → 2 → 3
            ↓
4   5   6   ↓
↑           ↓
7 ← 8 ← 9 ←
```

示例 2：
```
输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
输出：[1,2,3,4,8,12,11,10,9,5,6,7]
```

---

## 核心思路

### 关键洞察

**螺旋遍历 = 画圈**，每一圈都是固定的四个方向：

```
    →→→→→→→
    ↑       ↓
    ↑       ↓
    ←←←←←←←
```

只需要维护**四个边界**，每走完一条边就把对应的边界往里缩一格，直到边界相遇。

### 四个边界变量

| 变量 | 含义 | 初始值 |
|------|------|--------|
| `top` | 上边界（行号） | `0` |
| `bottom` | 下边界（行号） | `m - 1` |
| `left` | 左边界（列号） | `0` |
| `right` | 右边界（列号） | `n - 1` |

---

## 算法步骤

每一轮 `while (top <= bottom && left <= right)` 按顺序执行四步：

```
第1步：从左到右遍历 top 行 → 遍历完 top++（上边界下移）
第2步：从上到下遍历 right 列 → 遍历完 right--（右边界左移）
第3步：从右到左遍历 bottom 行 → 遍历完 bottom--（下边界上移）⚠️ 需判断 top <= bottom
第4步：从下到上遍历 left 列 → 遍历完 left++（左边界右移）⚠️ 需判断 left <= right
```

### ⚠️ 为什么第 3、4 步需要额外判断？

因为矩阵不一定是正方形的。当某一步走完后，边界可能已经交叉，但循环条件还没被检查到。如果不加判断就会**重复遍历**。

举例：一个 `3 x 4` 的矩阵（3 行 4 列），走完第 1 步（top 行）和第 2 步（right 列）后，`top` 已经 > `bottom`，此时第 3 步就不应该再走了。

---

## 代码实现

```java
public static List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> result = new ArrayList<>();

    if (matrix == null || matrix.length == 0) {
        return result;
    }

    int top = 0;
    int bottom = matrix.length - 1;
    int left = 0;
    int right = matrix[0].length - 1;

    while (top <= bottom && left <= right) {
        // 1. 从左到右遍历 top 行
        for (int i = left; i <= right; i++) {
            result.add(matrix[top][i]);
        }
        top++;

        // 2. 从上到下遍历 right 列
        for (int i = top; i <= bottom; i++) {
            result.add(matrix[i][right]);
        }
        right--;

        // 3. 从右到左遍历 bottom 行（需要判断）
        if (top <= bottom) {
            for (int i = right; i >= left; i--) {
                result.add(matrix[bottom][i]);
            }
            bottom--;
        }

        // 4. 从下到上遍历 left 列（需要判断）
        if (left <= right) {
            for (int i = bottom; i >= top; i--) {
                result.add(matrix[i][left]);
            }
            left++;
        }
    }

    return result;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(m × n) | 每个元素恰好访问一次 |
| 空间 | O(1) | 只用了四个边界变量（不计结果集） |

---

## 图解执行过程

以 `[[1,2,3],[4,5,6],[7,8,9]]` 为例：

```
初始边界：top=0, bottom=2, left=0, right=2

第1圈：
  → top行:  1, 2, 3        top变为1
  ↓ right列: 6, 9          right变为1
  ← bottom行: 8, 7         bottom变为1
  ↑ left列:  4             left变为1

第2圈：(top=1, bottom=1, left=1, right=1)
  → top行:  5              top变为2
  ↓ right列: (无，top>bottom不执行)
  ← (top>bottom，跳过)
  ↑ (left<=right但bottom<top，跳过)

结束，结果：[1, 2, 3, 6, 9, 8, 7, 4, 5] ✅
```

---

## 易错点

1. **第 3、4 步忘记加边界判断**：非正方形矩阵会在最后一圈重复遍历元素
2. **循环条件写错**：应该用 `top <= bottom && left <= right`，用 `||` 会多跑一圈
3. **边界更新方向搞反**：`top++` 是下移，`bottom--` 是上移，别搞混

---

## 记忆口诀

```
四边界，画圈圈
左右横走上下竖
走完一步缩一边
非方正要加判断
```
