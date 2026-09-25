# LeetCode 4. 寻找两个正序数组的中位数

对应源码：[Hot4_findMedianSortedArrays.java](../src/s11_binarySearch/Hot4_findMedianSortedArrays.java)

## 题目描述

给定两个大小分别为 `m` 和 `n` 的**正序（从小到大）数组** `nums1` 和 `nums2`，请找出并返回这两个正序数组的**中位数**。

要求时间复杂度为 `O(log(m + n))` —— 即必须用二分，不能直接合并（合并是 `O(m+n)`）。

例：

```
nums1 = [1,3], nums2 = [2]     → 合并 [1,2,3]，中位数 = 2.0
nums1 = [1,2], nums2 = [3,4]   → 合并 [1,2,3,4]，中位数 = (2+3)/2 = 2.5
```

## 💡 大白话解析（分割线法）

中位数的本质是把所有元素**切成等长的左右两半**，使得：

1. 左半的**所有元素 ≤ 右半的所有元素**；
2. 左半元素个数 = `(m+n+1)/2`（奇数时左半比右半多 1 个）。

那么中位数就由**分割线两侧交界的 4 个数**决定：

```
nums1:  ... L1 | R1 ...      （nums1 左半最大 L1，右半最小 R1）
nums2:  ... L2 | R2 ...      （nums2 左半最大 L2，右半最小 R2）
          左半 | 右半
```

- **总数为奇**：中位数 = 左半的最大值 = `max(L1, L2)`；
- **总数为偶**：中位数 = `(左半最大 + 右半最小) / 2` = `(max(L1,L2) + min(R1,R2)) / 2`。

### 关键：只在较短数组上二分

由于左半总数固定为 `totalLeft`，只要确定 `nums1` 左半取几个（记作 `i`），`nums2` 左半取几个就被唯一确定：`j = totalLeft - i`。所以**只需在 `nums1` 上二分 `i`（范围 `[0, m]`）**，`j` 随之算出。

为保证 `j` 不越界（`j ≥ 0` 且 `j ≤ n`），必须让 `nums1` 是**较短**的那个数组——这就是开头 `if(nums1.length > nums2.length)` 交换的原因。

### 二分调整方向

合法分割的判定条件：`L1 <= R2 && L2 <= R1`（同一数组内 `L1<=R1`、`L2<=R2` 天然成立，只需**交叉比较**）。不合法时：

- `L1 > R2`：`nums1` 左半拿太多、太大了 → 分割线**左移**，`right = i-1`；
- 否则（即 `L2 > R1`）：`nums2` 左半拿太多 → `nums1` 分割线**右移**，`left = i+1`。

### 越界哨兵

当 `i==0`（nums1 左半为空）时 `L1` 取 `Integer.MIN_VALUE`，让它不参与 `max`；当 `i==m`（右半为空）时 `R1` 取 `Integer.MAX_VALUE`，让它不参与 `min`。`L2/R2` 同理。这样交界比较逻辑无需为边界写特判。

```
nums1 = [1,3], nums2 = [2]  （m=2,n=1,total=3,totalLeft=2）

left=0 right=2 → i=1, j=2-1=1
  L1=nums1[0]=1, L2=nums2[0]=2
  R1=nums1[1]=3, R2=(j==n)→MAX
  L1<=R2 (1<=MAX) && L2<=R1 (2<=3) ✅ 合法
  总数 3 为奇 → 返回 max(L1,L2)=max(1,2)=2.0
```

## 💻 Java 代码实现

