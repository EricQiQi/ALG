# LeetCode 46. 全排列

对应源码：[Hot46_permute.java](../src/s10_backTracking/Hot46_permute.java)

## 题目描述

给定一个**不含重复数字**的数组 `nums`，返回其所有全排列。

例：`nums = {1, 2, 3}` → `[[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,2,1], [3,1,2]]`，共 3! = 6 个。

## 💡 大白话解析（交换法回溯）

全排列就是**给每个位置依次挑一个还没用过的数**。交换法把这个过程做成原地操作：

想象排队拍照，`first` 是"现在轮到第几个站位"：
1. 站位 `first` 后面所有人（候选池 `[first, len)`）轮流站上来——每人和 `first` 位置**交换**一次；
2. 换上来之后，递归去安排下一个站位（`first + 1`）；
3. 拍完照（递归返回）**再换回去**，把队形还原，让下一个人接着试。

**核心不变量**：`[0, first)` 是已固定的前缀，`[first, len)` 是还没用过的候选池。每层递归就是"从候选池里挑一个固定到 first"，天然不重不漏。

```
{1,2,3}  固定 first=0（三个人轮流站 0 号位）：
 ├─ 换上 1: [1|2,3] → 递归固定 first=1：[1,2|3] [1,3|2]  → 123, 132
 ├─ 换上 2: [2|1,3] → 递归固定 first=1：[2,1|3] [2,3|1]  → 213, 231
 └─ 换上 3: [3|2,1] → 递归固定 first=1：[3,2|1] [3,1|2]  → 321, 312

| 左边 = 已固定前缀，右边 = 候选池；每条边都是一次 swap
```

## 💻 Java 代码实现

```java
public static List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    // 数组转 List，后续直接在这个 list 上原地交换
    List<Integer> output = new ArrayList<>();
    for (int i : nums) {
        output.add(i);
    }
    backTrack(res, output, nums.length, 0);
    return res;
}

public static void backTrack(List<List<Integer>> res, List<Integer> output, int len, int first) {
    // 所有位置都固定完了，收获一份完整排列
    // 必须拷贝！output 后续还会被 swap 改动，直接 add 会让所有结果指向同一个对象
    if (first == len) {
        res.add(new ArrayList<>(output));
    }
    // 依次把候选池 [first, len) 里的每个数换到 first 位置上
    for (int i = first; i < len; i++) {
        Collections.swap(output, first, i);        // 做选择：换上来固定
        backTrack(res, output, len, first + 1);    // 递归固定下一个位置
        Collections.swap(output, first, i);        // 撤销选择：换回去还原
    }
}
```

## 复杂度分析

| 项 | 复杂度 | 说明 |
| :--- | :---: | :--- |
| 时间 | O(n × n!) | 共 n! 个排列，每个收割时拷贝要 O(n) |
| 空间 | O(n) 递归栈 | 不计输出数组；output 是原地复用的 |

## ⚠️ 易错点

1. **收割时忘记拷贝**：`res.add(output)` 会让所有结果指向同一个 list，最后全部变成最后一次 swap 后的样子。必须 `res.add(new ArrayList<>(output))`。
2. **忘记第二次 swap（撤销）**：候选池被污染，后面的分支会漏解或重复。
3. **swap 法不保证字典序**：如 {1,2,3} 输出顺序是 123, 132, 213, 231, 321, 312（312 在 321 后面）。本题不要求顺序；但做"第 k 个排列"或需要按序输出的题时，应改用 **used 数组法**（每层从 0 到 n-1 依序尝试未用过的数，天然字典序）。

## 🆚 交换法 vs used 数组法

| 解法 | 核心操作 | 顺序 | 适用 |
| :--- | :--- | :--- | :--- |
| 交换法（本文） | swap 固定 / swap 还原，原地无额外结构 | 不保证字典序 | 不要求顺序的全排列 |
| used 数组法 | path 记路径 + used 标记已用 | 天然字典序 | 需要按序输出、含重复元素去重（配合排序） |

used 数组法模板：每层 `for i in [0, n)`，跳过 `used[i]`，选了就 `used[i]=true` 并加入 path，递归后撤销。两法复杂度同阶，交换法少一个数组和 path 列表。

## 🧠 记忆口诀

> **换上来 → 往下钻 → 换回去；钻满收割要拷贝，不拷白拍。**
