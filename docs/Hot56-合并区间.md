# 算法笔记：合并区间（力扣 56 题）

## 题目描述
以数组 `intervals` 表示若干个区间的集合，其中 `intervals[i] = [start_i, end_i]`。请合并所有重叠的区间，并返回一个不重叠的区间数组。

```
输入: intervals = [[1,3],[2,6],[8,10],[15,18]]
输出: [[1,6],[8,10],[15,18]]
解释: [1,3] 和 [2,6] 重叠，合并成 [1,6]
```

---

## 核心思想

> **先按左端点排序，再一次遍历：能接上就扩展右端点，接不上就新开一个区间。**

排序后，重叠的区间一定会**挨在一起**，所以只需和"结果列表里最后一个区间"比较即可，不用两两对比。

---

## 图解：排序 + 逐个合并

```
排序后（按左端点）:  [1,3] [2,6] [8,10] [15,18]

数轴上看重叠：
  1───3
    2──────6        2 ≤ 3 → 重叠，合并成 [1,6]
  1──────6
          8──10     8 > 6 → 不重叠，新开 [8,10]
                15──18   15 > 10 → 不重叠，新开 [15,18]

结果: [1,6] [8,10] [15,18]
```

**判断重叠的条件**：当前区间左端点 `l` ≤ 结果列表最后一个区间的右端点 → 重叠。

---

## 代码实现 (Java)

```java
public static int[][] merge(int[][] intervals) {
    if (intervals == null || intervals.length == 0) return intervals;

    // 1. 按左端点升序排序
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

    List<int[]> merged = new ArrayList<>();
    // 2. 遍历合并
    for (int[] interval : intervals) {
        int l = interval[0], r = interval[1];
        // 列表空，或当前左端点 > 最后一个区间的右端点 → 不重叠，新开
        if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < l) {
            merged.add(new int[]{l, r});
        } else {
            // 重叠 → 扩展最后一个区间的右端点（取 max！）
            int[] last = merged.get(merged.size() - 1);
            last[1] = Math.max(last[1], r);
        }
    }
    // 3. List 转二维数组
    return merged.toArray(new int[merged.size()][]);
}
```

---

## ❗关键易错点：右端点必须取 `max`，不能直接赋值

重叠时更新右端点，一定要 `last[1] = Math.max(last[1], r)`，**不能写成 `last[1] = r`**。

反例：`[[1,10],[2,3]]`

```
若直接赋值 last[1] = r：
  [1,10] 遇到 [2,3] → 变成 [1,3]   ❌ 把大区间缩水了！
正确取 max(10, 3) = 10：
  [1,10] 遇到 [2,3] → 仍是 [1,10]  ✅ 后一个被前一个完全包含
```

因为排序只保证了**左端点**有序，右端点可能"后一个比前一个短"（被包含），所以必须取两者较大值。

---

## 复杂度分析

* **时间复杂度**：$O(n \log n)$。主要开销在排序，遍历合并只需 $O(n)$。
* **空间复杂度**：$O(\log n)$（排序栈）或 $O(n)$（结果列表，取决于是否计入输出）。

---

## 💡 记忆口诀

> **左端点排序打头阵，逐个比对最后一个：接得上就 max 扩右界，接不上就新开区间。**

> 关联：排序写法（`Comparator.comparingInt` 防溢出）详见 [Arrays.sort 原理详解](Tip1-Arrays.sort原理详解.md)。
