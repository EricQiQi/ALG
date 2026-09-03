# Queue、Deque、Stack

## 一、三者本质

```
Queue（队列）：先进先出（FIFO）
  入口 → [1, 2, 3] → 出口
  只能从一端进、另一端出

Stack（栈）：后进先出（LIFO）
  顶部 ↑ [1, 2, 3]
  同一端进出（从顶部压入、从顶部弹出）

Deque（双端队列）：两端都能进出
  ← [1, 2, 3] →
  左端右端都能插入和删除
```

> **Deque 是 Queue 和 Stack 的超集** — 它能干两者的所有事。

---

## 二、方法速查表

### Queue 接口（实现类：LinkedList）

| 操作 | 抛异常版 | 返回特殊值版 | 说明 |
|------|---------|-------------|------|
| 入队 | `add(e)` | `offer(e)` → boolean | 尾部插入 |
| 出队 | `remove()` | `poll()` → null | 头部删除 |
| 查看队头 | `element()` | `peek()` → null | 只看不动 |

### Stack（继承自 Vector）

| 操作 | 方法 | 说明 |
|------|------|------|
| 压栈 | `push(e)` | 顶部插入 |
| 弹栈 | `pop()` | 顶部删除 |
| 查看栈顶 | `peek()` | 只看不动 |

### Deque 接口（推荐实现类：ArrayDeque）

| 操作 | 头部 | 尾部 |
|------|------|------|
| 插入 | `offerFirst(e)` / `push(e)` | `offerLast(e)` / `offer(e)` / `add(e)` |
| 删除 | `pollFirst()` / `poll()` / `pop()` | `pollLast()` |
| 查看 | `peekFirst()` / `peek()` | `peekLast()` |

> **当 Stack 用**：`push / pop / peek`（操作头部）
> **当 Queue 用**：`offer / poll / peek`（尾部进、头部出，等价于 `offerLast / pollFirst / peekFirst`）

---

## 三、为什么推荐用 Deque 代替 Stack？

### Stack 的问题

```java
// Stack 继承自 Vector → 每个方法都加了 synchronized
Stack<Integer> stack = new Stack<>();
```

| 问题 | 说明 |
|------|------|
| **性能差** | 继承 Vector，所有操作都加了不必要的同步锁 |
| **API 混乱** | 继承了 Vector 的 `add(int index, e)`、`remove(int index)` 等方法，可以在中间随意插入删除，破坏了栈的语义 |
| **过时设计** | JDK 1.0 就有的老类，官方注释自己都写着"推荐用 Deque 代替" |

### Deque 的优势

```java
// 当栈用
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);
stack.pop();

// 当队列用
Deque<Integer> queue = new ArrayDeque<>();
queue.offerLast(1);  // 入队
queue.pollFirst();   // 出队
```

| 优势 | 说明 |
|------|------|
| **更快** | ArrayDeque 基于数组，没有同步锁开销 |
| **更灵活** | 同一个类既能当栈又能当队列 |
| **官方推荐** | Java 官方文档明确建议用 ArrayDeque 替代 Stack |

---

## 四、本项目中的实际用法

| 数据结构 | 题目 | 用途 |
|---------|------|------|
| `Queue<TreeNode>` | 102 层序遍历、101 对称二叉树 | BFS 逐层遍历 |
| `Stack<TreeNode>` | 94 中序遍历、108 有序数组转BST | 迭代法模拟递归 |
| `Deque<Integer>` | 11 滑动窗口最大值 | 单调队列 |
| `PriorityQueue<ListNode>` | 23 合并K个有序链表 | 优先队列选最小 |

> 注：项目中 94、108 用了 `Stack`，实际可以替换为 `Deque` 更规范。

---

## 五、选型速记

```
需要先进先出？
  → Queue<TreeNode> queue = new LinkedList<>();
  → 或 Deque<TreeNode> queue = new ArrayDeque<>();  ← 更推荐

需要后进先出？
  → Deque<Integer> stack = new ArrayDeque<>();      ← 推荐
  → 不要用 new Stack<>()

需要两端操作？（如单调队列、滑动窗口）
  → Deque<Integer> deque = new ArrayDeque<>();

需要按优先级出队？
  → PriorityQueue<ListNode> pq = new PriorityQueue<>();
```
