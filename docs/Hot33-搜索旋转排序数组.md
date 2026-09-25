# LeetCode 33. 搜索旋转排序数组

对应源码：[Hot33_search.java](../src/s11_binarySearch/Hot33_search.java)

## 题目描述

整数数组 `nums` 按升序排列，且**无重复元素**。在某个下标处被**旋转**了一次（例如 `[0,1,2,4,5,6,7]` 旋转后可能变成 `[4,5,6,7,0,1,2]`）。

给定旋转后的数组 `nums` 和目标值 `target`，若 `target` 存在于数组中则返回其下标，否则返回 `-1`。

要求时间复杂度为 `O(log n)` —— 即必须用二分查找。

例：

```
nums = [4,5,6,7,0,1,2], target = 0 → 4
nums = [4,5,6,7,0,1,2], target = 3 → -1
nums = [1],             target = 0 → -1
```

## 💡 大白话解析（二分 + 判断哪半有序）

数组虽然整体被旋转打乱了，但有一个关键性质：**从任意位置 `mid` 切开，左右两半里必定有一半是完全有序的**。

```
[4, 5, 6, 7, 0, 1, 2]
 ↑mid=3(值7) 切开：
左半 [4,5,6,7] 有序      右半 [0,1,2] 也有序（本题恰好两半都有序）

一般情况只会有一半有序，另一半跨越了旋转点。
```

于是每一轮二分这样走：

1. `nums[mid] == target` → 命中，直接返回；
2. 判断哪半有序：用 `nums[left] <= nums[mid]` 判定**左半区 `[left, mid]` 有序**，否则**右半区 `[mid, right]` 有序**；
3. 在**有序的那一半**里，用两端值就能确定 target 是否落在其中：
   - 落在有序半区内 → 收缩到这一半；
   - 不在 → 只能去另一半（乱序的那半）继续找。

**为什么用 `nums[left] <= nums[mid]` 而不是 `<`？** 因为闭区间里当 `left == mid`（区间只剩一两个元素）时，`nums[left] == nums[mid]`，这种情况应归为「左半区有序」，用 `<=` 才不会漏判。

```
nums = [4,5,6,7,0,1,2], target = 0：

left=0 right=6 mid=3 → nums[3]=7 ≠ 0
  nums[0]=4 <= 7，左半 [4..7] 有序
  target=0 不在 [4,7) 内 → left = mid+1 = 4
left=4 right=6 mid=5 → nums[5]=1 ≠ 0
  nums[4]=0 <= 1，左半 [0,1] 有序
  target=0 在 [0,1) 内 → right = mid-1 = 4
left=4 right=4 mid=4 → nums[4]=0 == target → 返回 4
```

## 💻 Java 代码实现

```java
public static int search(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;   // 空数组直接无解
    if (nums.length == 1) return nums[0] == target ? 0 : -1;  // 只有一个元素，比对即可

    int left = 0, right = nums.length - 1;
    while (left <= right) {                            // 闭区间 [left, right]，用 <= 才能查到最后一个元素
        int mid = left + (right - left) / 2;           // 防溢出写法，等价于 (left+right)/2
        if (nums[mid] == target) return mid;           // 命中，直接返回下标

        // 旋转数组从 mid 切开后，必有一半是完全有序的，先判断哪半有序
        if (nums[left] <= nums[mid]) {                 // 左半区 [left, mid] 有序（含 left==mid 的单元素情况）
            if (nums[left] <= target && target < nums[mid]) {
                right = mid - 1;                       // target 落在有序的左半区，收缩右边界
            } else {
                left = mid + 1;                        // 否则只能去右半区找
            }
        } else {                                       // 右半区 [mid, right] 有序
            if (nums[mid] < target && target <= nums[right]) {
                left = mid + 1;                        // target 落在有序的右半区，收缩左边界
            } else {
                right = mid - 1;                       // 否则去左半区找
            }
        }
    }
    return -1;                                         // 区间收缩为空仍未命中
}
```

## 复杂度分析

| 项 | 复杂度 | 说明 |
| :--- | :---: | :--- |
| 时间 | O(log n) | 每轮区间减半，只多一次「哪半有序」的判断 |
| 空间 | O(1) | 只用了 left/right/mid 三个变量 |

## ⚠️ 易错点

1. **判断有序用 `<` 而非 `<=`**：`nums[left] < nums[mid]` 会在 `left == mid`（区间只剩一个元素）时误判为「右半区有序」，导致边界元素查不到。必须用 `<=`。
2. **落区间的边界符号**：左半区判断用 `nums[left] <= target && target < nums[mid]`，右半区用 `nums[mid] < target && target <= nums[right]`——`nums[mid]` 那侧一律用**严格不等号**（mid 已在前面比对过），外侧端点用 `<=` 才能含住边界值。
3. **误以为要先找到旋转点**：不需要单独二分找旋转点，一次二分里边判断有序半边、边收缩即可。
4. **以为能直接用普通二分**：数组整体无序，直接 `nums[mid] > target` 收缩会漏掉另一半的解，必须先定位有序半边。

## 🆚 命中判断 vs 落区判断

| 步骤 | 判断依据 | 作用 |
| :--- | :--- | :--- |
| 是否命中 | `nums[mid] == target` | 命中立即返回 mid |
| 哪半有序 | `nums[left] <= nums[mid]` | 决定接下来在哪个半区做落区判断 |
| target 是否落在有序半区 | 有序半区的两端值夹逼 | 落区内则收缩到该半区，否则去另一半 |

核心思想：**永远在「有序的那一半」里用两端值判断 target 在不在**，因为乱序的半区无法靠端点确定范围。

## 🧠 记忆口诀

> **mid 切开必有一半有序；`nums[left] <= nums[mid]` 定左半有序，否则右半有序。在有序半区用两端夹 target：夹得住就进去，夹不住就去另一半。判断有序别忘 `<=`。**

## 📌 备注

该题属于**二分查找**题型，源文件归入 `s11_binarySearch` 包。它是「旋转数组二分」的经典模板，与 [搜索插入位置](Hot35-搜索插入位置.md)、[搜索二维矩阵](Hot74-搜索二维矩阵.md) 同属二分家族。若数组**含重复元素**（LeetCode 81），当 `nums[left] == nums[mid]` 时无法判断哪半有序，只能 `left++` 跳过，最坏退化到 `O(n)`。
