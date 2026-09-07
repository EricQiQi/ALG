# 算法笔记：最大子数组和（力扣 53 题）

## 题目描述
给你一个整数数组 `nums` ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

---

## 解法一：贪心算法（Greedy）

### 1. 核心思想
从头开始遍历数组，用一个变量 `preSum` 记录当前的连续子数组和。
* 如果当前的 `preSum` 变成了**负数**，说明它对后续的求和只有“负贡献”。因此，我们应该果断**放弃之前的累加**，将 `preSum` 重置为 `0`，从下一个位置重新开始计算。
* 在遍历过程中，不断用 `preSum` 更新全局最大值 `maxSum`。

### 2. 代码实现 (Java)
这里将判断逻辑放在了累加之前，整体流向更符合“发现负包袱，立即抛弃”的直觉。

```java
class Solution {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int maxSum = nums[0];
        int preSum = 0;
        
        for (int i = 0; i < nums.length; i++) {
            // 如果历史累加和是负数，说明是负资产，直接清零丢弃
            if (preSum < 0) {
                preSum = 0;
            }
            
            // 累加当前元素
            preSum += nums[i];
            
            // 更新全局最大值
            maxSum = Math.max(maxSum, preSum);
        }
        
        return maxSum;
    }
}
```

### 3. 复杂度分析
* **时间复杂度**：$O(n)$。只需对数组进行一次遍历。
* **空间复杂度**：$O(1)$。只使用了常数个额外变量。

### 4. 概念澄清：`preSum` 不是真正的“前缀和”

容易把 `preSum` 误解为“该元素前面所有元素之和”，但本题的 `preSum` 会**归零丢弃**，这与经典前缀和有本质区别：

| | 本题 `preSum` | 经典前缀和（如 Hot560 和为K） |
| :--- | :--- | :--- |
| 含义 | 以当前元素结尾的**当前保留子段**之和 | 从头累加、**永不归零** |
| 是否丢弃 | 负了就归零重开 | 从不丢弃 |

**为何初始化为 0（而非 `nums[0]`）：** i=0 时它左边没有元素，“左边的和”自然是 0；若写成 `nums[0]` 会在 i=0 时重复累加一次。注意这与 `maxSum` 初始为 `nums[0]` 不同——`maxSum` 是结果，至少要含一个元素，防止全负数组错返 0。

**`preSum` 在循环里的两个时刻：**
```java
if (preSum < 0) preSum = 0;   // ← 此刻：以 i-1 结尾的“左边保留和”，<0 则切断
preSum += nums[i];            // ← 此刻：以 i 结尾的当前子段和，拿它更新 maxSum
```

> 一句话：“左边保留和”负了就归零重开——**归零**这个动作正是它能用贪心一趟解决、而和为K必须配 HashMap 记账的根本区别。

---

## 解法二：动态规划（Dynamic Programming / Kadane 算法）

### 1. 核心思想
动态规划的核心在于**状态转移**。我们定义一个状态：
* `dp[i]` 表示**以 `nums[i]` 结尾**的连续子数组的最大和。

对于当前元素 `nums[i]`，它有两个选择：
1. **加入前面的子数组**：即 `dp[i-1] + nums[i]`（前提是 `dp[i-1]` 是正数，有正向收益）。
2. **自己独立门户，作为新子数组的起点**：即 `nums[i]`（因为前面的 `dp[i-1]` 是负数，带上它反而变小）。

**状态转移方程：**
$$dp[i] = \max(dp[i-1] + nums[i], nums[i])$$

由于 `dp[i]` 只与 `dp[i-1]` 有关，我们可以使用一个变量 `dp` 代替数组，将空间复杂度优化到 $O(1)$。这也正是著名的 **Kadane 算法**。

### 2. 代码实现 (Java)

```java
class Solution {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int maxSum = nums[0];
        int dp = nums[0]; // 初始状态：以 nums[0] 结尾的最大和就是自身
        
        for (int i = 1; i < nums.length; i++) {
            // 决定是“接在前面后面”还是“自立门户”
            dp = Math.max(dp + nums[i], nums[i]);
            
            // 全局最大值一定是所有 dp[i] 中的最大那个
            maxSum = Math.max(maxSum, dp);
        }
        
        return maxSum;
    }
}
```

### 3. 复杂度分析
* **时间复杂度**：$O(n)$。只需遍历一遍数组。
* **空间复杂度**：$O(1)$。优化后仅使用了两个变量。