```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    // 保证 nums1 是较短的数组，使二分范围更小，也避免 j 算出负数越界
    if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);

    int m = nums1.length, n = nums2.length;
    // 左半部分应含的元素个数；+1 使奇数总数时左半多拿一个（中位数就落在左半最大处）
    int totalLeft = (m + n + 1) / 2;

    // 在 nums1 上二分“分割线”位置 i：nums1 左半取 i 个，nums2 左半取 j=totalLeft-i 个
    int left = 0, right = m;
    while (left <= right) {
        int i = left + (right - left) / 2;   // nums1 的分割位置（左半元素个数）
        int j = totalLeft - i;               // nums2 的分割位置，两者之和恒为 totalLeft

        // L1/L2 = 两数组左半的最大值；R1/R2 = 两数组右半的最小值
        // 越界时用哨兵：左半空→MIN（不影响 max），右半空→MAX（不影响 min）
        int L1 = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
        int L2 = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
        int R1 = i == m ? Integer.MAX_VALUE : nums1[i];
        int R2 = j == n ? Integer.MAX_VALUE : nums2[j];

        // 分割合法：左半所有元素 ≤ 右半所有元素（同数组内天然有序，只需交叉比较）
        if (L1 <= R2 && L2 <= R1) {
            if ((m + n) % 2 == 1) {
                return Math.max(L1, L2);   // 总数为奇：中位数 = 左半最大值
            }
            // 总数为偶：中位数 = (左半最大 + 右半最小) / 2
            return (double) (Math.max(L1, L2) + Math.min(R1, R2)) / 2;
        }

        if (L1 > R2) {
            right = i - 1;   // nums1 左半取多了（L1 太大），分割线左移
        } else {
            left = i + 1;    // nums2 左半取多了（L2 > R1），nums1 分割线右移
        }
    }
    throw new IllegalArgumentException("输入数组非正序");
}
```

## 复杂度分析

| 项 | 复杂度 | 说明 |
| :--- | :---: | :--- |
| 时间 | O(log(min(m, n))) | 只在**较短**数组上二分分割位置 i，满足题目 O(log(m+n)) 要求 |
| 空间 | O(1) | 只用了 i/j/L1/L2/R1/R2 等常量变量 |

## ⚠️ 易错点

1. **必须交换保证 nums1 较短**：否则 `j = totalLeft - i` 可能为负数或超过 `n`，导致 `nums2[j-1]` / `nums2[j]` 越界。
2. **`totalLeft` 用 `(m+n+1)/2` 而非 `(m+n)/2`**：`+1` 让奇数总数时左半多含一个元素，中位数才恰好是「左半最大值」，奇偶两种情况可统一处理。
3. **哨兵方向别写反**：左半为空时 `L` 取 `MIN`（因为它要参与 `max`，MIN 不会成为最大值）；右半为空时 `R` 取 `MAX`（参与 `min`，MAX 不会成为最小值）。
4. **交叉比较写成同数组比较**：合法条件是 `L1<=R2 && L2<=R1`（跨数组），不是 `L1<=R1`——后者天然成立，无需判断。
5. **偶数情况忘记转 double**：`(max + min)` 是 int，除以 2 前要先 `(double)` 强转，否则丢失小数部分。
6. **收缩方向搞反**：`L1 > R2` 说明 nums1 左半太大，要**减小** i（`right = i-1`）；反之增大 i。写反会二分不到正确分割。

## 🆚 分割线二分 vs 直接合并

| | 分割线二分（本题解） | 双指针合并 |
| :--- | :--- | :--- |
| 时间 | O(log(min(m,n))) | O(m+n) |
| 空间 | O(1) | O(1)（只走到中位数即可，不必真建数组） |
| 是否满足题目要求 | ✅ 满足 O(log) | ❌ 不满足，会被判超时/不符合 |
| 思路难度 | 高（分割线 + 哨兵 + 交叉比较） | 低（模拟归并，走到第 (m+n)/2 个） |

面试若只要求「能跑」，双指针合并更稳；但题目明确要求 `O(log(m+n))`，必须掌握分割线二分。

## 🧠 记忆口诀

> **中位数 = 把两数组切一刀，左半总数 `(m+n+1)/2`，左半都 ≤ 右半。只在短数组二分 i，`j=totalLeft-i`；交界四数 `L1,L2 | R1,R2`，越界用 MIN/MAX 兜底。合法判据交叉比 `L1<=R2 && L2<=R1`：奇数返回 `max(L1,L2)`，偶数返回 `(max(L1,L2)+min(R1,R2))/2`。L1 太大就左移，否则右移。**

## 📌 备注

该题属于**二分查找**题型（LeetCode Hard），源文件归入 `s11_binarySearch` 包。它与本包其他题的区别在于：二分的对象不是「数组下标值」，而是**分割线位置**——把「找中位数」转化为「找一条满足条件的分割线」。这是二分「答案空间」思想的典型代表，与 [搜索插入位置](Hot35-搜索插入位置.md)、[搜索旋转排序数组](Hot33-搜索旋转排序数组.md) 的「值二分」形成对照。
