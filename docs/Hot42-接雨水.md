# LeetCode 42. 接雨水

对应源码：[Hot42_trap.java](../src/s2_doubleIndex/Hot42_trap.java)

## 题目描述

给定 `n` 个非负整数，表示高度图中每根宽度为 1 的柱子，求下雨之后一共能接多少雨水。

```
[0,1,0,2,1,0,1,3,2,1,2,1] → 6
[4,2,0,3,2,4,3]           → 9
[1,2,3,4,5]               → 0   （单调上升，一滴水都留不住）
```

`[0,1,0,2,1,0,1,3,2,1,2,1]` 的积水情况（`█` 是柱子，`~` 是水）：

```
h=3   . . . . . . . █ . . . .
h=2   . . . █ ~ ~ ~ █ █ ~ █ .
h=1   . █ ~ █ █ ~ █ █ █ █ █ █
      0 1 0 2 1 0 1 3 2 1 2 1
      水：0 0 1 0 1 2 1 0 0 1 0 0  → 合计 6
```

## 💡 大白话解析

**每一格能装多少水，由它头顶两道墙里矮的那道决定**（短板决定水位）。

站在下标 `i` 这格上：

- 左边（含自己）最高的墙 `leftMax`
- 右边（含自己）最高的墙 `rightMax`
- 水面只能到较矮的那道，所以这格的水 = `min(leftMax, rightMax) - height[i]`

难点全在「怎么高效拿到每个位置的 `leftMax` 和 `rightMax`」。每格都往两边各扫一遍是 $O(n^2)$，两个解法各用一招绕开：

| 解法 | 这一招是什么 |
| :--- | :--- |
| 双指针 | 不等到两边都探明，**哪边的记录矮，哪边的短板就已经定了**，当场结算 |
| 动态规划 | 先把每一格的左右最高墙各扫一遍存进数组，最后查表算 |

---

## 解法一：双指针（`trap_1`）

### 核心思想

`left` 从最左、`right` 从最右同时往中间挤，各自记着「我这一侧见过的最高墙」`leftmax` / `rightmax`。

疑问来了：`left` 还没看过右边的全部柱子，凭什么敢直接算它头顶的水？

> 拿两个 max 比最容易想通：只要 `leftmax < rightmax`，`left` 的右边就明明白白站着一根比它高的。后面还没探的部分只会更高、不可能更低，水位就被 `leftmax` 锁死 → 当场算 `leftmax - height[left]`，`left` 往里走一步。

源码里比的是 `height[left] < height[right]`（当前柱高，不是 max），结论一样成立：`leftmax` 那根最高的墙只有两种来路。

- 它就是 `left` 脚下这根 → `height[right]` 比它高，右边现成有兜底
- 它在更左边的某个位置 `k` → `left` 能跨过 `k`，说明当时走的正是算左分支，也就是 `height[k]` 比当时右边那根矮；而 `right` 只会往左缩、不会往右退，那根比 `k` 高的墙如今依旧在 `left` 的右边

两种情形都保证：`left` 右边一定有不低于 `leftmax` 的墙兜底，所以算左边是安全的。反过来 `height[left] >= height[right]` 时，左边有高山顶着，算右边。

一句话：**哪边矮就算哪边、动哪边。**

用 `[0,1,0,2,1,0,1,3,2,1,2,1]` 里 `left=5`、`right=7` 这一轮看清楚：

```
leftmax  = 2   （下标 0~5 里最高是下标 3 那根）
rightmax = 3   （下标 7~11 里最高就是 right 站的这根）

height[left]=0 < height[right]=3 → 左边矮
⇒ 下标 5 头顶的水位被 leftmax=2 钉死，右边那根 3 保证它不会往外漏 → res += 2 - 0 = 2
⇒ 左边已经不需要再等右边的消息，left 进到 6
```

同一个数组完整跑下来共 10 轮，摘几个关键节点（每轮都是「先更 max、再判、再算」）：

