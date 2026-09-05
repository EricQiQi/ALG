# 字母异位词分组（Group Anagrams）

## 题目描述

给定一个字符串数组，将所有互为**字母异位词**的字符串归为一组。

> **字母异位词**：由相同字母重新排列组成的不同单词。  
> 例如：`"eat"`、`"tea"`、`"ate"` 互为字母异位词。

---

## 核心思路

### 关键洞察

**将字符串排序后，互为异位词的字符串会得到完全相同的排序结果。**

```
"eat" → 排序 → "aet"
"tea" → 排序 → "aet"
"ate" → 排序 → "aet"
```

排序后的字符串 `"aet"` 就可以作为 **HashMap 的 key**，将这三个词归入同一组。

---

## 算法步骤

1. **创建 HashMap**：`Map<String, List<String>>`
   - key：排序后的字符串
   - value：属于该组的所有原始字符串

2. **遍历每个字符串**：
   - 将字符串转为字符数组并排序
   - 用排序后的结果作为 key
   - 将原始字符串加入 key 对应的列表中

3. **返回结果**：将 HashMap 中所有 value 收集为列表返回

---

## 代码实现

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    for (String s : strs) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr);              // 核心：排序生成统一 key
        String key = new String(arr);
        List<String> list = map.getOrDefault(key, new ArrayList<>());
        list.add(s);
        map.put(key, list);
    }
    return new ArrayList<>(map.values());
}
```

---

## 图解示例

输入：`["eat", "tea", "tan", "ate", "nat", "bat"]`

```
HashMap 结构：
┌─────────┬──────────────────────┐
│  key    │       value          │
├─────────┼──────────────────────┤
│ "aet"   │ ["eat","tea","ate"]  │
│ "ant"   │ ["tan","nat"]        │
│ "abt"   │ ["bat"]              │
└─────────┴──────────────────────┘
```

---

## 复杂度分析

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间复杂度 | O(n · k log k) | n = 字符串个数，k = 字符串最大长度 |
| 空间复杂度 | O(n · k) | HashMap 存储所有字符串 |

---

## 技巧总结

> **特征映射法**：将具有相同特征的元素映射到同一个桶（Bucket）中。

本题的"特征"就是：**字符串排序后的结果相同**。

HashMap 是实现特征映射的最佳数据结构。
