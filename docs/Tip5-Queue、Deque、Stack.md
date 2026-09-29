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

## 四、PriorityQueue（优先队列）

### 本质

```
普通 Queue：先进先出（FIFO）—— 谁先来谁先出
PriorityQueue：按优先级出队 —— 谁最小（默认）谁先出

底层是二叉堆（默认小顶堆），堆顶永远是当前最小元素
```

> **它不是 FIFO，而是「每次 poll 都取出当前优先级最高的」**。底层用数组实现的完全二叉堆，不保证遍历（`for`/迭代器）有序，只有不断 `poll` 才是有序的。

### 方法速查表

| 操作 | 抛异常版 | 返回特殊值版 | 时间复杂度 |
|------|---------|-------------|-----------|
| 入队 | `add(e)` | `offer(e)` → boolean | O(log n) |
| 出队（取最小） | `remove()` | `poll()` → null | O(log n) |
| 查看堆顶 | `element()` | `peek()` → null | O(1) |
| 是否为空 | — | `isEmpty()` → boolean | O(1) |
| 大小 | — | `size()` → int | O(1) |

> 方法名和 Queue 完全一致（PriorityQueue 实现了 Queue 接口），区别只在「出队顺序由优先级决定」。

### 三种常见创建方式

```java
// 1. 默认：小顶堆（堆顶最小），元素需可比较（Integer、String 等自带）
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// 2. 大顶堆：堆顶最大
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
//   等价写法：new PriorityQueue<>((a, b) -> b - a);  ← a-b 小顶，b-a 大顶

// 3. 自定义对象：用 Comparator 指定排序字段
PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
```

> **口诀**：`a - b` 是**小顶堆**（升序），`b - a` 是**大顶堆**（降序）。自定义对象推荐 `Comparator.comparingInt(a -> a.字段)`，比手写 `(a,b)->...` 更清晰、也避免整型相减溢出。

### 怎么判断是小顶堆还是大顶堆？

关键：看比较器的**升/降序方向**。PriorityQueue 总把「比较结果最小」的元素放堆顶。

以 `Comparator.comparingInt(a -> a.val)` 为例，它等价于 `(a, b) -> a.val - b.val`（按 val **升序**）：

- `a.val < b.val` 时返回负数 → a 更小、优先级更高 → val 最小的在堆顶 → **小顶堆**

| 写法 | 比较方向 | 堆类型 | 堆顶 |
|------|---------|--------|------|
| `Comparator.comparingInt(a -> a.val)` | 升序（a-b） | **小顶堆** | 最小 |
| `Comparator.comparingInt(a -> -a.val)` | 降序（取负） | 大顶堆 | 最大 |
| `(a, b) -> a.val - b.val` | 升序 | 小顶堆 | 最小 |
| `(a, b) -> b.val - a.val` | 降序 | 大顶堆 | 最大 |

想把小顶堆改成大顶堆，推荐用 `.reversed()`（无溢出风险）：

```java
new PriorityQueue<>(Comparator.comparingInt((ListNode a) -> a.val).reversed());
```

> 注：`.reversed()` 写法中 lambda 参数需显式标类型 `(ListNode a)`，否则编译器无法推导泛型。

### 注意事项

| 事项 | 说明 |
|------|------|
| **不能存 null** | `add(null)` / `offer(null)` 会抛 `NullPointerException` |
| **遍历无序** | `for-each`、`toString()` 输出的不是排好序的结果，要有序必须逐个 `poll` |
| **装箱开销** | 存基本类型会自动装箱为 `Integer` 等，有额外内存/性能成本 |
| **相等不保证顺序** | 优先级相同的元素，出队先后不保证稳定 |
| **堆顶随 poll 更新** | `peek` 只看当前堆顶，`poll` 后堆会自动重新调整 |

---

## 五、本项目中的实际用法

| 数据结构 | 题目 | 用途 |
|---------|------|------|
| `Queue<TreeNode>` | 102 层序遍历、101 对称二叉树 | BFS 逐层遍历 |
| `Stack<TreeNode>` | 94 中序遍历、108 有序数组转BST | 迭代法模拟递归 |
| `Deque<Integer>` | 11 滑动窗口最大值 | 单调队列 |
| `PriorityQueue<ListNode>` | 23 合并K个有序链表（方法3） | 最小堆，每次 poll 出当前最小节点 |

> 注：项目中 94、108 用了 `Stack`，实际可以替换为 `Deque` 更规范。
> `Hot23_mergeKLists_3` 用 `PriorityQueue<ListNode>` + `Comparator.comparingInt(a -> a.val)` 建最小堆，是优先队列的典型用法。

---

## 六、选型速记

```
需要先进先出？
  → Queue<TreeNode> queue = new LinkedList<>();
  → 或 Deque<TreeNode> queue = new ArrayDeque<>();  ← 更推荐

需要后进先出？
  → Deque<Integer> stack = new ArrayDeque<>();      ← 推荐
  → 不要用 new Stack<>()

需要两端操作？（如单调队列、滑动窗口）
  → Deque<Integer> deque = new ArrayDeque<>();

需要按优先级出队？（如 Top K、多路归并）
  → 存 Integer/String：PriorityQueue<Integer> pq = new PriorityQueue<>();      小顶堆
  → 存自定义对象：必须传比较器，否则 ListNode 等未实现 Comparable 会报错
    PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
  → 要大顶堆：new PriorityQueue<>(Comparator.reverseOrder());
```
