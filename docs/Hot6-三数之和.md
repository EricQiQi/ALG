# 三数之和（3Sum）

## 题目描述

给定一个整数数组 `nums`，找出所有**和为 0** 且**不重复**的三元组 `[nums[i], nums[j], nums[k]]`。

> 约束：`i != j != k`，答案中不能包含重复的三元组。

示例：
```
输入：nums = [-1, 0, 1, 2, -1, -4]
输出：[[-1, -1, 2], [-1, 0, 1]]
```

---

## 核心思路

### 关键洞察

**三数之和 → 固定一个数 → 两数之和**

```
nums[i] + nums[j] + nums[k] = 0
                    ↓
        nums[j] + nums[k] = 0 - nums[i]
                    ↓
              两数之和 = target
```

把三数之和**降维**成两数之和，是本题的核心转化。

---

## 解法一：HashSet

### 算法步骤

1. **排序** + 剪枝（最小值 > 0 直接返回）
2. **外层循环**：固定 `nums[i]`，算出 `target = -nums[i]`
3. **内层循环**：遍历 `j = i+1 ... n-1`，用 HashSet 做两数之和
   - `target - nums[j]` 在 set 中 → 找到三元组
   - 不在 → 把 `nums[j]` 加入 set，留给后面匹配
4. 用 `HashSet<List<Integer>>` 去重

### 代码实现

```java
public List<List<Integer>> threeSum_1(int[] nums) {
    Arrays.sort(nums);
    if (nums[0] > 0) return new ArrayList<>();  // 剪枝

    Set<List<Integer>> result = new HashSet<>();
    for (int i = 0; i < nums.length - 2; i++) {
        int target = -nums[i];
        Set<Integer> set = new HashSet<>();
        for (int j = i + 1; j < nums.length; j++) {
            if (set.contains(target - nums[j])) {
                result.add(Arrays.asList(nums[i], target - nums[j], nums[j]));
            } else {
                set.add(nums[j]);
            }
        }
    }
    return new ArrayList<>(result);
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n²) | 两层循环 |
| 空间 | O(n) | HashSet 存储 |

---

## 解法二：排序 + 双指针

### 算法步骤

1. **排序**
2. **外层循环**：固定 `nums[i]`，双指针 `left = i+1`，`right = n-1`
3. **计算三数之和 `sum`**：
   - `sum == 0` → 记录结果，左右指针同时内移，并跳过重复值
   - `sum < 0` → 左指针右移（需要更大的数）
   - `sum > 0` → 右指针左移（需要更小的数）
4. 外层循环也跳过重复的 `nums[i]` 来去重

### 代码实现

```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();

    for (int i = 0; i < nums.length - 2; i++) {
        if (nums[i] > 0) break;  // 剪枝
        if (i > 0 && nums[i] == nums[i - 1]) continue;  // 去重

        int left = i + 1, right = nums.length - 1;
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            if (sum == 0) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                while (left < right && nums[left] == nums[left + 1]) left++;
                while (left < right && nums[right] == nums[right - 1]) right--;
                left++;
                right--;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }
    return result;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n²) | 外层循环 + 双指针遍历 |
| 空间 | O(1) | 不需要额外空间（不计结果集） |

---

## 两种解法对比

| 维度 | HashSet | 双指针 |
|------|---------|--------|
| 去重方式 | HashSet 自动去重 | 手动跳过重复值 |
| 空间 | O(n) | O(1) |
| 代码难度 | 简洁直观 | 需要处理边界 |
| 适用场景 | 快速写出 | 面试更优 |

---

## 剪枝技巧

排序后可以利用数值大小提前终止：

```
剪枝1：nums[i] > 0        → 三个正数之和不可能为 0，直接 break
剪枝2：nums[i] + nums[i+1] + nums[i+2] > 0  → 最小的三个数之和都 > 0，后面更不可能
```

> **剪枝 = 提前排除不可能的情况，减少无意义的计算。**

---

## 记忆口诀

```
三数变两数，固定一个找两个
排序双指针，小移左大移右
等于就记录，去重跳重复
```
