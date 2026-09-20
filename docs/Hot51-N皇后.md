# LeetCode 51. N 皇后

对应源码：[Hot51_solveNQueens.java](../src/s10_backTracking/Hot51_solveNQueens.java)

## 题目描述

将 `n` 个皇后放置在 `n × n` 的棋盘上，使皇后彼此之间**不能相互攻击**（任意两个皇后不能处于同一行、同一列、同一斜线上），返回所有不同的解决方案。

每个方案用棋盘表示，`'Q'` 是皇后，`'.'` 是空位。

例：`n = 4` → 共 2 个解

```
解 1            解 2
.Q..            ..Q.
...Q            Q...
Q...            ...Q
..Q.            .Q..
```

## 💡 大白话解析（逐行放置 + 回溯）

因为**每行必须恰好放一个皇后**（n 个皇后 n 行，鸽笼原理），所以不用纠结"哪个格子"，直接**一行一行往下放，每行只决定放在哪一列**——这样天然保证了"不同行"，只剩列和斜线要检查。

回溯三步走：

1. **做选择**：在当前行的某一列放下皇后（前提是这个位置合法）；
2. **往下钻**：递归去放下一行（`row + 1`）；
3. **撤销选择**：下一行放不下去（或已经收割完），把当前皇后擦掉，换本行的下一列再试。

当 `row == n` 时说明 n 行全部放好，收获一份完整棋盘。

**合法性检查为什么只看上方？** 因为是从第 0 行逐行往下放的，当前行以下还都是空的，冲突只可能来自已经放好的上方行。所以 `isValid` 只需向上检查三个方向：正上方同列、左上斜线、右上斜线。

```
以 n=4、决策树为例（每层 = 一行，分支 = 该行选哪一列）：

row0:  试 col0 → 合法，放 Q
row1:  试 col0 ✗(同列) col1 ✗(斜线) col2 ✗(斜线) col3 → 合法，放 Q
row2:  col0..col3 全冲突 → 无路可走，回溯
row1:  擦掉 col3，本行已无列可试 → 回溯
row0:  擦掉 col0，试 col1 → 合法 …… 最终得到 .Q.. / ...Q / Q... / ..Q.
```

## 💻 Java 代码实现

```java
public List<List<String>> solveNQueens(int n) {
    List<List<String>> res = new ArrayList<>();
    // 棋盘：'.' 表示空格，'Q' 表示皇后
    char[][] grid = new char[n][n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            grid[i][j] = '.';
        }
    }
    backTrack(res, grid, 0, n);  // 从第 0 行开始逐行放置
    return res;
}

public void backTrack(List<List<String>> res, char[][] grid, int row, int n) {
    // 递归出口：n 行都成功放下皇后，收割一份完整棋盘
    if (row == n) {
        res.add(construct(grid));
        return;
    }
    // 在当前 row 行里，依次尝试把皇后放到每一列
    for (int col = 0; col < n; col++) {
        if (isValid(grid, row, col, n)) {
            grid[row][col] = 'Q';              // 做选择：放下皇后
            backTrack(res, grid, row + 1, n);  // 递归处理下一行
            grid[row][col] = '.';              // 撤销选择：回溯，换下一列再试
        }
    }
}

public boolean isValid(char[][] grid, int row, int col, int n) {
    // 逐行放置，皇后只可能出现在当前行上方，故只需向上检查三个方向
    for (int i = row - 1; i >= 0; i--) {                       // 正上方同列
        if (grid[i][col] == 'Q') return false;
    }
    for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {  // 左上斜线
        if (grid[i][j] == 'Q') return false;
    }
    for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {   // 右上斜线
        if (grid[i][j] == 'Q') return false;
    }
    return true;
}

public List<String> construct(char[][] grid) {
    // 按行拼成字符串；new String(grid[i]) 会拷贝，不受后续回溯影响
    List<String> output = new ArrayList<>();
    for (int i = 0; i < grid.length; i++) {
        output.add(new String(grid[i]));
    }
    return output;
}
```

## 复杂度分析

| 项 | 复杂度 | 说明 |
| :--- | :---: | :--- |
| 时间 | O(n!) | 第一行 n 种选择、第二行不超过 n-1 种……上界约 n!；每次合法性检查 O(n) |
| 空间 | O(n) 递归栈 | 不计输出；棋盘 `grid` 为 O(n²)，递归深度 O(n) |

## ⚠️ 易错点

1. **忘记撤销（回溯）**：递归返回后必须 `grid[row][col] = '.'`，否则皇后残留会污染后续分支，导致漏解。
2. **合法性检查漏掉某个斜线方向**：必须同时查正上方、左上斜、右上斜三个方向；只查同列会放进互相攻击的皇后。
3. **收割时未拷贝棋盘**：`construct` 里用 `new String(grid[i])` 逐行拷贝；若直接把 `grid` 的引用加进结果，回溯擦除后所有解都会变成同一份全 `'.'` 棋盘。
4. **isValid 多查了下方**：逐行放置时下方必为空，向上查即可；查全表不会错但白白浪费一半时间。

## 🆚 逐行放置 vs 逐格放置

| 解法 | 思路 | 优点 | 缺点 |
| :--- | :--- | :--- | :--- |
| 逐行放置（本文） | 一行一个皇后，`isValid` 只查上方 | 天然排除同行冲突，剪枝强、代码短 | 需理解"每行必放一个" |
| 逐格放置 | 每个格子选放/不放，放满 n 个收割 | 模板通用 | 状态空间大、需额外计数，慢 |

进阶优化：用三个布尔数组分别标记「已占列」「已占主对角线 `row-col`」「已占副对角线 `row+col`」，可把 `isValid` 从 O(n) 降到 O(1)。

## 🧠 记忆口诀

> **一行放一个，只查上三向（同列 + 两斜）；放下 → 钻下一行 → 擦掉换列，钻满 n 行就收割（记得拷贝棋盘）。**
