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

<div style="background-color:#fff9c4; padding:10px 14px; border-radius:6px;">
<p>因为矩阵不一定是正方形的。当某一步走完后，边界可能已经交叉，但循环条件还没被检查到。如果不加判断就会<strong>重复遍历</strong>。</p>
<p>举例：一个 <code>3 x 4</code> 的矩阵（3 行 4 列），走完第 1 步（top 行）和第 2 步（right 列）后，<code>top</code> 已经 &gt; <code>bottom</code>，此时第 3 步就不应该再走了。</p>
</div>

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

## 🎯 专治：第 3/4 步守卫到底写 `top<=bottom` 还是 `left<=right`？

这是螺旋矩阵**最容易串位**的地方——明明该写 `top <= bottom`，手却滑成了 `left <= right`。

### 为什么老写错？因为循环体在“骗”你

```java
if (top <= bottom) {                        // ← 守卫用的是 top/bottom
    for (int i = right; i >= left; i--) {   // ← 循环里动的却是 right/left！
        result.add(matrix[bottom][i]);
    }
    bottom--;
}
```

第 3 步的循环变量 `i` 从 `right` 走到 `left`，**满眼都是 left/right**，大脑就顺手把守卫也写成了 `left <= right`。第 4 步正好反过来（循环动 top/bottom，守卫却是 left/right）——所以这两步特别容易互相串。

### 正确规则：看这一步“走”的是什么

| 步骤 | 走的方向 | 固定的是 | 该判断 |
|------|---------|---------|--------|
| 第3步 | 横着走一条**行**（bottom 行）| 行号 = bottom | **`top <= bottom`**（行还得存在）|
| 第4步 | 竖着走一条**列**（left 列）| 列号 = left | **`left <= right`**（列还得存在）|

一句话：**横向遍历行 → 判上下 `top<=bottom`；纵向遍历列 → 判左右 `left<=right`。**

### 最保险的钩子：看这个代码块“最后缩的是哪条边”

守卫和它自己块尾那句边界更新是**成对**的，瞥一眼就对上：

```
第3步 块尾是 bottom--  → 缩的是“上下”这对 → 守卫写 top <= bottom
第4步 块尾是 left++    → 缩的是“左右”这对 → 守卫写 left <= right
```

> **“我这块缩谁，就判谁那一对”**。写 `if` 时先瞥下面几行是 `bottom--` 还是 `left++`，答案直接出来，不用再纠结循环里是 left 还是 right。

---

## 记忆口诀

```
四边界，画圈圈
左右横走上下竖
走完一步缩一边
非方正要加判断
```
