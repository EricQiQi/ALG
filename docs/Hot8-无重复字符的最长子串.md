# 无重复字符的最长子串

## 题目描述

给定字符串 `s`，找出不含有重复字符的**最长子串**的长度。

```
s = "abcabcbb"
最长无重复子串："abc"，长度 3

s = "bbbbb"
最长无重复子串："b"，长度 1
```

---

## 方法1：HashMap（字符位置瞬移）

### 思路

滑动窗口 + HashMap 记录每个字符**最后出现的位置**。右指针扩张，遇到重复字符时左指针直接跳到该字符上次出现位置的**下一格**。

```
a b c a b c b b
0 1 2 3 4 5 6 7
        ↑
    遇到重复 a，left 从 0 跳到 map[a]+1 = 1+1 = 2（实际是 3+1=4，看 b）
```

**关键**：`left = Math.max(left, map.get(ch) + 1)`，用 `Math.max` 防止左指针回退（旧重复字符可能已经在窗口外了）。

### 代码

```java
public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> map = new HashMap<>();
    int left = 0, maxLen = 0;

    for (int right = 0; right < s.length(); right++) {
        char ch = s.charAt(right);
        if (map.containsKey(ch)) {
            left = Math.max(left, map.get(ch) + 1);  // 瞬移，不走回头路
        }
        map.put(ch, right);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个字符访问一次 |
| 空间 | O(min(n, m)) | m 为字符集大小 |

---

## 方法2：int 数组（ASCII 直接寻址）

### 思路

和方法1 完全相同的逻辑，只是把 HashMap 换成 `int[128]` 数组，用字符的 ASCII 码当数组下标，查找从哈希 O(1) 变成物理 O(1)。

**巧妙之处**：数组存 `right + 1`，默认值 `0` 天然表示"未出现过"。`Math.max(left, 0)` 结果就是 `left` 本身，**用数学消灭了 if 判断**。

```
index[ch] = 0（未出现）→ left = Math.max(left, 0) = left（不动）
index[ch] = 3（上次在位置2）→ left = Math.max(left, 3) = 3（瞬移）
```

### 代码

```java
public int lengthOfLongestSubstring(String s) {
    int[] index = new int[128];
    int left = 0, maxLen = 0;

    for (int right = 0; right < s.length(); right++) {
        char ch = s.charAt(right);
        left = Math.max(left, index[ch]);        // 0 = 没出现过，left 不动
        index[ch] = right + 1;                   // 存 right+1，让默认值 0 有意义
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个字符访问一次 |
| 空间 | O(m) | 固定 128 大小，纯 O(1) |

---

## 两种方法对比

| | HashMap | int 数组 |
|--|---------|----------|
| 查找方式 | `map.get(ch)` | `index[ch]`（ASCII 直接寻址） |
| 判断是否出现过 | `containsKey` + `if` | 默认值 0 + `Math.max`，无需 if |
| 底层开销 | 自动装箱 + 哈希计算 | 无，纯内存地址访问 |

---

## 易错点

1. **左指针必须用 `Math.max`**：不能直接 `left = map.get(ch) + 1`，因为旧重复字符可能已在窗口外，直接赋值会导致左指针回退
2. **数组存 `right + 1` 而非 `right`**：因为数组默认值是 `0`，如果存 `right`，位置 0 的字符和未出现的字符无法区分
3. **先更新 left 再更新 map/index**：顺序不能反，否则会用当前位置覆盖历史位置

---

## 记忆口诀

```
无重复最长子串：
右指针吃新字符，撞车就翻账本找旧位置；
左指针瞬移到旧位置+1，Math.max 保证不回退。
```
