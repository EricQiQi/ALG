# LeetCode 79. 单词搜索

对应源码：[Hot79_exist.java](../src/s10_backTracking/Hot79_exist.java)

## 题目描述

给定一个 `m × n` 的二维字符网格 `board` 和一个字符串 `word`，判断 `word` 是否存在于网格中。

单词必须按照**字母顺序**，通过**相邻单元格**（上下左右四方向）内的字母构成，且**同一个单元格不能重复使用**。

例：

```
board =            word = "ABCCED"  → true
A B C E            （A→B→C→C→E→D 一条不重复的路径）
S F C S
A D E E
```

## 💡 大白话解析（DFS + 回溯）

这就是在网格里"走迷宫拼单词"：**从某个格子出发，按 word 的字母一个个往四个方向找，走过的格子做记号不许再踩，走投无路就退回来擦掉记号换条路。**

三个关键动作，正好是回溯的模板：

1. **做选择**：当前格字符匹配上了 `word[start]`，就把它标记为已访问 `visited = true`；
2. **往下钻**：向上、下、左、右四个方向递归去匹配下一个字符 `start + 1`；
3. **撤销选择**：四个方向都没拼出完整单词，说明这条路走错了，把标记擦掉 `visited = false`，退回上一格让它去试别的方向。

**为什么需要 visited**：题目要求同一格不能重复用。如果不标记，路径可能在一格里绕圈反复踩，把本来该失败的单词判成成功。

**外层为什么要遍历每个格子**：单词的起点不确定，任何一个格子都可能是第一个字母，所以以所有格子为起点各搜一次，只要有一次成功就返回 true。

```
以 board 找 "ABCCED"、起点 (0,0)='A' 为例：

(0,0)A ─右→ (0,1)B ─右→ (0,2)C ─下→ (1,2)C ─下→ (2,2)E ─左→ (2,1)D
  start0      start1       start2       start3       start4      start5 → 拼完，true

每一步走不通时（越界/字符不符/已访问）就回头擦标记换方向
```

## 💻 Java 代码实现

```java
public boolean exist(char[][] board, String word) {
    // 先判空再解引用，避免 board 为 null 时抛 NPE
    if (board == null || board.length == 0) return false;

    int rows = board.length;
    int cols = board[0].length;
    // 剪枝：路径不能重复走格子，单词最长只能有 rows*cols 个字符
    if (word.length() > rows * cols) return false;

    boolean[][] visited = new boolean[rows][cols];
    // 枚举每个格子作为单词起点，任一起点搜到就成功
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (dfs(board, word, visited, i, j, 0)) {
                return true;
            }
        }
    }
    return false;
}

public boolean dfs(char[][] board, String word, boolean[][] visited, int row, int col, int start) {
    // 所有字符都匹配完了，成功
    if (start == word.length()) return true;
    // 越界 / 已在当前路径上 / 当前格字符对不上，任一成立即失败
    if (row < 0 || row >= board.length || col < 0 || col >= board[0].length
            || visited[row][col] || board[row][col] != word.charAt(start)) {
        return false;
    }

    visited[row][col] = true;  // 做选择：标记当前格已走

    // 向四个方向递归匹配下一个字符，|| 短路：任一方向成功即整体成功
    boolean found = dfs(board, word, visited, row - 1, col, start + 1) ||
                    dfs(board, word, visited, row + 1, col, start + 1) ||
                    dfs(board, word, visited, row, col - 1, start + 1) ||
                    dfs(board, word, visited, row, col + 1, start + 1);

    visited[row][col] = false; // 撤销选择：回溯，让其它路径能重新使用该格
    return found;
}
```

## 复杂度分析

| 项 | 复杂度 | 说明 |
| :--- | :---: | :--- |
| 时间 | O(m·n·3^L) | m·n 个起点，每次 DFS 最长 L 层（L=word 长度）；除来路外每格最多向 3 个新方向扩展，故约 3^L |
| 空间 | O(m·n) | visited 数组 O(m·n) + 递归栈最深 O(L) |

## ⚠️ 易错点

1. **忘记撤销 visited（不回溯）**：一条路走到黑后没把标记擦掉，会污染其它起点/其它分支的搜索，导致本该 true 的判成 false。回溯的精髓就是"做选择 → 递归 → 撤销选择"三步齐全。
2. **剪枝条件方向写反**：应该是 `word.length() > rows*cols` 才剪掉（单词比格子总数还长，不可能不重复地走出来）。若误写成 `<`，几乎所有正常用例都会被直接返回 false。
3. **先解引用后判空**：`board.length` 写在 `board == null` 判断之前，board 为 null 时会先抛 NPE，判空形同虚设。判空必须在解引用之前。
4. **终止时机**：`start == word.length()` 表示前 L 个字符全部匹配完成，此时才返回 true；不要在匹配到最后一个字符前就提前返回。

## 🧠 记忆口诀

> **每格当起点，逐字母四方向钻；踩一格标一格，走不通就擦掉退回——标记 / 递归 / 撤销，一个都不能少。**
