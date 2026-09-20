# LeetCode 35. 搜索插入位置

对应源码：[Hot35_searchInsert.java](../src/s10_backTracking/Hot35_searchInsert.java)

## 题目描述

给定一个**升序排列、无重复元素**的整数数组 `nums` 和一个目标值 `target`。若目标存在则返回其下标；若不存在，返回它**按顺序插入**的位置。

要求时间复杂度为 `O(log n)` —— 即必须用二分查找。

例：

```
nums = [1,3,5,6]
target = 5 → 2   （已存在，返回下标）
target = 2 → 1   （插在 1 和 3 之间）
target = 7 → 4   （比所有元素都大，插到末尾）
target = 0 → 0   （比所有元素都小，插到开头）
```

## 💡 大白话解析（二分查找插入点）

本题本质是找**「第一个 ≥ target 的位置」**（lower_bound）：

- 找到相等 → 直接返回该下标；
- 找不到 → 循环结束时 `left` 恰好停在「第一个比 target 大的元素」处，也就是 target 应该插入的位置。

标准二分：用 `left`、`right` 夹逼，每次取中点 `mid`：

1. `nums[mid] > target`：目标在左半区，收缩右边界 `right = mid - 1`；
2. `nums[mid] < target`：目标在右半区，收缩左边界 `left = mid + 1`；
3. `nums[mid] == target`：命中，直接返回 `mid`。

**为什么返回 `left` 就是插入位置？** 循环的不变量是：`[0, left)` 里的元素全都 `< target`，`(right, n-1]` 里的元素全都 `> target`。当 `left > right` 循环退出时，`left` 正是「第一个 ≥ target」的下标——比 target 大的都排在它右边，比它小的都在左边，插这里刚好保持有序。

```
nums = [1,3,5,6], target = 2：

left=0 right=3 mid=1 → nums[1]=3 > 2 → right=0
left=0 right=0 mid=0 → nums[0]=1 < 2 → left=1
left=1 > right=0，退出 → 返回 left = 1   （2 插在 1 和 3 之间）
```

## 💻 Java 代码实现

```java
public int searchInsert(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left <= right) {                    // 闭区间 [left, right]，用 <= 才能查到最后一个元素
        int mid = left + (right - left) / 2;   // 防溢出写法，等价于 (left+right)/2
        if (nums[mid] > target) {
            right = mid - 1;                   // 目标在左半区
        } else if (nums[mid] < target) {
            left = mid + 1;                    // 目标在右半区
        } else {
            return mid;                        // 命中，直接返回
        }
    }
    return left;                               // 未命中，left 即插入位置
}
```

## 复杂度分析

| 项 | 复杂度 | 说明 |
| :--- | :---: | :--- |
| 时间 | O(log n) | 每轮区间减半 |
| 空间 | O(1) | 只用了 left/right/mid 三个变量 |

## ⚠️ 易错点

1. **循环条件用 `<` 而非 `<=`**：`while (left < right)` 会漏掉区间只剩一个元素的情况，可能查不到边界上的 target。闭区间写法必须配 `<=`。
2. **mid 溢出**：`(left + right) / 2` 在 left、right 都很大时会整型溢出；用 `left + (right - left) / 2` 规避。
3. **返回值搞错**：未命中时应返回 `left`（第一个 ≥ target 的位置），返回 `right` 会差一位（right 停在最后一个 < target 处）。
4. **误以为要真的插入元素**：题目只要求返回**位置下标**，不需要改动数组。

## 🆚 命中返回 vs 未命中返回 left 的统一视角

| 场景 | 循环退出原因 | 返回 |
| :--- | :--- | :--- |
| target 存在 | `nums[mid] == target` 提前 return | `mid` |
| target 不存在 | `left > right` 区间收缩为空 | `left`（插入点） |

两种情况可以统一理解为「找第一个 ≥ target 的下标」：存在时该下标就是 target 本身，不存在时就是插入位置。这也是 C++ `lower_bound`、Java `Arrays.binarySearch`（返回值取反后为插入点）背后的同一套逻辑。

## 🧠 记忆口诀

> **闭区间用 `<=`，mid 防溢出；大了收右、小了进左、相等就返回；查不到时 `left` 即插入点。**

## 📌 备注

该源文件当前放在 `s10_backTracking`（回溯）包下，但 LeetCode 35 属于**二分查找**题型，与 `src/search/BinarySearch.java` 同类。若后续按题型归档，可考虑移到 `search` 包（非功能性问题，不影响运行）。
