# 前 K 个高频元素（Top K Frequent Elements）

## 题目描述

给定一个整数数组 `nums` 和一个整数 `k`，请返回其中**出现频率前 k 高**的元素。可以按**任意顺序**返回答案。

```
输入：nums = [1,1,1,2,2,3], k = 2
输出：[1,2]
解释：1 出现 3 次，2 出现 2 次，3 出现 1 次，前 2 高频是 [1,2]

输入：nums = [1], k = 1
输出：[1]
```

> 进阶：算法的时间复杂度必须优于 O(n log n)，其中 n 是数组大小。

源码位置：[`../src/s13_dump/Hot347_topKFrequent.java`](../src/s13_dump/Hot347_topKFrequent.java)

---

## 核心思路

两步走：**先统计频率，再挑出频率最大的 k 个**。第一步两种方法完全一样（HashMap 记账），差别只在第二步「怎么选 Top K」：

```
① HashMap 记账：value → 出现次数
② 从 map 里选 Top K：
   方法1 小顶堆（容量 k，按频率比较）  → O(n log k)   ✅ 已实现 topKFrequent_1
   方法2 桶排序（下标当频率）          → O(n)         ✅ 已实现 topKFrequent_2，满足进阶
   （方法3 全排序：按频率降序取前 k     → O(n log n)，不满足进阶，本文不实现）
```

和 [`Hot215 数组中的第 K 个最大元素`](Hot215-数组中的第K个最大元素.md) 是**同一个 Top K 模板**，区别只在于：215 直接对原数组取值比较，347 要先用 HashMap 把「元素 → 频率」统计出来，再对**频率**做 Top K。

---

## 方法1：小顶堆（容量为 k）

源码：[`topKFrequent_1`](../src/s13_dump/Hot347_topKFrequent.java)

### 思路

维护一个**容量为 k 的小顶堆**，堆里按**频率**排序（频率小的在堆顶）。遍历频率表，每个元素入堆后若超过 k 个就弹掉堆顶——即淘汰当前频率最小的，最终堆里留下的就是频率最高的 k 个。

```
遍历 map 的每个 (元素, 频率)：
  offer 入堆（比较器按频率 a[1]-b[1]）
  若堆大小 > k → poll 弹掉堆顶（频率最小的那个）
遍历结束 → 堆里就是前 k 高频元素，逐个 poll 取出
```

**为什么用小顶堆？** 要「留下频率最大的 k 个」，就得随时能踢掉当前保留集合里频率最小的，小顶堆的堆顶正好是它。

### 代码

```java
public int[] topKFrequent_1(int[] nums, int k) {
    // 1. 统计频率
    Map<Integer, Integer> map = new HashMap<>();
    for (int i : nums) {
        map.put(i, map.getOrDefault(i, 0) + 1);
    }

    // 2. 小顶堆按频率排序，只保留频率最大的 k 个
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        heap.offer(new int[]{entry.getKey(), entry.getValue()});  // {元素, 频率}
        if (heap.size() > k) {
            heap.poll();   // 弹掉频率最小的
        }
    }

    // 3. 收割堆里的 k 个元素
    int[] res = new int[k];
    for (int i = 0; i < k; i++) {
        res[i] = heap.poll()[0];
    }
    return res;
}
```

> 堆里存的是 `int[]{元素, 频率}` 二元组，比较器只看 `[1]`（频率）。用二元组而非只存元素，是为了收割时能直接拿到元素值 `[0]`。

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | O(n log k) | 统计频率 O(n)；最多 n 个不同元素入堆，每次 O(log k) |
| 空间 | O(n) | HashMap 存全部不同元素，堆最多 k 个 |

---

## 方法2：桶排序（下标当频率）

源码：[`topKFrequent_2`](../src/s13_dump/Hot347_topKFrequent.java)

### 思路

频率的取值范围一定是 `[0, n]`（一个元素最多出现 n 次）。于是开一个长度为 `n+1` 的桶数组，**用下标当频率**，把元素丢进它频率对应的桶里；最后从高频桶（下标大）往低频桶扫，收集到的前 k 个就是答案。整个过程没有比较排序，所以是线性的。

