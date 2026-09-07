# 算法笔记：最小覆盖子串（力扣 76 题）

## 题目描述
给你一个字符串 `s`、一个字符串 `t`。返回 `s` 中涵盖 `t` 所有字符的**最小子串**。如果不存在，则返回空字符串 `""`。

```
输入: s = "ADOBECODEBANC", t = "ABC"
输出: "BANC"
解释: 最小覆盖子串 "BANC" 包含了 t 中的 A、B、C
```

> 注意：`t` 中的重复字符也要被覆盖同样多次；答案唯一。

---

## 核心思想

> **滑动窗口 + 账单差额：右探子扩张"还账"，凑齐后左探子收缩"找最小"。**

和 [438 找到所有字母异位词](Hot438-找到字符串中所有字母异位词-Hot76.md) 是**同一套账单框架**，区别只在 `remain == 0`（窗口已覆盖 t）时做什么：

| | 438 异位词 | 76 最小覆盖 |
| :--- | :--- | :--- |
| 窗口大小 | **固定** = t.length() | **可变**，越小越好 |
| `remain==0` 时 | 收集左端点下标 | **更新最小长度和起点** |

---

## 图解：扩张 → 凑齐 → 收缩

```
s = A D O B E C O D E B A N C
下标 0 1 2 3 4 5 6 7 8 9 ...

① 右探子扩张，直到窗口 [A D O B E C] 首次集齐 A、B、C → remain=0
② 记录长度 6，然后左探子收缩（踢掉 A 就缺 A 了 → 停下继续扩张）
③ 一路推进，最终最优窗口 [B A N C] 长度 4 ✅

答案 = "BANC"
```

**关键**：`remain` 表示"还差几个字符才能覆盖 t"，降到 0 就说明窗口合格，进入收缩阶段。

---

## 代码实现 (Java)

```java
public static String minWindow(String s, String t) {
    // 1. 建立账单：count[c] > 0 表示还欠 c 这个字符
    int[] count = new int[128];
    for (char ch : t.toCharArray()) count[ch]++;
    int remain = t.length();          // 还差几个字符

    int minLen = Integer.MAX_VALUE;   // 最小窗口长度
    int start = 0;                    // 最小窗口起点

    int left = 0;
    for (int right = 0; right < s.length(); right++) {
        // 2. 右探子进窗，销账
        char rch = s.charAt(right);
        if (count[rch] > 0) remain--; // 这个字符正是缺的，缺额减一
        count[rch]--;                 // 收入窗口（可能变负，说明拿多了）

        // 3. 窗口已覆盖 t → 收缩找最小
        while (remain == 0) {
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                start = left;
            }
            // 4. 左探子出窗，恢复账单
            char lch = s.charAt(left);
            count[lch]++;
            if (count[lch] > 0) remain++; // 踢掉的是必需字符 → 又缺了，退出收缩
            left++;
        }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
}
```

---

## ❗关键易错点

**1. `remain == 0` 时更新最小长度，不是收集下标**
这是与 438 的分水岭。76 求最短，每次窗口合格都要 `if (right-left+1 < minLen)` 记录更优解。

**2. 判断"必需字符"用 `count[c] > 0`，不是 `>= 0`**
- 右探子进窗：`count[rch] > 0` 才 `remain--`（说明这个字符是真正缺的，不是多余的）。
- 左探子出窗：`count[lch]++` 后若 `> 0`，说明踢掉了一个不可或缺的字符，`remain++` 迫使收缩停止。

**3. `count` 数组允许变负数**
负数代表"这个字符窗口里拿多了/不在 t 里"，是正常状态，不要试图阻止它变负。

**4. 无解要返回 `""`**
用 `minLen == Integer.MAX_VALUE` 判断从未找到合格窗口。

---

## 复杂度分析

* **时间复杂度**：$O(n)$。左右探子各自最多走 n 步，`n = s.length()`。
* **空间复杂度**：$O(1)$。账本 `int[128]` 大小固定。

---

## 💡 记忆口诀

> **建账欠字符，右进销账 remain 减；凑齐就收缩，更新最小再还账；踢到必需字符，remain 加一继续探。**

> 关联：这套"账单差额"框架的完整推导、与双 HashMap 老框架的性能对比，见 [滑动窗口终极框架指南](Hot438-找到字符串中所有字母异位词-Hot76.md)。
