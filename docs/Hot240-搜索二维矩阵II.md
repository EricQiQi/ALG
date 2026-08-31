# 搜索二维矩阵 II（Search a 2D Matrix II）

## 题目描述

编写一个高效的算法来搜索 `m x n` 矩阵 `matrix` 中的一个目标值 `target`。该矩阵具有以下特性：

- 每行的元素**从左到右升序**排列
- 每列的元素**从上到下升序**排列

示例：
```
输入：matrix = [[1, 4, 7, 11, 15],
               [2, 5, 8, 12, 19],
               [3, 6, 9, 16, 22],
               [10,13,14,17, 24],
               [18,21,23,26, 30]]
      target = 5
输出：true
```

---

## 核心思路

矩阵**行有序、列也有序**，这是本题最特殊的条件，三种方法对这条件的利用程度不同：

```
暴力法：完全不用有序性 → O(mn)
二分法：只利用行有序   → O(m log n)
Z 字形：行列有序都用上 → O(m + n)
```

---

## 方法一：暴力法

### 算法步骤

遍历矩阵每个元素，逐一比较。

### 代码实现

```java
public boolean searchMatrix_1(int[][] matrix, int target) {
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[i].length; j++) {
            if (matrix[i][j] == target) {
                return true;
            }
        }
    }
    return false;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(m × n) | 每个元素都要看一眼 |
| 空间 | O(1) | |

---

## 方法二：逐行二分

### 算法步骤

1. 逐行遍历矩阵
2. 每行内部使用**二分查找**（因为每行都是升序的）

### 代码实现

```java
public boolean searchMatrix_2(int[][] matrix, int target) {
    for (int[] row : matrix) {
        if (binarySearch(row, target)) {
            return true;
        }
    }
    return false;
}

private boolean binarySearch(int[] row, int target) {
    int left = 0, right = row.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;  // 防溢出写法
        if (row[mid] == target) {
            return true;
        } else if (row[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return false;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(m log n) | m 行，每行二分 log n |
| 空间 | O(1) | |

---

## 方法三：Z 字形查找（最优解）

### 关键洞察

**右上角的元素是"分界点"**：

```
1   4   7  [15]  ← 15 是第 0 行的最大值
2   5   8   19      15 又是最后一列的最小值
3   6   9   22
10  13  14  24
18  21  23  30
```

站在右上角看 `matrix[row][col]`：
- 它是**所在行的最大值**
- 它是**所在列的最小值**

于是每次比较都能**排除一整行或一整列**：

| 比较结果 | 结论 | 动作 |
|----------|------|------|
| `matrix[row][col] == target` | 找到了 | 返回 true |
| `matrix[row][col] > target` | target 比本行最大值还小，**本行下面不用看**... 不，是**这一列都比 target 大** | `col--`（左移） |
| `matrix[row][col] < target` | target 比本列最小值还大，**这一行都比 target 小** | `row++`（下移） |

### 图解执行过程

在上述矩阵中查找 `target = 5`：

```
起点(0,4)=15：15 > 5 → col--
    ↓
(0,3)=11：11 > 5 → col--
    ↓
(0,2)=7：7 > 5 → col--
    ↓
(0,1)=4：4 < 5 → row++
    ↓
(1,1)=5：命中！返回 true ✅
```

移动轨迹像一个"Z 字"（或阶梯状），故名 Z 字形查找。

### 代码实现

```java
public boolean searchMatrix_3(int[][] matrix, int target) {
    int row = 0, col = matrix[0].length - 1;  // 从右上角出发
    while (row < matrix.length && col >= 0) {
        if (matrix[row][col] == target) {
            return true;
        } else if (matrix[row][col] > target) {
            col--;  // 当前太大，左移
        } else {
            row++;  // 当前太小，下移
        }
    }
    return false;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(m + n) | `row` 最多加 m 次，`col` 最多减 n 次 |
| 空间 | O(1) | |

---

## 三种方法对比

| 维度 | 暴力法 | 逐行二分 | Z 字形查找 |
|------|--------|----------|-----------|
| 时间复杂度 | O(m × n) | O(m log n) | **O(m + n)** |
| 空间复杂度 | O(1) | O(1) | O(1) |
| 利用有序性 | 完全不用 | 只用行有序 | 行列有序都用 |
| 代码难度 | 最简单 | 需要写二分 | 最巧妙、代码最短 |

**为什么 Z 字形是 O(m + n)？**
每次循环指针要么下移要么左移，`row` 的上限是 m、`col` 的下限是 0，所以循环最多执行 m + n 次必然越界退出，一步都不浪费。

**从左下角出发也可以！** 左下角元素是"所在行最小、所在列最大"，逻辑对称：
- 当前值 < target → 右移
- 当前值 > target → 上移

⚠️ 但从**左上角或右下角**出发不行：左上角是"行最小且列最小"、右下角是"行最大且列最大"，比较一次无法排除任何一行或一列。

---

## 易错点

1. **出发点选错**：必须从右上角（或左下角）出发，左上角/右下角无法缩小范围
2. **移动方向搞反**：右上角出发时，大了左移、小了下移，别写反
3. **二分 mid 写成 `(left + right) / 2`**：大数组下可能整型溢出，应写 `left + (right - left) / 2`

---

## 记忆口诀

```
右上角，当哨兵
它最大，又最小
大了往左走，小了往下跑
行列各走一遍，Z 字画到老
```
