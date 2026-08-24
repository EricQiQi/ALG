# 🏃‍♂️ 无重复字符的最长子串：从老实人到终极战神版

无重复字符的最长子串的核心逻辑是：**“右探子疯狂向前冲，一旦撞车，左探子立刻‘断尾求生’。”**

这道题在双指针/滑动窗口里，其实就是一个**“拉橡皮筋”**或者**“贪吃蛇”**的游戏：右指针（`right`）是蛇头，负责往前吃新字符拉长队伍；左指针（`left`）是蛇尾，负责在出事的时候缩减长度。

下面为你梳理力扣（LeetCode）这道经典高频题的**三重境界**，带你从最基础的官方解法，一路进化到 100% 胜率的终极战神版。

---

## 🧱 第一重：官方 HashSet（逐个赶人老实人版）

这是最符合人类直觉的解法。窗口就像是一个奶茶店的排队队伍（`HashSet`），里面不能有名字重复的人。

### 🎮 大白话机制
1. **右探子 `right`** 带了一个新顾客（比如 `'a'`）来排队。
2. 他抬头一看，队伍（`HashSet`）里**已经有一个 `'a'` 了**！发生撞车。
3. 因为 `HashSet` 脑子不太好使，它**不知道**原来那个 `'a'` 具体排在队伍的第几个位置。
4. 所以，**左探子 `left` 只能苦哈哈地从队伍最前面开始，把人一个一个赶走**（`set.remove()`，然后 `left++`），直到把那个重名的 `'a'` 赶走为止。

### 💻 Java 代码实现
```java
import java.util.HashSet;

public class SolutionHashSet {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;

        HashSet<Character> set = new HashSet<>();
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);

            // 遇到重复的了！左探子开始悲催地“一个一个往外赶人”
            while (set.contains(ch)) {
                set.remove(s.charAt(left));
                left++; // 老老实实一步一步挪
            }

            set.add(ch); // 赶干净了，新人才可以入队
            maxLen = Math.max(maxLen, right - left + 1); // 实时结算最高纪录
        }
        return maxLen;
    }
}
```

---

## 🚀 第二重：HashMap 优化（一键瞬移进阶版）

官方解法太老实了，既然撞车了，我们能不能让左指针不走回头路，**直接“瞬移”** 过去？

### 🎮 大白话机制
我们把脑子换成 `HashMap`（账本），专门记录：“我已经吃过哪些字符了，它们在**哪个具体位置**”。
1. 发现 `'c'` 重复了，翻开账本（Map），账本直接说：“旧的 `'c'` 在索引 2 呢！”
2. `left` 眼睛都不眨，**直接全速瞬移**到 `2 + 1 = 3` 的位置。中间排队的人连理都不用理，实现 $O(1)$ 的直接跳跃。
3. **关键细节**：左指针更新必须用 `Math.max(left, 过去位置 + 1)`。因为已经被切掉的死蛇肉，绝不回头去吃（防止左指针后退导致重复字符重新入窗）。

### 💻 Java 代码实现
```java
import java.util.HashMap;

public class SolutionHashMap {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        
        // 脑子（账本）：记录字符最后一次出现的位置
        HashMap<Character, Integer> map = new HashMap<>();
        int left = 0;
        int maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            
            // 撞车检查：如果吃过这个字符
            if (map.containsKey(ch)) {
                // 蛇尾立刻向右瞬移，但通过 max 确保绝不走回头路
                left = Math.max(left, map.get(ch) + 1);
            }
            
            map.put(ch, right); // 无论撞没撞，都更新/记录最新位置
            maxLen = Math.max(maxLen, right - left + 1); // 实时结算
        }
        return maxLen;
    }
}
```

---

## 🏆 第三重：int 数组（击败 100% 终极战神版）

这是把 `HashMap` 优化到极致的硬件级解法。不仅继承了瞬移的优点，还利用了底层硬件逻辑，把运行速度直接拉满到 **1ms**。

### 🎮 大白话机制
1. **用数组代替哈希表**：输入字符在底层就是 **ASCII 码（数字 0 - 127）**。直接开辟一个长度为 128 的 `int[] index` 数组，用字符的 ASCII 码当**下标抽屉**。找位置的速度变成了绝对的物理 $O(1)$。
2. **`right + 1` 的免 `if` 神仙操作**：Java 中 `int` 数组默认值全都是 `0`。
    * 如果存入 `right + 1`，那么当字符从未出现过时，查出来的就是 `0`。
    * 执行 `left = Math.max(left, 0)` 时，结果永远是 `left` 自己（保持原样不动）。
    * 这直接**用数学合并了 `if` 判断**！既消除了冗余代码，又避免了 CPU 的分支预测损耗，性能极其恐怖。

### 💻 Java 代码实现
```java
public class SolutionWarrior {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;

        // 1. 终极账本：记录每个字符最后一次出现的位置 + 1 （默认全是 0）
        int[] index = new int[128]; 
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            
            // 2. 隐藏的 if：若未出现过，index[ch]为0，left保持不变；
            //              若出现过，index[ch]已经是“上一次位置+1”，直接一步瞬移到位！
            left = Math.max(left, index[ch]);
            
            // 3. 实时结算橡皮筋拉伸的最长距离
            maxLen = Math.max(maxLen, right - left + 1);
            
            // 4. 登记当前字符的最新位置（故意 +1，为了让默认值 0 完美代表“没出现过”）
            index[ch] = right + 1; 
        }

        return maxLen;
    }
}
```

---

## 📊 三大解法终极对比表

| 维度 | 🧱 官方 `HashSet`（逐个移除） | 🚀 优化 `HashMap`（一键瞬移） | 🏆 `int` 数组（战神终极版） |
| :--- | :--- | :--- | :--- |
| **理论时间复杂度** | **$O(N)$** | **$O(N)$** | **$O(N)$** |
| **最坏情况指针移动** | 左右指针各走 $N$ 步，共 **$2N$ 次** | 右指针 $N$ 步，左指针**最多跳跃 $N$ 次** | 右指针 $N$ 步，左指针**最高效硬跳跃** |
| **空间复杂度** | $O(\min(N, M))$ （$M$ 为字符集大小） | $O(\min(N, M))$ | **$O(M)$** （固定 128 大小，纯 $O(1)$） |
| **哈希底层开销** | 频繁调用 `add()` / `remove()` | 频繁调用 `put()`，伴随对象装箱 | **没有任何哈希计算，直接按内存地址查** |
| **底层分支预测** | 有 `while` 循环，存在分支开销 | 有 `if` 判断，存在分支开销 | **无任何 `if/while` 分支，硬件级纯计算** |
| **实际运行效率** | 较慢 | 快 | **极快（1ms，击败 100%）** |

---

## 🗣️ 面试官最想听到的“高情商”汇报词

> “这三种方法在理论上的时间复杂度都是 $O(N)$。但我提交的是 **`int` 数组代替哈希表的终极优化版**。
>
> 首先，由于题目处理的是标准字符，我直接用长度 128 的 `int` 数组作映射，免去了 `HashMap` 自动装箱和哈希冲突的开销。
>
> 其次，在撞车更新左指针时，我通过存储 `right + 1` 的小技巧，让数组默认值 `0` 完美等价于‘未出现过’。再配合 `Math.max()` 压平了代码中的 `if-else` 分支。这样在底层可以**完全避免 CPU 的分支预测失败损耗**，实现了零多余判断、零空间浪费、纯硬件级的 $O(1)$ 跳跃，工程运行效率达到了极致。”
