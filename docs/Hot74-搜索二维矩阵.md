# LeetCode 74. 搜索二维矩阵

对应源码：[Hot74_searchMatrix.java](../src/s11_binarySearch/Hot74_searchMatrix.java)

## 题目描述

给定一个 `m × n` 的整数矩阵 `matrix`，满足两个性质：

- 每行元素**从左到右升序**；
- 每行第一个数**大于上一行最后一个数**。

判断目标值 `target` 是否在矩阵中，要求 `O(log(m·n))` 时间复杂度。

例：

```
matrix =              target = 3  → true
1  3  5  7            target = 13 → false
10 11 16 20
23 30 34 60
```

## 💡 大白话解析

矩阵的两个性质合起来其实说明：**把矩阵按行拉平就是一个严格升序的一维数组**。既然是有序数组找值，就用二分。有两种落地方式：

### 方法一：两次二分（先定行，再定列）

1. 对**第一列**二分，找到「最后一个首元素 ≤ target 的行」——target 若存在，一定落在这一行；
2. 再对这一行做一次普通二分找 target。

定位行的关键：`firstColSearch` 未精确命中时返回 `up - 1`。因为循环退出时 `up` 停在「第一个首元素 > target 的行」，减 1 就是「最后一个首元素 ≤ target 的行」。若 `up - 1 < 0`（target 比所有行的首元素都小），直接返回 false。

### 方法二：一次二分（拉平成一维）

把下标 `idx ∈ [0, m·n-1]` 映射回二维：`matrix[idx / n][idx % n]`，直接在这个虚拟一维数组上二分。代码最短，但**要求矩阵规则**（每行元素个数相同），才能用 `idx / cols`、`idx % cols` 换算。

```
方法二：matrix 3×4，target=3
left=0 right=11
mid=5 → matrix[5/4][5%4]=matrix[1][1]=11 > 3 → right=4
mid=2 → matrix[0][2]=5 > 3 → right=1
mid=0 → matrix[0][0]=1 < 3 → left=1
mid=1 → matrix[0][1]=3 == 3 → true
```

## 💻 Java 代码实现

### 方法一：两次二分

```java
public boolean searchMatrix_1(int[][] matrix, int target) {
    if (matrix == null || matrix[0].length == 0) return false;
    int rows = matrix.length, cols = matrix[0].length;

    int rowIndex = firstColSearch(matrix, target, 0, rows - 1); // 定位行
    if (rowIndex < 0) return false;                             // target 比所有行首都小
    if (matrix[rowIndex][0] == target) return true;             // 命中行首

    return binarySearch(matrix[rowIndex], target, 0, cols - 1); // 行内二分
}

// 在第一列上找「最后一个首元素 ≤ target 的行」，找不到返回 -1
public int firstColSearch(int[][] matrix, int target, int up, int down) {
    while (up <= down) {
        int mid = up + (down - up) / 2;
        if (matrix[mid][0] > target) {
            down = mid - 1;
        } else if (matrix[mid][0] < target) {
            up = mid + 1;
        } else {
            return mid;          // 精确命中行首
        }
    }
    return up - 1;               // up 是第一个 > target 的行，减 1 即目标行
}

// 一维数组标准二分
public boolean binarySearch(int[] nums, int target, int left, int right) {
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] > target) {
            right = mid - 1;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            return true;
        }
    }
    return false;
}
```

### 方法二：拉平成一维一次二分

```java
public boolean searchMatrix_2(int[][] matrix, int target) {
    if (matrix == null || matrix[0].length == 0) return false;
    int rows = matrix.length, cols = matrix[0].length;

    int left = 0, right = rows * cols - 1;   // 虚拟一维数组的两端
    while (left <= right) {
        int mid = left + (right - left) / 2;
        int val = matrix[mid / cols][mid % cols]; // 一维下标 → 二维坐标
        if (val > target) {
            right = mid - 1;
        } else if (val < target) {
            left = mid + 1;
        } else {
            return true;
        }
    }
    return false;
}
```

## 复杂度分析

| 方法 | 时间 | 空间 | 说明 |
| :--- | :---: | :---: | :--- |
| 方法一（两次二分） | O(log m + log n) | O(1) | = O(log(mn))，先定行再定列 |
| 方法二（一维二分） | O(log(m·n)) | O(1) | 一次二分，下标换算 `mid/cols`、`mid%cols` |

两者复杂度同阶，方法二代码更短，方法一在「行内定位」上更直观、可分别复用两个二分函数。

## ⚠️ 易错点

1. **`firstColSearch` 返回值搞错**：未命中时必须返回 `up - 1`（最后一个 ≤ target 的行），返回 `up` 会定位到首元素已经 > target 的下一行，导致漏判。
2. **`up - 1 < 0` 未拦截**：target 比第一行首元素还小时，`firstColSearch` 返回 -1，不判断就会数组越界。
3. **一维下标换算写反**：应是 `matrix[mid / cols][mid % cols]`——除以**列数**得行、对**列数**取余得列；用 rows 换算会错乱。
4. **方法二的前提**：依赖「每行元素个数相同」，不规则矩阵不能用一维下标换算，只能用方法一的思路。
5. **判空只挡了 `matrix[0].length == 0`**：若传入 `matrix.length == 0`（空矩阵），`matrix[0]` 会抛 `ArrayIndexOutOfBoundsException`。本题保证 `m,n ≥ 1` 不影响，但要更健壮可加 `matrix.length == 0` 判断。

## 🆚 本题（74）vs 搜索二维矩阵 II（240）

| | 74 搜索二维矩阵 | 240 搜索二维矩阵 II |
| :--- | :--- | :--- |
| 矩阵性质 | 整体拉平严格升序（行首 > 上行行尾） | 仅**每行**、**每列**各自升序，行间无全局关系 |
| 最优解法 | 二分（O(log mn)） | Z 字形查找，从右上角出发（O(m+n)） |
| 能否二分 | 能，因为全局有序 | 不能整体二分，只能逐行/逐列排除 |

关键区别：74 的两个性质合起来是**全局有序**，240 只有**行列局部有序**，所以 74 用二分、240 用右上角 Z 字形。详见 [搜索二维矩阵 II](Hot240-搜索二维矩阵II.md)。

## 🧠 记忆口诀

> **拉平就是有序数组，二分即可；定行返回 `up-1`，一维下标 `mid/cols` 取行、`mid%cols` 取列。全局有序用二分（74），行列局部有序用 Z 字形（240）。**
