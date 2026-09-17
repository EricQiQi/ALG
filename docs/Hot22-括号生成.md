# LeetCode 22. 括号生成

对应源码：
- 方法一（分支写法）：[Hot22_generateParenthesis_1.java](../src/s10_backTracking/Hot22_generateParenthesis_1.java)
- 方法二（候选数组 + continue 剪枝）：[Hot22_generateParenthesis_2.java](../src/s10_backTracking/Hot22_generateParenthesis_2.java)

## 题目描述

数字 `n` 代表生成括号的对数，请生成所有可能的**有效的**括号组合。

例：`n = 3` → `["((()))","(()())","(())()","()(())","()()()"]`，共 5 个（卡特兰数 C₃ = 5）。

## 💡 大白话解析

每个位置只有两种选择：放 `(` 或放 `)`。暴力生成 2^(2n) 个串再筛合法的太浪费——**关键在于边生成边剪枝，让非法的分支根本不进去**。

两条规则就够了（`left`/`right` 记录已放的左/右括号数）：

| 想放什么 | 能放的条件 | 为什么 |
| :--- | :--- | :--- |
| `(` | `left < n` | 左括号总共只有 n 个，用完就不能再放 |
| `)` | `left > right` | 必须有一个"已放但还没闭合"的左括号等着它配对，否则会生成 `)(` 这种非法串 |

**收割时机**：串长到 `2 * n` 时必然左右括号各 n 个且全程合法（因为每一步都被规则拦着），直接收。

```
n = 2 的决策树（节点标注 left,right，边上标注放什么）：

              ""(0,0)
                 │ 只能放 (  （right 不能 ≥ left）
              "("(1,0)
            ┌────┴────┐
          放 (       放 )
       "(("(2,0)   "()"(1,1)
          │ 只能放 )  │ 只能放 (  （left 已用完）
       "(()"(2,1)  "()("(2,1)
          │ 只能放 )  │ 只能放 )
      "(())" ✓      "()()" ✓

长度到 2n=4 就收割；非法分支（如 ")" 开头、"())"）根本进不去
```

一句话：**`(` 看余量，`)` 看欠账（左括号欠它一个配对），两个条件天然把非法分支挡在门外，不需要事后校验。**

## 💻 方法一：分支写法（if-if 两个独立分支）

```java
public void backTrack(List<String> res, StringBuilder sb, int n, int left, int right) {
    if (sb.length() == 2 * n) {        // 长度够了，收割
        res.add(sb.toString());
        return;
    }

    if (left < n) {                    // 分支一：能放左括号
        sb.append('(');
        backTrack(res, sb, n, left + 1, right);
        sb.deleteCharAt(sb.length() - 1);  // 撤销
    }

    if (left > right) {                // 分支二：能放右括号
        sb.append(')');
        backTrack(res, sb, n, left, right + 1);
        sb.deleteCharAt(sb.length() - 1);  // 撤销
    }
}
```

特点：**没有 for 循环**，两个 if 就是两个分支。选择只有两种时，直接摊开写最直观。

## 💻 方法二：候选数组 + continue 剪枝（通用回溯模板）

```java
public void backTrack(List<String> res, StringBuilder sb, int n, int left, int right) {
    if (sb.length() == 2 * n) {
        res.add(sb.toString());
        return;
    }

    char[] arr = {'(', ')'};           // 候选池：本层能选的字符
    for (int i = 0; i < arr.length; i++) {
        char ch = arr[i];
        if (ch == '(' && left >= n) continue;      // 剪枝：左括号用完了
        if (ch == ')' && right >= left) continue;  // 剪枝：没有未闭合的左括号

        int nextLeft = ch == '(' ? left + 1 : left;
        int nextRight = ch == ')' ? right + 1 : right;

        sb.append(ch);                            // 做选择
        backTrack(res, sb, n, nextLeft, nextRight);
        sb.deleteCharAt(sb.length() - 1);         // 撤销选择
    }
}
```

特点：把"选择"抽象成**候选数组 + 循环 + continue 过滤**，和 Hot78/Hot39 的 `for (i = start; ...)` 模板结构完全一致——**候选池只有 2 个元素、且每层候选池相同**。适合往候选更多、剪枝条件更复杂的题上套。

## 🆚 两种写法对比

| | 方法一：分支写法 | 方法二：候选数组 + continue |
| :--- | :--- | :--- |
| 结构 | 两个 if 平铺 | for 循环 + 剪枝 continue |
| 可读性 | 选择少时最直观 | 结构统一，易扩展到多候选 |
| 状态更新 | 递归实参直接写 `left+1` | 先算 `nextLeft/nextRight` 再传 |
| 剪枝表达 | 正向条件（能放才进） | 反向条件（不能放就 continue） |
| 本质 | 完全等价，输出顺序也一致 | 完全等价 |

**两种写法条件互为反面，注意别写混**：方法一 `left > right` 才放 `)`，方法二 `right >= left` 就跳过——`left > right` 的否定正是 `right >= left`。

## 复杂度分析

| 项 | 复杂度 | 说明 |
| :--- | :---: | :--- |
| 时间 | O(4^n / √n) | 答案数是卡特兰数 Cₙ ≈ 4^n/(n^1.5·√π)，每个串构造 O(n) |
| 空间 | O(n) | 递归深度 = 串长 2n，即 O(n)（不计输出） |

## ⚠️ 易错点

1. **右括号条件写成 `right < n`**：只判"还没用完"，会生成 `)(`、`())(` 这类非法串。**必须是 `left > right`**（有未闭合的左括号才能闭合）。
2. **`left`/`right` 更新写反**：放 `(` 时是 `left+1`，放 `)` 时是 `right+1`，方法二里 `nextLeft/nextRight` 的三元表达式尤其容易抄错。
3. **忘记 `deleteCharAt` 撤销**：StringBuilder 全程复用，不撤销会让后续分支带上脏字符。
4. **收割后忘记 `return`**：本题因为长度到 `2n` 时 `left == right == n`，两个 if 都不成立，不加也不会出错；但写上 `return` 语义才清晰（也和 Hot17/Hot39 的模板保持一致）。
5. **`sb.toString()` 不需要拷贝**：String 不可变，天然是快照；这点和 Hot46/78 里 List 必须 `new ArrayList<>(output)` 不同。

## 🆚 回溯五兄弟对比（Hot46 / Hot78 / Hot17 / Hot39 / Hot22）

| | 全排列 46 | 子集 78 | 字母组合 17 | 组合总和 39 | 括号生成 22 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 每层选择池 | 候选池 `[first, len)` | `[start, n)` 往后挑 | 当前数字的字母表 | `[start, n)` 往后或原地 | 固定两个：`(` / `)` |
| 循环形式 | for + swap | for + add/remove | for + append | for + add/remove | 两个 if（或 for + continue） |
| 收割时机 | 钻满才收 | 每个节点都收 | 钻满才收 | `target == 0` 才收 | 串长 `2n` 才收 |
| 剪枝 | 无 | 无 | 无 | `target < 0` 返回 | `left >= n` / `right >= left` 不进分支 |
| 撤销方式 | swap 换回 | remove 末尾 | deleteCharAt | remove 末尾 | deleteCharAt |
| 答案数量 | n! | 2^n | 各位字母数之积 | 取决于 target | 卡特兰数 Cₙ |

## 🧠 记忆口诀

> **左看余量（left < n），右看欠账（left > right）；append → 递归 → deleteCharAt，长到 2n 就收割。**