```
l=2(0)  r=10(2)  leftmax=1 rightmax=2  → 左矮，算左 += 1-0 = 1   res=1
l=3(2)  r=9(1)   leftmax=2 rightmax=2  → 右矮，算右 += 2-1 = 1   res=2
l=3(2)  r=7(3)   leftmax=2 rightmax=3  → 左矮，算左 += 2-2 = 0   （自己就是最高，接不住水）
l=5(0)  r=7(3)   leftmax=2 rightmax=3  → 左矮，算左 += 2-0 = 2   res=5
l=6(1)  r=7(3)   leftmax=2 rightmax=3  → 左矮，算左 += 2-1 = 1   res=6
```

### 代码实现

```java
public static int trap_1(int[] height) {
    if (height == null || height.length == 0) return 0;

    // 左边见过的最高的，右边见过的最高的板子
    int leftmax = 0, rightmax = 0;
    int left = 0, right = height.length - 1;
    int res = 0;

    while (left < right) {
        // Math.max容易搞不清楚!!!：max 必须包含当前这根柱子
        leftmax = Math.max(leftmax, height[left]);
        rightmax = Math.max(rightmax, height[right]);

        if (height[left] < height[right]) {
            // 右边一定有更高的板子兜底 → 算左边
            res += leftmax - height[left];
            left++;
        } else {
            // 左边一定有更高的板子兜底 → 算右边
            res += rightmax - height[right];
            right--;
        }
    }
    return res;
}
```

---

## 解法二：动态规划（`trap_2`）

### 核心思想

既然每格都要问「我左边最高是谁、右边最高是谁」，那就**开工前把两本账本一次记好**：

- `left_max[i]`：`i` 及其左边的最高墙
- `right_max[i]`：`i` 及其右边的最高墙

记账的递推就是「跟邻居比大小」：

```
left_max[i]  = max(left_max[i-1],  height[i])   ← 从左往右填
right_max[i] = max(right_max[i+1], height[i])   ← 从右往左填
```

账本记完，再遍历一次每格查表：`min(left_max[i], right_max[i]) - height[i]`。

`[0,1,0,2,1,0,1,3,2,1,2,1]` 的两本账本（已用代码打印核对）：

```
下标       0  1  2  3  4  5  6  7  8  9  10  11
height     0  1  0  2  1  0  1  3  2  1   2   1
left_max   0  1  1  2  2  2  2  3  3  3   3   3
right_max  3  3  3  3  3  3  3  3  2  2   2   1
取水       0  0  1  0  1  2  1  0  0  1   0   0   → 合计 6
```

看 `right_max` 一行：下标 8 往后最高只有 2，所以 8~11 这几格的水位全被右边这道矮墙压着；下标 0~6 的 `right_max` 都是 3，水位由左边的 `left_max` 说话——这正是双指针想省掉的那张表。

### 代码实现

```java
public static int trap_2(int[] height) {
    if (height == null || height.length == 0) return 0;
    int n = height.length;
    int[] left_max = new int[n];
    int[] right_max = new int[n];

    // 注意赋值：两端先各钉死自己
    left_max[0] = height[0];
    right_max[n - 1] = height[n - 1];

    // 从左往右记左边的账
    for (int i = 1; i < n; i++) {
        left_max[i] = Math.max(left_max[i - 1], height[i]);
    }

    // 从右往左记右边的账，注意从 n-2 开始
    for (int j = n - 2; j >= 0; j--) {
        right_max[j] = Math.max(right_max[j + 1], height[j]);
    }

    // 查表算水
    int res = 0;
    for (int i = 0; i < n; i++) {
        res += Math.min(left_max[i], right_max[i]) - height[i];
    }
    return res;
}
```

---

## 复杂度分析

| | 解法一 双指针 | 解法二 动态规划 |
| :--- | :---: | :---: |
| 时间 | $O(n)$，数组刚好扫一遍 | $O(n)$，数组扫了三遍 |
| 空间 | $O(1)$，只有几个变量 | $O(n)$，两个记账数组 |