```
频率 → 桶：
  bucket[3] = [1]      （元素 1 出现 3 次）
  bucket[2] = [2]      （元素 2 出现 2 次）
  bucket[1] = [3]      （元素 3 出现 1 次）
从右往左扫（高频 → 低频），凑够 k 个即停：
  k=2 → 先拿 bucket[3] 的 1，再拿 bucket[2] 的 2 → [1,2]
```

### 代码

```java
public int[] topKFrequent_2(int[] nums, int k) {
    // 1. 统计频率
    Map<Integer, Integer> map = new HashMap<>();
    for (int i : nums) {
        map.put(i, map.getOrDefault(i, 0) + 1);
    }

    // 2. 建桶：下标 = 频率，桶里装该频率对应的所有元素（频率范围 [0, n]，故开 n+1 长）
    List<Integer>[] bucket = new List[nums.length + 1];
    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        int num = entry.getKey();
        int count = entry.getValue();
        if (bucket[count] == null) {
            bucket[count] = new ArrayList<>();
        }
        bucket[count].add(num);
    }

    // 3. 从高频桶往低频桶收集，凑够 k 个即停
    int[] res = new int[k];
    int index = 0;
    for (int i = bucket.length - 1; i >= 0 && index < k; i--) {
        if (bucket[i] != null) {
            for (int num : bucket[i]) {
                res[index++] = num;
                if (index == k) break;   // 同一桶内可能超过 k，装满就停
            }
        }
    }
    return res;
}
```

### 复杂度

| 类型 | 复杂度 | 说明 |
|------|--------|------|
| 时间 | **O(n)** | 统计频率、建桶、扫桶各遍历一遍，无比较排序 |
| 空间 | O(n) | HashMap + 桶数组都是 O(n) |

> 桶排序是**唯一满足进阶要求（优于 O(n log n)）**的解法，用空间换时间。

---

## 复杂度对照表

| 方法 | 时间 | 空间 | 是否比较排序 | 适用场景 |
|------|------|------|:-----------:|---------|
| 1 小顶堆(容量k) | O(n log k) | O(n) | 是 | **通用首选**，海量/流式数据 |
| 2 桶排序 | **O(n)** | O(n) | 否 | 追求最优时间、满足进阶 |

---

## 方法对比

```
共同点：第一步都是 HashMap 统计「元素 → 频率」
差别只在第二步怎么选 Top K：
   方法1 小顶堆：靠比较淘汰，O(n log k)，k 远小于 n 时很划算
   方法2 桶排序：靠下标定位不比较，O(n)，用 n+1 的桶空间换掉 log 因子
面试推荐：先说堆（通用），再补桶排序（进阶最优）
```

---

## 易错点

1. **比较器方向**（方法1）：求前 k **高频**用**小顶堆**（`a[1] - b[1]`，淘汰频率最小的）；写成大顶堆会把高频的先弹掉。
2. **忘记控制容量**（方法1）：每次 `offer` 后必须检查 `size() > k` 再 `poll`，否则堆装下全部元素，退化成 O(n log n)。
3. **堆里存二元组**（方法1）：只存元素值就没法按频率比较，必须存 `{元素, 频率}` 并让比较器看频率列。
4. **收割下标**（方法1）：取元素用 `heap.poll()[0]`（第 0 列是元素），别错拿成 `[1]`（那是频率）。
5. **桶数组长度**（方法2）：频率范围是 `[0, n]`，桶要开 `n+1` 长，否则元素全部出现时（频率 = n）会数组越界。
6. **桶可能装多个**（方法2）：同一频率可能对应多个元素，桶必须是 `List` 而非单个值；收集时同一桶内也要边装边判 `index == k` 提前停。
7. **收集方向**（方法2）：从高频往低频扫是 `i` 从 `bucket.length-1` 递减，别写反成升序。
8. **返回顺序**：两种方法返回顺序都不保证，但题目允许任意顺序，无需再排。

---

## 记忆口诀

```
先 HashMap 数频率，再挑 Top K；
小顶堆，按频率比，容量 K，超了弹堆顶（最小的），存 {元素,频率} 取 [0]；
桶排序，下标当频率，桶开 n+1，从高频桶往低频桶扫，凑够 K 就停。
```