---

## 解法三：分治法（Divide and Conquer）

### 1. 核心思想
把数组从中间一分为二，那么**最大子数组只可能出现在三个位置**：
1. 完全在**左半**区间；
2. 完全在**右半**区间；
3. **跨越中点**（一半在左、一半在右）。

前两种递归求解，第三种单独用线性扫描算出，三者取最大即为答案。

> 关键：跨中点的最大和 = 左半边的**最大后缀和**（从中点向左扩） + 右半边的**最大前缀和**（从中点+1向右扩）。

### 2. 递归树图解

以 `nums = [-2, 1, -3, 4]`（下标 0~3）为例：

**① 自顶向下拆分**：每个节点把区间从中点切成两半，直到只剩一个元素（叶子）

```text
                         [0,3]
                   ┌───────┴───────┐
                [0,1]            [2,3]
              ┌───┴───┐        ┌───┴───┐
            [0,0]   [1,1]    [2,2]   [3,3]
```

**② 自底向上回填**：每个节点返回 `max(左子, 跨中点, 右子)`

```text
            -2      1         -3      4      ← 叶子：单元素即答案
              \    /            \    /
        [0,1]=max(-2, -1, 1)=1   [2,3]=max(-3, 1, 4)=4
                     \                /
                 [0,3]=max(1, 2, 4) = 4  ✅
                        └ 跨中点=2
```

> 看两件事就懂了：**向下只是“切区间”**（不计算），**向上才“合答案”**——每层用左、右、跨三者取最大。跨中点值靠 `merge` 线性扫出。

### 3. 代码实现 (Java)
将“拆分”与“合并”拆成 `divide` 和 `merge` 两个方法，职责更清晰。

```java
class Solution {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return divide(nums, 0, nums.length - 1);
    }

    // 求 [left, right] 区间内的最大子数组和
    private int divide(int[] nums, int left, int right) {
        // 终止：区间只剩一个元素，最大子数组就是它自己
        if (left == right) return nums[left];

        int mid = left + (right - left) / 2;

        // 分：递归求左半、右半
        int leftMax = divide(nums, left, mid);
        int rightMax = divide(nums, mid + 1, right);
        // 治：求跨越中点的最大和
        int crossMax = merge(nums, left, mid, right);

        // 三者取最大
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }

    // 跨中点的最大子数组和
    private int merge(int[] nums, int left, int mid, int right) {
        // 从中点向左扩，记录左半边的最大后缀和
        int leftCross = Integer.MIN_VALUE, sum = 0;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftCross = Math.max(leftCross, sum);
        }
        // 从中点+1向右扩，记录右半边的最大前缀和
        int rightCross = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightCross = Math.max(rightCross, sum);
        }
        return leftCross + rightCross;
    }
}
```

### 4. 复杂度分析
* **时间复杂度**：$O(n \log n)$。每层合并（`merge`）需 $O(n)$ 扫描跨中点子段，递归共 $\log n$ 层。
* **空间复杂度**：$O(\log n)$。递归调用栈深度。

> ⚠️ 分治是三种解法里**唯一达不到 $O(n)$** 的，但它完美展示了“分-治-合”思想，与归并排序、线段树同源，面试常被拿来追问。

---

## 💡 总结与对比

| 特性 | 贪心算法 | 动态规划 (Kadane) | 分治法 |
| :--- | :--- | :--- | :--- |
| **思维出发点** | 关注**全局收益**，一旦发现累加和变负，立刻清零重来。 | 关注**局部状态**，决定当前元素是融入过去还是开启未来。 | 把区间一分为二，最大子数组必在左半、右半或跨中点三者之中。 |
| **代码表现** | 通过 `if (preSum < 0)` 进行分支截断。 | 通过 `Math.max()` 表达状态转移。 | 递归 `divide` 拆分 + `merge` 求跨中点和，三者取最大。 |
| **时间复杂度** | $O(n)$ | $O(n)$ | $O(n \log n)$ |
| **空间复杂度** | $O(1)$ | $O(1)$（滚动变量优化后） | $O(\log n)$（递归栈） |

**结论**：贪心与动态规划运行效率完全一致（都是 $O(n)$ / $O(1)$），贪心更具象好理解，DP 更系统、易扩展到区间型题；分治法虽然复杂度略高，但它是理解“分-治-合”递归思想的经典载体。
