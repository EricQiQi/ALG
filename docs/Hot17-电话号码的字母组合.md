# LeetCode 17. 电话号码的字母组合

对应源码：[Hot17_letterCombinations.java](../src/s10_backTracking/Hot17_letterCombinations.java)

## 题目描述

给定一个仅包含数字 `2-9` 的字符串，返回所有它能表示的字母组合（按九宫格键盘映射：2→abc，3→def，…，7→pqrs，9→wxyz）。

例：`digits = "23"` → `["ad","ae","af","bd","be","bf","cd","ce","cf"]`。

## 💡 大白话解析

和 Hot46 全排列、Hot78 子集不同，这题**每一层的选择池不一样**：第 `index` 层能选什么，完全由第 `index` 位数字决定（"2" 只能选 a/b/c，"3" 只能选 d/e/f）。

所以回溯模板不变，只是把"候选池"换成了**当前数字对应的字母表**：

1. 收割条件：`index == digits.length()`，每位数字都配好字母了，收一份完整组合；
2. 做选择：把当前数字的某个字母 append 到 `sb`；
3. 递归：处理下一位数字（`index + 1`）；
4. 撤销：`deleteCharAt` 删掉刚 append 的字母，换下一个字母试。

```
digits = "23"：逐位配字母，每位独立选（笛卡尔积）

            ""            ← index=0，数字 2 → 选 a/b/c
        ┌────┼────┐
        a    b    c       ← append 后进入 index=1，数字 3 → 选 d/e/f
       /|\   …   /|\
     ad ae af    cd ce cf ← index==2 钻满收割，然后 deleteCharAt 回溯

结果数 = 3 × 3 = 9 = 各数位字母数的乘积
```

## 💻 Java 代码实现

```java
private final String[] Mapping = {
        "", "",
        "abc", "def", "ghi", "jkl",
        "mno", "pqrs", "tuv", "wxyz"
};

public List<String> letterCombinations(String digits) {
    List<String> res = new ArrayList<>();
    backTrack(res, digits, 0, new StringBuilder());
    return res;
}

public void backTrack(List<String> res, String digits, int index, StringBuilder sb) {
    // 收割：每位数字都配好字母了（toString 是新字符串，无需拷贝）
    if (index == digits.length()) {
        res.add(sb.toString());
        return;
    }

    // 当前数字对应的字母表（'2'-'0'=2 → "abc"）
    char ch = digits.charAt(index);
    String letter = Mapping[ch - '0'];

    // 逐个字母尝试
    for (int i = 0; i < letter.length(); i++) {
        sb.append(letter.charAt(i));        // 做选择
        backTrack(res, digits, index + 1, sb); // 递归处理下一位数字
        sb.deleteCharAt(sb.length() - 1);   // 撤销选择
    }
}
```

## 复杂度分析

| 项 | 复杂度 | 说明 |
| :--- | :---: | :--- |
| 时间 | O(3^m × 4^n) | m = 映射到 3 个字母的数字个数，n = 映射到 4 个字母的（7、9）；每个组合构造 O(1) 摊到叶子 |
| 空间 | O(m+n) | 递归栈深度 = 数字位数（不计输出） |

## ⚠️ 易错点

1. **空字符串边界（本代码有此问题）**：`digits = ""` 时，一进 `backTrack` 就满足 `index == digits.length()`，会把 `""` 收进结果返回 `[""]`；**LeetCode 要求返回空列表 `[]`**。修法：入口处先判 `if (digits.isEmpty()) return res;`。
2. **收割后忘了 `return`**：本代码写了 return 是对的；漏写虽不产生错误结果（后面循环的 letter 是 `Mapping[0]=""`，空循环），但语义不清晰。
3. **`sb.toString()` 不需要再拷贝**：String 不可变，`res.add(sb.toString())` 天然是快照；和 Hot46/78 里 List 必须 `new ArrayList<>(output)` 不同。
4. **撤销用 `deleteCharAt(sb.length()-1)`**：不能删错位置；StringBuilder 复用同一条路径，append/delete 必须成对。
5. **数字含 0/1 时 `Mapping` 对应空串**：题目保证只有 2-9，但 Mapping 里给 0、1 留空串占位，下标 `ch-'0'` 才不用偏移。

## 🆚 回溯三兄弟对比（Hot46 / Hot78 / Hot17）

| | 全排列 46 | 子集 78 | 字母组合 17 |
| :--- | :--- | :--- | :--- |
| 每层选择池 | 候选池 `[first, len)`（swap 固定） | `[start, n)` 往后挑 | 当前数字对应的字母表（逐层不同） |
| 收割时机 | 钻满才收（`first == len`） | 每个节点都收 | 钻满才收（`index == len`） |
| 去重手段 | swap 原地交换 | start 不回头 | 天然无需去重（逐位笛卡尔积） |
| 答案数量 | n! | 2^n | 各数位字母数之积 |
| 撤销方式 | 换回去 swap | remove 末尾 | deleteCharAt 末尾 |

## 🧠 记忆口诀

> **逐位配字母，append → 下一位 → deleteCharAt；钻满收割，空串先挡。**
