# LeetCode 39. 组合总和

对应源码：[Hot39_combinationSum.java](../src/s10_backTracking/Hot39_combinationSum.java)

## 题目描述

给定一个**无重复元素**的正整数数组 `candidates` 和目标整数 `target`，找出所有和为 `target` 的组合。**同一个数字可以无限次重复使用**。

例：`candidates = {2,3,6,7}`，`target = 7` → `[[2,2,3], [7]]`。

## 💡 大白话解析（start 索引法 + 减法凑数）

把它想成**凑钱**：手里有一把面额（可无限重复用），要凑出 target 元，问有几种凑法。

回溯三步和 Hot78 子集一模一样，只有两处不同：

1. **收割条件**：不再是"每个节点都收"，而是 `target == 0`（正好凑齐）；`target < 0`（凑超了）直接剪枝返回。
2. **递归传 `i` 而不是 `i+1`**：因为同一个数可以重复选，下一层还能从当前这个数开始挑。

**为什么传 `i` 不会重复？** `start` 保证了只能"往后或原地"选，不能回头。所以 `{2,2,3}` 只会以"2→2→3"这一种下标不递减的顺序被构造出来，`[3,2,2]`、`[2,3,2]` 根本不会出现——组合无序，只留一种代表。

```
candidates = {2,3,6,7}, target = 7（节点标注剩余 target）：

  7
  ├─ 选2 → 5 ─┬─ 选2 → 3 ─┬─ 选2 → 1 → 全部剪枝(1<2)
  │           │           └─ 选3 → 0 ✓ 收获 [2,2,3]
  │           ├─ 选3 → 2 → 选2 → 0 ✓ 收获 [2,3,2]？—— 不会出现！
  │           │        （下一层 start=1，只能选 3/6/7，2 在下标 0 已被跳过）
  │           └─ 选6/7 → 负数，剪枝
  ├─ 选3 → 4 ─┬─ 选3 → 1 剪枝  ├─ 选6 剪枝  └─ 选7 剪枝
  ├─ 选6 → 1 → 剪枝
  └─ 选7 → 0 ✓ 收获 [7]

答案：[[2,2,3], [7]]
```

## 💻 Java 代码实现

```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    res = new ArrayList<>();
    output = new ArrayList<>();   // 每次调用重建，避免结果累积
    backTrack(candidates, target, 0);
    return res;
}

public void backTrack(int[] candidates, int target, int start) {
    if (target == 0) {                       // 正好凑齐，收割
        res.add(new ArrayList<>(output));    // 必须拷贝
        return;
    } else if (target < 0) {                 // 凑超了，剪枝
        return;
    }

    for (int i = start; i < candidates.length; i++) {
        output.add(candidates[i]);                       // 做选择
        backTrack(candidates, target - candidates[i], i); // 传 i：允许重复选同一个数
        output.remove(output.size() - 1);                // 撤销选择
    }
}
```

## 复杂度分析

| 项 | 复杂度 | 说明 |
| :--- | :---: | :--- |
| 时间 | O(S) | S 为所有可行解的节点总数，上界约 O(n^(target/min))，最坏情况递归树很深 |
| 空间 | O(target/min) | 递归栈深度 = 组合最长长度（不计输出） |

## ⚠️ 易错点

1. **递归传 `i+1`**：变成"每个数只能用一次"，那是 Hot40 组合总和 II 的题意，本题会漏掉 `[2,2,3]`。
2. **递归传 `0`（或干脆不传 start）**：允许回头选，`[2,2,3]`、`[2,3,2]`、`[3,2,2]` 会全部出现，组合重复爆炸。
3. **收割时忘记拷贝**：`res.add(output)` 会让所有结果指向同一 list，最后全变成空集。
4. **忘记 `target < 0` 剪枝**：不加也能出正确结果（凑不齐永远到不了 0），但会多跑大量无效分支；剪枝是性能关键。
5. **可选优化（本代码未做）**：先 `Arrays.sort(candidates)`，循环里加 `if (candidates[i] > target) break;`——排序后一旦当前数超过剩余 target，后面的更大，直接整层砍掉。

## 🆚 回溯四兄弟对比（Hot46 / Hot78 / Hot17 / Hot39）

| | 全排列 46 | 子集 78 | 字母组合 17 | 组合总和 39 |
| :--- | :--- | :--- | :--- | :--- |
| 每层选择池 | 候选池 `[first, len)` | `[start, n)` 往后挑 | 当前数字的字母表 | `[start, n)` 往后或原地挑 |
| 递归下标 | `first+1`（位置递进） | `i+1`（不回头） | `index+1`（下一位数字） | **`i`（可重复选）** |
| 收割时机 | 钻满才收 | 每个节点都收 | 钻满才收 | `target == 0` 才收 |
| 剪枝 | 无 | 无 | 无 | `target < 0` 返回 |
| 答案数量 | n! | 2^n | 各位字母数之积 | 取决于 target 与 candidates |

## 🧠 记忆口诀

> **减法凑钱：减到 0 收货，减成负剪枝；传 `i` 能重复，传 `i+1` 只能用一次。**
