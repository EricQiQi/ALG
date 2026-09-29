# 数组中的第 K 个最大元素（Kth Largest Element）

## 题目描述

给定整数数组 `nums` 和整数 `k`，请返回数组中**第 k 个最大**的元素。

注意：第 k 个最大是**排序后**从大到小数的第 k 个，不是第 k 个不同的元素。

```
输入：nums = [3,2,1,5,6,4], k = 3
输出：4
解释：降序排列 6 5 4 3 2 1，第 3 个是 4
```

> 进阶：能否设计 O(n) 时间复杂度的算法？（快选 / BFPRT，本文档聚焦四种常见写法）

源码位置：[`../src/s13_dump/Hot215_findKthLargest.java`](../src/s13_dump/Hot215_findKthLargest.java)

---

## 核心思路

四条路线，本质分两类：

```
① 全排序类：把整个数组排好，直接取 nums[n-k]
   方法1 Arrays.sort / 方法2 手写快排        → O(n log n)

② 部分选择类：只关心"前 k 大"，不必全排序
   方法3 小顶堆（容量 k）                     → O(n log k)
   方法4 大顶堆（下沉 k-1 次）                → O(n log n)，但可优化到 O(n + k log n)
```

---

## 方法1：排序（Arrays.sort）

### 逻辑

直接调用库排序，第 k 大 = 升序数组的倒数第 k 个。

```java
public int findKthLargest_1(int[] nums, int k) {
    Arrays.sort(nums);
    return nums[nums.length - k];   // 升序排列，倒数第 k 个
}
```

> `Arrays.sort` 对基本类型 `int[]` 用**双轴快速排序**（见 [`Tip1-Arrays.sort原理详解.md`](Tip1-Arrays.sort原理详解.md)）。

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n log n) | 排序代价 |
| 空间 | O(log n) | 快排递归栈（双轴快排） |

---

## 方法2：手写快速排序

### 逻辑

自己实现快排再取值，和方法1本质相同，只是把库函数换成手写。

```java
public int findKthLargest_2(int[] nums, int k) {
    QuickSort.quickSort(nums);
    return nums[nums.length - k];
}
```

### 复杂度

同方法1：时间 O(n log n)，空间 O(log n)（递归栈）。

---

## 方法3：小顶堆（容量为 k）★ 推荐

### 思路

维护一个**容量为 k 的小顶堆**，堆里始终保留"目前见过的最大的 k 个数"，堆顶就是这 k 个里最小的——即第 k 大。

```
遍历每个 num：
  offer 入堆
  若堆大小 > k → poll 弹掉堆顶（当前最小）
遍历结束 → 堆顶就是第 k 大
```

**为什么用小顶堆而不是大顶堆？** 因为要"淘汰最小的、留下最大的"，小顶堆的堆顶正好是当前保留集合里最小的，一旦超过 k 个就把它踢掉。

### 代码

```java
public int findKthLargest_3(int[] nums, int k) {
    PriorityQueue<Integer> heap = new PriorityQueue<>();  // 默认小顶堆
    for (int num : nums) {
        heap.offer(num);
        if (heap.size() > k) {
            heap.poll();     // 弹掉最小的，保留最大的 k 个
        }
    }
    return heap.peek();      // 堆顶 = 第 k 大
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n log k) | 每个元素入堆/出堆 O(log k)，堆最大只有 k |
| 空间 | O(k) | 堆中最多 k 个元素 |

> **k 远小于 n 时优势明显**：O(n log k) 比全排序 O(n log n) 快，也是海量数据 / 流式数据求 Top K 的标准解法。

---

## 方法4：大顶堆（下沉 k-1 次）

### 思路

把整个数组建成**大顶堆**（堆顶是最大值），然后模拟堆排序的前 k-1 步：每轮把堆顶换到末尾并缩小堆范围，做 k-1 次后，堆顶 `nums[0]` 就是第 k 大。

```
建大顶堆 → nums[0] = 第 1 大
第1轮：swap(0, n-1)，heapify(0, n-1) → nums[0] = 第 2 大
第2轮：swap(0, n-2)，heapify(0, n-2) → nums[0] = 第 3 大
...
第 k-1 轮后 → nums[0] = 第 k 大，直接返回
```

### 代码

```java
public int findKthLargest_4(int[] nums, int k) {
    int n = nums.length;
    // 1. 建大顶堆：从最后一个非叶子节点倒着 heapify
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(nums, i, n);
    }
    // 2. 下沉 k-1 次：每轮把当前最大换到末尾并缩堆
    for (int i = n - 1; i > n - k; i--) {
        swap(nums, 0, i);
        heapify(nums, 0, i);
    }
    return nums[0];   // 第 k 大
}

private void heapify(int[] arr, int i, int heapSize) {
    int largest = i;
    int left = 2 * i + 1, right = 2 * i + 2;
    if (left < heapSize && arr[left] > arr[largest]) largest = left;
    if (right < heapSize && arr[right] > arr[largest]) largest = right;
    if (largest != i) {
        swap(arr, i, largest);
        heapify(arr, largest, heapSize);   // 继续向下调整
    }
}
```

> `heapify` 的下沉逻辑与手写堆排序 [`HeapSort.java`](../src/sort/HeapSort.java) 完全一致，区别只是这里只下沉 k-1 次而非 n-1 次。

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n log n) | 建堆 O(n) + k 次下沉 O(k log n)；k 接近 n 时约 O(n log n) |
| 空间 | O(log n) | heapify 递归栈（可改迭代做到 O(1)） |

---

## 复杂度对照表

| 方法 | 时间 | 空间 | 是否需全排序 | 适用场景 |
|------|------|------|:-----------:|---------|
| 1 Arrays.sort | O(n log n) | O(log n) | 是 | 最省事，面试保底 |
| 2 手写快排 | O(n log n) | O(log n) | 是 | 考察快排实现 |
| 3 小顶堆(容量k) | **O(n log k)** | O(k) | 否 | **Top K / 海量数据首选** |
| 4 大顶堆下沉 | O(n log n) | O(log n) | 否（只做k-1次） | 考察堆的手写 |

---

## 方法对比

```
方法1 ≈ 方法2：都是"全排序后取 nums[n-k]"，只是排序实现不同
方法3 vs 方法4：都用堆，但方向相反
   方法3 小顶堆：留最大的 k 个，淘汰小的 → 堆容量只有 k，省空间
   方法4 大顶堆：把大的逐个"抽"出来 → 堆容量是整个 n
面试推荐顺序：方法3（最优） > 方法1（保底） > 方法4（展示堆功底）
```

---

## 易错点

1. **取错下标**：升序数组中第 k 大是 `nums[n - k]`，不是 `nums[k]` 或 `nums[k-1]`
2. **方法3 用错堆方向**：求第 k **大**要用**小顶堆**（淘汰最小）；求第 k **小**才用大顶堆
3. **方法3 忘记控制容量**：每次 `offer` 后必须检查 `size() > k` 再 `poll`，否则堆会装下全部 n 个元素，退化成 O(n log n)
4. **方法4 下沉次数**：循环是 `i > n - k`，共下沉 **k-1** 次（不是 k 次）；第 k 大此时正好在 `nums[0]`
5. **heapify 的边界**：`left < heapSize` 用 `<` 而非 `<=`，避免访问已锁定/越界的元素
6. **建堆起点**：从 `n/2-1`（最后一个非叶子节点）开始倒序，叶子节点无需 heapify

---

## 记忆口诀

```
第K大，取 n-k；
小顶堆，容量 K，超了就弹堆顶，留最大的 K 个；
大顶堆，下沉 K-1，堆顶就是第 K 大。
```
