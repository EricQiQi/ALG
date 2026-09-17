# LeetCode 208. 实现 Trie (前缀树)

对应源码：[Hot208_Trie.java](../src/s9_graph/Hot208_Trie.java)

## 题目描述

实现一个 Trie（前缀树），包含 `insert`（插入单词）、`search`（查找完整单词）和 `startsWith`（查找前缀）三个操作。

- `insert("apple")`
- `search("apple")` → `true`（完整单词）
- `search("app")` → `false`（只是前缀）
- `startsWith("app")` → `true`（前缀存在）

## 💡 大白话解析

**Trie 就是一棵"逐字符往下钻"的多叉树**：每个节点有 26 个槽位（对应 a~z），插入单词就是沿字符一层层往下铺路。

两个关键设计：

1. **`children[26]`**：下标 = `字符 - 'a'`，为 `null` 表示这个方向没有路。
2. **`isEnd` 标记**：标记"有单词恰好在这个节点结尾"。它是**区分"完整单词"和"只是前缀"**的唯一依据。

**为什么需要 isEnd？** 插入 `apple` 后，路径 `a→p→p→l→e` 天然存在，所以 `startsWith("app")` 是 true。但 `search("app")` 要求 `app` 本身是完整单词——只有当 `p` 节点上的 `isEnd == true` 才算（比如再插入一个 `"app"`）。**路径存在 ≠ 单词存在**，isEnd 就是补上这一半信息。

## 图解

插入 `apple`、`app` 两个单词后的 Trie（同一批节点被两条路径共享）：

```
root
 └─ a ── p ── p ── l ── e
              ↑isEnd     ↑isEnd
              (app)      (apple)

search("app")     → 钻到第 2 个 p，isEnd=true  ✓
search("appl")    → 钻到 l，isEnd=false        ✗（只是路径中转站）
startsWith("app") → 只要钻得到就行             ✓
startsWith("apples") → 钻过 e 后槽位为 null     ✗（断路）
```

**一句话总纲：三大操作共用同一条主循环（从根逐字符往下钻），分歧只在钻到之后——insert 盖章、search 看章、startsWith 不看章。**

## 💻 Java 代码实现

```java
class Trie {
    /** 26 个孩子槽位，下标 = 字符 - 'a'，为 null 表示这个方向没有路 */
    Trie[] children;
    /** 标记是否有单词恰好在这个节点结尾 */
    boolean isEnd;

    Trie() {
        this.children = new Trie[26];
        this.isEnd = false;
    }

    /** 插入：逐字符往下钻，没路就铺路，钻到最后盖上 isEnd 章 */
    void insert(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.children[ch - 'a'] == null) {
                node.children[ch - 'a'] = new Trie();   // 铺路
            }
            node = node.children[ch - 'a'];             // 钻进去
        }
        node.isEnd = true;                              // 盖章
    }

    /** 查完整单词：钻得到 且 isEnd == true */
    boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    /** 查前缀：钻得到就行，不看 isEnd */
    boolean startsWith(String pre) {
        return searchPrefix(pre) != null;
    }

    /** 公共子过程：沿 word 逐字符往下钻，中途断路返回 null */
    private Trie searchPrefix(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.children[ch - 'a'] == null) {
                return null;                            // 断路
            }
            node = node.children[ch - 'a'];
        }
        return node;
    }
}
```

## 复杂度分析

| 操作 | 时间复杂度 | 空间复杂度 |
| :--- | :---: | :---: |
| insert | O(L) | O(L) 最坏新建 L 个节点 |
| search / startsWith | O(L) | O(1) |

L 为单词长度。**所有操作都和字典里有多少个单词无关**——这是 Trie 相比 `HashSet.contains`（O(1) 均摊但和哈希计算、字符串长度有关）和暴力前缀匹配的核心优势。

## 🧠 什么时候用 Trie？

| 场景 | 为什么用 Trie |
| :--- | :--- |
| 大量字符串的**前缀匹配**（搜索框联想） | `startsWith` 只要 O(前缀长度)，HashMap 做不到前缀查询 |
| 按前缀**分组统计**（Hot211、Hot212 单词搜索） | 搜索时携带 Trie 节点状态，走一步缩一步 |
| 字符集小且固定（a-z、0-9） | children 数组下标直达，O(1) 定位孩子 |

如果只需要"单词是否存在"，HashMap 就够了，不必上 Trie；**Trie 的价值在前缀**。

## ⚠️ 易错点

1. **`node = node.children[ch-'a']` 忘了写在 if 外面**：不管槽位是不是新建的，都要钻进去，这行必须在 if 块外面。
2. **search 忘了判 `node.isEnd`**：只判了路径存在，把"apple 存在"误判成"appl 也存在"。
3. **insert 忘了置 `isEnd = true`**：所有 search 全部返回 false。
4. **本实现 API 命名为 `startWith`**，LeetCode 标准接口是 `startsWith`，刷题提交时注意改名。

## 🧠 记忆口诀

> **insert 铺路盖章，search 看章，startsWith 不看章；断路即 false，章在 isEnd。**