双指针能省掉那两张表，靠的是「哪边矮就算哪边」——矮的那侧短板已经锁定，另一侧的真实最高只会更高，用不着提前查。

---

## ⚠️ 易错点

### 1. `Math.max` 更新必须在算水之前

源码里那句 `// Math.max容易搞不清楚!!!` 就是指这个：`leftmax` 的定义是「`left` **及其左边**见过的最高」，必须先 `max` 再算。

写成「先算水、后更新 max」，`res` 用的就是不含当前柱子的旧值，当前柱子一旦创新高就减成负数。实测 `[1,1]`：正确答案 0，这样写得到 **-1**。

### 2. `leftmax` / `rightmax` 不能写成直接赋值

```java
leftmax = Math.max(leftmax, height[left]);   // ✓ 历史最高
leftmax = height[left];                      // ✗ 只记住当前这根
```

漏掉 `Math.max` 后 `leftmax` 退化成当前柱高，左边的高墙记录全丢。实测 `[1,0,1]`：正确答案 1，这样写得到 **0**。

### 3. 条件和「算哪边」必须配套

`height[left] < height[right]` 说的是**右边有更高的兜底**，所以这个分支要算**左边** `leftmax - height[left]`，并且 `left++`。写反成算右边，等于拿一个还没被验证过的短板去算水。实测 `[1,0,0]`：正确答案 0，这样写得到 **1**。

口诀：**矮边算水，矮边移动。**

### 4. 判断条件用 `height[left] < height[right]` 还是 `leftmax < rightmax`？

两种写法都对（已枚举长度 ≤ 7、高度 0~4 的全部数组 + 20 万随机用例与暴力解对拍，结果完全一致），区别只是「比当前柱高」还是「比历史最高」。

- 比 `height`：`left` 右边至少有一根（就是 `height[right]`）比它高
- 比 `leftmax`：`left` 右边至少有一根（就是探到的 `rightmax`）比左边的最高记录高，结论更强、更好解释

关键是**一次只能改一种**：条件是 `height[left] < height[right]` 就配 `left++`，条件是 `leftmax < rightmax` 也配 `left++`，但把条件和算的水量张冠李戴（如用 `leftmax` 判断、却算 `rightmax - height[right]`）必错。

### 5. 等号落在哪个分支不影响结果

`<` 写成 `<=` 只是让相等时先动哪一边的顺序变了，水量不变。实测：所有枚举用例下两种写法结果一致。

### 6. 动态规划的两端初始化与起始下标

- `left_max[0] = height[0]`、`right_max[n-1] = height[n-1]` 不能漏。漏了左边那行，`left_max[0]` 就是默认的 0：实测 `[1,0]` 正确答案 0，这样写得到 **-1**；`[3,0,0,3]` 正确 6，得到 **-3**。（首尾格子本身接不住水，但它们是后面所有格子的「左墙」，记错了整列全部偏小）
- 反向循环必须从 `n-2` 开始。从 `n-1` 起会去读 `right_max[n]`，实测直接抛 `ArrayIndexOutOfBoundsException: 12`。

---

## 🔗 相关题目

- [盛最多水的容器](Hot11-盛最多水的容器.md) —— 同样是两头往中间挤，但那题算的是「两根柱子之间的面积」，短板移动
- [除自身以外数组的乘积](Hot238-除自身以外数组的乘积.md) —— 左右两本账（前缀 + 后缀）再合并，和动态规划解法同一个骨架
- [柱状图中最大的矩形](Hot84-柱状图中最大的矩形.md) —— 接雨水的第三种解法（单调栈按层结算）在这里，思路是横着一层一层算水

## 🧠 记忆口诀

```
每格水量看矮墙，左右最高取小的
DP 先记两本账，三趟扫完查表算
双指针只留变量，哪边矮来哪边算
矮边算完矮边动，另一侧必有大山顶
```
