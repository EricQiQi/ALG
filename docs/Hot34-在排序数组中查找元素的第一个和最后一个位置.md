# LeetCode 34. 在排序数组中查找元素的第一个和最后一个位置

对应源码：[Hot34_searchRange.java](../src/s11_binarySearch/Hot34_searchRange.java)

## 题目描述

给定一个**升序排列、可含重复元素**的整数数组 `nums` 和一个目标值 `target`，返回 target 在数组中的**起始位置和结束位置** `[start, end]`；不存在则返回 `[-1, -1]`。

要求时间复杂度为 `O(log n)` —— 即必须用二分查找。

例：

```
nums = [5,7,7,8,8,10]
target = 8  → [3, 4]   （8 出现在下标 3~4）
target = 6  → [-1, -1] （不存在）
```

## 💡 大白话解析（命中不返回，继续收缩）

普通二分命中就返回 mid，但本题命中时**既不能停也不能走开**——mid 处只是众多重复元素之一，还要继续找边界。核心技巧一句话：

> **命中后不返回，而是把 mid 记下来，再朝边界方向多走一步，把区间继续压小。**

跑**两次二分**，唯一的区别就在命中分支：

1. **找左边界（start）**：命中时记录 `start = mid`，然后 `right = mid - 1`，逼着搜索继续往**左**找，看还有没有更靠前的 target；
2. **找右边界（end）**：命中时记录 `end = mid`，然后 `left = mid + 1`，逼着搜索继续往**右**找，看还有没有更靠后的 target。

非命中分支两次完全一样：大了收右、小了进左。循环退出后，最后一次记录的 mid 恰好就是最靠左（或最靠右）的 target；一次都没命中过，`start` / `end` 就保持初值 `-1`。

```
nums = [5,7,7,8,8,10], target = 8

第一轮（找左边界）：
left=0 right=5 mid=2 → nums[2]=7 < 8 → left=3
left=3 right=5 mid=4 → nums[4]=8 命中 → start=4, right=3   （还能往左找）
left=3 right=3 mid=3 → nums[3]=8 命中 → start=3, right=2   （更靠左了）
left=3 > right=2，退出 → start = 3

第二轮（找右边界）：
left=0 right=5 mid=2 → nums[2]=7 < 8 → left=3
left=3 right=5 mid=4 → nums[4]=8 命中 → end=4, left=5      （还能往右找）
left=5 right=5 mid=5 → nums[5]=10 > 8 → right=4
left=5 > right=4，退出 → end = 4

结果 [3, 4]
```

## 💻 Java 代码实现

```java
public int[] searchRange(int[] nums, int target) {
    int start = -1, end = -1;

    // 第一次二分：找左边界，命中后 right = mid - 1 继续往左压
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;   // 防溢出写法
        if (nums[mid] == target) {
            start = mid;          // 先记下候选答案
            right = mid - 1;      // 不返回，继续往左找更靠前的
        } else if (nums[mid] > target) {
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }

    // 第二次二分：找右边界，命中后 left = mid + 1 继续往右压
    left = 0;
    right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) {
            end = mid;            // 先记下候选答案
            left = mid + 1;       // 不返回，继续往右找更靠后的
        } else if (nums[mid] > target) {
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }
    return new int[]{start, end}; // 一次都没命中时保持 -1，天然处理"不存在"
}
```

## 复杂度分析

| 项 | 复杂度 | 说明 |
| :--- | :---: | :--- |
| 时间 | O(log n) | 两次独立二分，每轮区间减半 |
| 空间 | O(1) | 只用了 left/right/mid/start/end 几个变量 |

## ⚠️ 易错点

1. **命中就 return**：普通二分模板命中直接返回，本题必须改成「记录 + 继续收缩」，否则只找到重复元素中的任意一个，不是边界。
2. **收缩方向记反**：找**左**边界命中后 `right = mid - 1`（往左压），找**右**边界命中后 `left = mid + 1`（往右压）——方向与要找的边界**相反**才对，这样才能排除 mid、把答案逼到边界。
3. **初值不是 0**：`start` / `end` 必须初始化为 `-1`，因为"数组里根本没有 target"要靠"从没命中过"来体现。
4. **mid 溢出**：用 `left + (right - left) / 2`，与 Hot35 同理。
5. **两段循环只差命中分支**：非命中分支（大于收右、小于进左）完全相同，记住差异只在命中后的走向即可，不必背两套模板。

## 🆚 与 Hot35 的关系

| 题 | 命中后行为 | 求的是什么 |
| :--- | :--- | :--- |
| Hot35 搜索插入位置 | 直接 return | 第一个 ≥ target 的位置（lower_bound） |
| Hot34 本题·第一轮 | 记录后 `right = mid - 1` | 第一个 == target 的位置（左边界） |
| Hot34 本题·第二轮 | 记录后 `left = mid + 1` | 最后一个 == target 的位置（右边界） |

Hot35 是"命中即答案"，Hot34 是"命中后继续压区间找极端"。三者共用同一套闭区间二分骨架，只是命中分支不同。

## 🧠 记忆口诀

> **两次二分找边界，命中不返回：记下 mid，找左往左压（right=mid-1），找右往右压（left=mid+1）；没命中过就是 -1。**

## 📌 备注

该题属于**二分查找**题型，源文件归入 `s11_binarySearch` 包，建立在 Hot35 的闭区间二分模板之上，是「二分找边界」的标准题。同类变体（如求数组中 target 的出现次数）可直接用 `end - start + 1` 得到。
