# 和为 K 的子数组（力扣 560 题）

## 题目描述
给你一个整数数组 `nums` 和一个整数 `k`，请你统计并返回该数组中和为 `k` 的连续子数组的个数。

---

## 核心思路

### 为什么不能用滑动窗口？

滑动窗口的前提是区间具备**单调性**（元素全为正数时，越加越大）。但本题**数组元素可为负数和零**，导致窗口和不再单调变化，双指针方向完全失控，滑动窗口失效。

### 正确思路：前缀和 + HashMap

**核心公式**：`子数组和(i, j) = preSum[j] - preSum[i-1] = k`

变换得：**`preSum[i-1] = preSum[j] - k`**

即：遍历到位置 `j` 时，只需在历史前缀和中查找 `preSum - k` 出现过几次，就是以 `j` 结尾的满足条件的子数组个数。

HashMap 的作用就是记录每个前缀和出现的次数，实现 O(1) 查找。

---

## 解法一：前缀和 + HashMap（通用解法）

### 代码实现

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;

        // Key: 前缀和, Value: 出现次数
        Map<Integer, Integer> map = new HashMap<>();
        // 初始化：前缀和为 0 默认出现 1 次（处理从下标 0 开始的子数组）
        map.put(0, 1);

        int preSum = 0, count = 0;

        for (int num : nums) {
            preSum += num;

            // 逆向查账：在历史前缀和中找 preSum - k
            if (map.containsKey(preSum - k)) {
                count += map.get(preSum - k);
            }

            // 将当前前缀和登记到账本
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }

        return count;
    }
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 遍历一遍数组，HashMap 查找 O(1) |
| 空间 | O(n) | 最坏情况需存 n 个不同前缀和 |

### ⚠️ 易错点

查账表达式是 `preSum - k`，不是 `k - nums[i]`。map 的 key 空间是**前缀和**，不是元素值，注意和"两数之和"区分。

---

## 解法二：滑动窗口（仅限全正数场景）

如果数组**全部是正整数**，区间重新具备单调性，可以改用滑动窗口，将空间降到 O(1)。

### 代码实现

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        int left = 0, sum = 0, count = 0;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > k && left <= right) {
                sum -= nums[left];
                left++;
            }

            if (sum == k) {
                count++;
            }
        }
        return count;
    }
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n) | 每个元素最多被进出各一次 |
| 空间 | O(1) | 仅用两个指针和常量变量 |

---

## 两种解法对比

| 维度 | 前缀和 + HashMap | 滑动窗口 |
|------|------------------|----------|
| 适用条件 | 通用（含负数、零） | 仅全正数 |
| 时间复杂度 | O(n) | O(n) |
| 空间复杂度 | O(n) | O(1) |
| 核心操作 | 逆向查账 `preSum - k` | 窗口伸缩维护 `sum` |

---

## 记忆口诀

```
有负数不能用滑窗，前缀和加哈希来帮忙
map 记前缀和出现次数，查 preSum 减 k 就是答案
```
