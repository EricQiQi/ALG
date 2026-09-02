# 二叉树的遍历

## 题目描述

给定一棵二叉树，分别实现**前序、中序、后序**三种遍历（递归版 + 迭代版）。

```
      1
     / \
    2   3
   / \
  4   5
```

| 遍历方式 | 访问顺序 | 结果 |
|---------|---------|------|
| 前序（根左右） | 1→2→4→5→3 | `[1, 2, 4, 5, 3]` |
| 中序（左根右） | 4→2→5→1→3 | `[4, 2, 5, 1, 3]` |
| 后序（左右根） | 4→5→2→3→1 | `[4, 5, 2, 3, 1]` |

---

## 核心思路

三种遍历的唯一区别：**`res.add(root.val)` 放在递归的哪个位置**。

```text
          preOrder        midOrder        postOrder
          ────────        ────────        ─────────
递归前     add(根)         ↓               ↓
递归左     ↓              add(根)          ↓
递归右     ↓              ↓              add(根)
```

一句话：**add 在哪，哪就是"根"的位置**。

---

## 代码实现

### 前序遍历：根 左 右

```java
public void preOrder(TreeNode root, List<Integer> res) {
    if (root == null) return;

    res.add(root.val);          // ① 根：先记录
    preOrder(root.left, res);   // ② 左
    preOrder(root.right, res);  // ③ 右
}
```

### 中序遍历：左 根 右

```java
public void midOrder(TreeNode root, List<Integer> res) {
    if (root == null) return;

    midOrder(root.left, res);   // ① 左
    res.add(root.val);          // ② 根：夹在中间
    midOrder(root.right, res);  // ③ 右
}
```

### 后序遍历：左 右 根

```java
public void postOrder(TreeNode root, List<Integer> res) {
    if (root == null) return;

    postOrder(root.left, res);  // ① 左
    postOrder(root.right, res); // ② 右
    res.add(root.val);          // ③ 根：最后记录
}
```

---

## 递归执行过程图解

以中序遍历为例，展开递归调用栈：

```text
midOrder(1)
  ├── midOrder(2)
  │     ├── midOrder(4)
  │     │     ├── midOrder(null) → return
  │     │     ├── add(4)          ← 最左叶子，第一个输出
  │     │     └── midOrder(null) → return
  │     ├── add(2)
  │     └── midOrder(5)
  │           ├── midOrder(null) → return
  │           ├── add(5)
  │           └── midOrder(null) → return
  ├── add(1)
  └── midOrder(3)
        ├── midOrder(null) → return
        ├── add(3)
        └── midOrder(null) → return

输出顺序：4 → 2 → 5 → 1 → 3  ✅
```

---

## 三种遍历对比

| 维度 | 前序 | 中序 | 后序 |
|------|------|------|------|
| 顺序 | 根 左 右 | 左 根 右 | 左 右 根 |
| add 位置 | 递归前 | 两次递归之间 | 递归后 |
| 第一个访问 | 根节点 | 最左叶子 | 最左叶子 |
| 典型应用 | 复制树、前缀表达式 | BST 有序输出 | 删除树、后缀表达式 |

---

## 迭代版代码实现

### 核心思路

递归本质就是栈，把递归翻译成迭代 = **手动用 Stack 模拟递归调用栈**。

| 遍历 | 出栈顺序 | 入栈技巧 |
|------|---------|----------|
| 前序 | 根 左 右 | 先压右再压左，弹出即访问 |
| 中序 | 左 根 右 | 一路压左到底，弹出时访问再转右 |
| 后序 | 左 右 根 | 反过来得到「根 右 左」，再 reverse |

---

### 前序遍历（迭代）：根 左 右

```text
栈操作：弹出即访问，先压右再压左（左先出栈）

      1              pop 1, push 3, push 2
     / \             → res: [1]
    2   3            pop 2, push 5, push 4
   / \               → res: [1, 2]
  4   5              pop 4 → res: [1, 2, 4]
                     pop 5 → res: [1, 2, 4, 5]
                     pop 3 → res: [1, 2, 4, 5, 3]
```

```java
public void preOrder_1(TreeNode root, List<Integer> res) {
    if (root == null) return;
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        res.add(node.val);              // 弹出即访问
        if (node.right != null) stack.push(node.right);  // 先压右
        if (node.left != null) stack.push(node.left);    // 再压左（左先出）
    }
}
```

---

### 中序遍历（迭代）：左 根 右

```text
核心：一路压左到底，弹出时访问，再转向右子树

      1
     / \            1. 压 1→2→4（一路向左）
    2   3           2. pop 4, res: [4], 转右(null)
   / \              3. pop 2, res: [4,2], 转右(5)
  4   5             4. 压 5, pop 5, res: [4,2,5]
                    5. pop 1, res: [4,2,5,1], 转右(3)
                    6. 压 3, pop 3, res: [4,2,5,1,3]
```

```java
public void midOrder_1(TreeNode root, List<Integer> res) {
    if (root == null) return;
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) {        // 一路压左到底
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();           // 弹出最左节点
        res.add(curr.val);            // 访问
        curr = curr.right;            // 转向右子树
    }
}
```

---

### 后序遍历（迭代）：左 右 根

```text
技巧：前序是「根左右」，把入栈顺序反过来（先压左再压右）得到「根右左」，reverse 就是「左右根」

      1
     / \            pop 1, res: [1], push左2, push右3
    2   3           pop 3, res: [1,3]
   / \              pop 2, res: [1,3,2], push左4, push右5
  4   5             pop 5, res: [1,3,2,5]
                    pop 4, res: [1,3,2,5,4]
                    reverse → [4,5,2,3,1] ✅
```

```java
public void postOrder_1(TreeNode root, List<Integer> res) {
    if (root == null) return;
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        res.add(node.val);
        if (node.left != null) stack.push(node.left);   // 先压左
        if (node.right != null) stack.push(node.right); // 再压右（右先出）
    }
    Collections.reverse(res);  // 根右左 → 左右根
}
```

---

## 递归 vs 迭代对比

| 维度 | 递归 | 迭代 |
|------|------|------|
| 代码量 | 三种只差一行 | 前序/后序相似，中序单独写法 |
| 空间 | 调用栈 O(h) | 手动栈 O(h) |
| 理解难度 | 直观 | 中序较绕，后序靠 trick |
| 实际面试 | 必须会 | 常考中序和后序 |

---

## 易错点

### 递归版
1. **三种遍历搞混 add 位置**：记住口诀——"add 在哪，哪就是根"，前序先 add，中序夹中间，后序最后 add
2. **递归终止条件忘记写**：`if (root == null) return;` 是递归出口，漏掉会 NPE
3. **中序遍历误写成前序**：最常见的手误，`res.add` 写到了 `midOrder(root.left)` 前面

### 迭代版
4. **前序入栈顺序搞反**：要「根左右」，必须**先压右再压左**，这样左才先出栈
5. **中序 `while (curr != null || !stack.isEmpty())` 条件写错**：两个条件缺一不可，`curr != null` 处理还有右子树的情况
6. **后序忘记 reverse**：迭代后序靠反转得到正确顺序，漏掉就变成「根右左」

---

## 记忆口诀

```
递归版：
三种遍历一个模，递归框架不用改，
前序先加再递归，中序左加右，
后序递归完再加，add 在哪哪是根。

迭代版：
前序弹就加，右左入栈等弹出；
中序压到底，弹出加完转右边；
后序反过来，左右入栈再反转。
```
