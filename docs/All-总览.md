# 算法题总览

## s1_hash（哈希表）

| 题号 | 题目 | 方法 | 一句话总结 | 代码 |
| :---: | :--- | :--- | :--- | :--- |
| 1 | 两数之和 | HashMap | 边遍历边查 map 里有没有 `target - nums[i]`，没有就把当前值和下标存进去 | [代码](../src/s1_hash/Hot1_twoSum.java) |
| 49 | [字母异位词分组](Hot49-字母异位词分组.md) | HashMap + 排序 | 把每个字符串的**字符**排序后作为 key，相同 key 的字符串归为一组 | [代码](../src/s1_hash/Hot49_groupAnagrams.java) |
| 128 | 最长连续序列 | HashSet + 起点判定 | 全部放入 Set，只从"前一个数不存在"的位置开始向后延伸数长度，保证 O(n) | [代码](../src/s1_hash/Hot128_longestConsecutive.java) |

## s2_doubleIndex（双指针）

| 题号 | 题目 | 方法 | 一句话总结 | 代码 |
| :---: | :--- | :--- | :--- | :--- |
| 283 | 移动零 | 快慢指针 | 快指针找非零元素往慢指针位置填，遍历完后慢指针之后全部补零 | [代码](../src/s2_doubleIndex/Hot283_moveZeroes.java) |
| 11 | [盛最多水的容器](Hot11-盛最多水的容器.md) | 双指针（谁短移谁） | 两端的指针向中间收缩，面积由短板决定，所以移动较短的那一侧 | [代码](../src/s2_doubleIndex/Hot11_maxArea.java) |
| 15 | [三数之和](Hot15-三数之和.md) | 排序 + 双指针 / HashSet | 排序后固定一个数，双指针在剩余区间找两数之和，注意去重要和**前一个**元素比 | [代码](../src/s2_doubleIndex/Hot15_threeSum.java) |
| 42 | [接雨水](Hot42-接雨水.md) | 双指针 / DP | 双指针从两端向中间收，哪边矮就算哪边的积水量，因为另一边一定有更高的板兜底 | [代码](../src/s2_doubleIndex/Hot42_trap.java) |

## s3_slidingWindow（滑动窗口）

| 题号 | 题目 | 方法 | 一句话总结 | 代码 |
| :---: | :--- | :--- | :--- | :--- |
| 3 | [无重复字符的最长子串](Hot3-无重复字符的最长子串.md) | 滑动窗口 + HashMap | 右指针扩张，遇到重复字符时左指针直接跳到该字符上次出现位置的**下一格** | [代码](../src/s3_slidingWindow/Hot3_lengthOfLongestSubstring.java) |
| 438 | [找到字符串中所有字母异位词](Hot438-找到字符串中所有字母异位词-Hot76.md) | 滑动窗口 + 计数器数组 | 用 count 数组记账单，remain 记录还差几个字符，窗口严格保持 p.length() 大小一进一出 | [代码](../src/s3_slidingWindow/Hot438_findAnagrams.java) |

## s4_substring（子串）

| 题号 | 题目 | 方法 | 一句话总结 | 代码 |
| :---: | :--- | :--- | :--- | :--- |
| 560 | [和为K的子数组](Hot560-和为k的子数组.md) | 前缀和 + HashMap | 用 map 存历史前缀和的出现次数，每步查 `preSum - k` 是否在账本里，**先查后记** | [代码](../src/s4_substring/Hot560_subarraySum.java) |
| 239 | [滑动窗口最大值](Hot239-滑动窗口最大值.md) | 单调双端队列 / 分块DP | 队列存下标，维护单调递减序，队头就是当前窗口最大值，过期下标从队头踢掉 | [代码](../src/s4_substring/Hot239_maxSlidingWindow.java) |
| 76 | [最小覆盖子串](Hot76-最小覆盖子串.md) | 滑动窗口 + 计数器数组 | 和[找到字符串中所有字母异位词](Hot438-找到字符串中所有字母异位词-Hot76.md)同一套账单框架，窗口内字符凑齐后不断收缩左边界找最小 | [代码](../src/s4_substring/Hot76_minWindow.java) |

## s5_ordinaryArray（普通数组）

| 题号 | 题目 | 方法 | 一句话总结 | 代码 |
| :---: | :--- | :--- | :--- | :--- |
| 53 | [最大子数组和](Hot53-最大子数组和.md) | 贪心 / DP（Kadane）/ 分治 | 累加当前元素后判断 preSum 是否变负，负了就清零丢弃，全程跟踪最大值 | [代码](../src/s5_ordinaryArray/Hot53_maxSubArray.java) |
| 56 | [合并区间](Hot56-合并区间.md) | 排序 + 线性合并 | 按左端点排序后逐个遍历，能重叠就扩展右端点（取 max），不能重叠就新开一个区间 | [代码](../src/s5_ordinaryArray/Hot56_merge.java) |
| 189 | [轮转数组](Hot189-轮转数组.md) | 辅助数组 / 三次翻转 / 环状替换 | 先整体翻转，再分别翻转前 k 个和剩余部分，注意 k 必须先 `k %= n` | [代码](../src/s5_ordinaryArray/Hot189_rotate.java) |
| 238 | 除自身以外数组的乘积 | 前缀积（双指针同步累乘） | 左右指针从两端同步向中间走，用 `*=` 累乘把左积和右积分别灌进结果数组 | [代码](../src/s5_ordinaryArray/Hot238_productExceptSelf.java) |
| 41 | [缺失的第一个正数](Hot41-缺失的第一个正数.md) | 原地哈希（正负号标记） | 把负数替换为 n+1 后，用下标当 key、正负号当标记，第一个正数的下标+1 就是答案 | [代码](../src/s5_ordinaryArray/Hot41_firstMissingPositive.java) |

## s6_matrix（矩阵）

| 题号 | 题目 | 方法 | 一句话总结 | 代码 |
| :---: | :--- | :--- | :--- | :--- |
| 73 | [矩阵置零](Hot73-矩阵置零.md) | 原地标记（第一行/列当记事本） | 用第一行第一列记录该行/列是否有 0，最后统一置零，注意第一行/列自己要用额外变量单独处理 | [代码](../src/s6_matrix/Hot73_setZeroes.java) |
| 54 | [螺旋矩阵](Hot54-螺旋矩阵.md) | 四边界收缩 | 维护 top/bottom/left/right 四个边界，按"右→下→左→上"一圈一圈剥，每走完一边收缩对应边界 | [代码](../src/s6_matrix/Hot54_spiralOrder.java) |
| 48 | [旋转图像](Hot48-旋转图像.md) | 转置 + 水平翻转 | 先沿主对角线转置（行列互换），再逐行左右翻转，等价于顺时针旋转 90° | [代码](../src/s6_matrix/Hot48_rotate.java) |
| 240 | [搜索二维矩阵 II](Hot240-搜索二维矩阵II.md) | Z 字形查找（从右上角出发） | 从右上角开始，比 target 大就左移，比 target 小就下移，每步排除一行或一列 | [代码](../src/s6_matrix/Hot240_searchMatrix.java) |

## s7_linkTable（链表）

| 题号 | 题目 | 方法 | 一句话总结 | 代码 |
| :---: | :--- | :--- | :--- | :--- |
| 2 | 两数相加 | 模拟进位 | 两个链表同时遍历，逐位相加并 carry 进位，短的走完继续走长的，最后 carry>0 补一个节点 | [代码](../src/s7_linkTable/Hot2_addTwoNumbers.java) |
| 21 | 合并两个有序链表 | 双指针 | dummy 节点起手，两个指针比大小往结果链上接，剩余部分直接拼上去 | [代码](../src/s7_linkTable/Hot21_mergeTwoLists.java) |
| 206 | [反转链表](Hot206-反转链表.md) | 迭代 / 递归 | 三个指针 prev/curr/next 逐个翻转指向，或者递归到末尾再逐层回头指 | [代码](../src/s7_linkTable/Hot206_reverseList.java) |
| 24 | [两两交换链表中的节点](Hot24-两两交换链表中的节点.md) | 递归 / 迭代 | 每次取两个节点交换指向，递归处理后续部分，dummy 节点简化头节点交换 | [代码](../src/s7_linkTable/Hot24_swapPairs.java) |
| 25 | [K 个一组翻转链表](Hot25-K个一组翻转链表.md) | 分组翻转 | 先数长度看够不够 k 个，够就翻转这 k 个，不够就保持原样，递归/循环拼接 | [代码](../src/s7_linkTable/Hot25_reverseKGroup.java) |
| 19 | 删除链表的倒数第 N 个结点 | 快慢指针 | fast 先走 n 步拉开距离，然后 fast/slow 同速走，fast 到尾时 slow 正好在待删节点前一个 | [代码](../src/s7_linkTable/Hot19_removeNthFromEnd.java) |
| 138 | [复制带随机指针的链表](Hot138-复制带随机指针的链表.md) | 三步走（拼接→拆分→连随机） | 每个节点后面插一个克隆节点，设好 random，再从交错链表中拆出两条独立链表 | [代码](../src/s7_linkTable/Hot138_copyRandomList.java) |
| 141 | 环形链表 | 快慢指针 | 快指针每次走两步、慢指针走一步，有环必相遇 | [代码](../src/s7_linkTable/Hot141_hasCycle.java) |
| 142 | 环形链表 II | 快慢指针 + 入口检测 | 快慢相遇后，一个新指针从 head 出发和慢指针同速走，再次相遇就是入环点 | [代码](../src/s7_linkTable/Hot142_detectCycle.java) |
| 160 | [相交链表](Hot160-相交链表.md) | 双指针拼接 | a 走完走 b、b 走完走 a，等长后同步到达的就是交点，不相交则同时到 null | [代码](../src/s7_linkTable/Hot160_getIntersectionNode.java) |
| 234 | [回文链表](Hot234-回文链表.md) | 找中点 + 反转后半段 | 快慢指针找中点，反转后半段链表，再和前半段逐个比较 | [代码](../src/s7_linkTable/Hot234_isPalindrome.java) |
| 23 | [合并 K 个升序链表](Hot23-合并K个升序链表.md) | 分治 / 优先队列 | 分治两两合并（归并思想），或用最小堆每次取 k 个链表头中最小的 | [代码1](../src/s7_linkTable/Hot23_mergeKLists_1.java) [代码2](../src/s7_linkTable/Hot23_mergeKLists_2.java) [代码3](../src/s7_linkTable/Hot23_mergeKLists_3.java) |
| 146 | [LRU 缓存](Hot146-LRU缓存.md) | HashMap + 双向链表 | get/put 都移到链表头部，超容量删尾部，LinkedHashMap 可一行搞定 | [代码1](../src/s7_linkTable/Hot146_LRUCache_1.java) [代码2](../src/s7_linkTable/Hot146_LRUCache_2.java) |
| 148 | [链表排序](Hot148-链表排序.md) | 归并排序（自顶向下/自底向上） | 快慢指针找中点断开，递归排两半再 merge，自底向上可省递归栈 | [自顶向下](../src/s7_linkTable/Hot148_sortList_top2bottom.java) [自底向上](../src/s7_linkTable/Hot148_sortList_bottom2top.java) |

## s8_tree（二叉树）

| 题号 | 题目 | 方法 | 一句话总结 | 代码 |
| :---: | :--- | :--- | :--- | :--- |
| 94 | [二叉树的中序遍历](Hot94-二叉树的中序遍历.md) | 递归 / 迭代（栈） | 递归三行搞定；迭代用栈模拟，一路向左压栈，弹出后访问再转向右子树 | [代码](../src/s8_tree/Hot94_inorderTraversal.java) |
| 98 | [验证二叉搜索树](Hot98-验证二叉搜索树.md) | 递归带范围 / 中序遍历 | 每个节点带 (min, max) 范围下去检查，往左收紧 max、往右收紧 min；或中序遍历检查严格递增 | [代码](../src/s8_tree/Hot98_isValidBST.java) |
| 101 | [对称二叉树](Hot101-对称二叉树.md) | 递归对比 | 把一棵树拆成"左"和"右"两棵虚拟树，递归判断 left.left==right.right && left.right==right.left | [代码](../src/s8_tree/Hot101_isSymmetric.java) |
| 102 | [二叉树的层序遍历](Hot102-二叉树的层序遍历.md) | BFS（队列） | 队列每层记录 size，for 循环恰好处理完当前层再入队下一层 | [代码](../src/s8_tree/Hot102_levelOrder.java) |
| 104 | [二叉树的最大深度](Hot104-二叉树的最大深度.md) | BFS / DFS | DFS 一行：`1 + max(depth(left), depth(right))` | [代码](../src/s8_tree/Hot104_maxDepth.java) |
| 105 | [从前序与中序遍历序列构造二叉树](Hot105-从前序与中序遍历序列构造二叉树.md) | HashMap 分治 / 全局指针+stop | 前序定根、中序分左右；方法一 HashMap 查索引算左子树大小，方法二 stop 撞墙隐式划边界 | [代码1](../src/s8_tree/Hot105_buildTree_1.java) [代码2](../src/s8_tree/Hot105_buildTree_2.java) |
| 108 | [有序数组转平衡二叉搜索树](Hot108-有序数组转二叉搜索树.md) | 分治（取中间值当根） | 每次取数组中间元素当根，左半递归建左子树、右半递归建右子树，天然平衡 | [代码](../src/s8_tree/Hot108_sortedArrayToBST.java) |
| 114 | [二叉树展开为链表](Hot114-二叉树展开为链表.md) | 前序遍历 / 原地拼接 | 找到每个节点左子树的最右节点，把右子树接过去，再把左子树整体搬到右边 | [代码](../src/s8_tree/Hot114_flatten.java) |
| 124 | [二叉树中的最大路径和](Hot124-二叉树中的最大路径和.md) | 后序遍历 + 最大贡献值 | 每个节点当拐点：左贡献+根+右贡献更新答案；贡献值只能选一边往上走，负贡献取 0 | [代码](../src/s8_tree/Hot124_maxPathSum.java) |
| 199 | 二叉树的右视图 | BFS / DFS（根→右→左） | BFS 每层取最后一个；DFS 先走右子树，depth > res.size() 时记录 | [代码](../src/s8_tree/Hot199_rightSideView.java) |
| 226 | [翻转二叉树](Hot226-翻转二叉树.md) | 递归 / BFS | 递归交换每个节点的左右子树，三行代码 | [代码](../src/s8_tree/Hot226_invertTree.java) |
| 230 | [二叉搜索树中第 K 小的元素](Hot230-二叉搜索树中第K小的元素.md) | 中序遍历 | BST 中序遍历就是递增序列，数到第 k 个就是答案 | [代码](../src/s8_tree/Hot230_kthSmallest.java) |
| 236 | [二叉树的最近公共祖先](Hot236-二叉树的最近公共祖先.md) | 后序递归 | 左右各找一遍，两侧都有就是当前节点；只有一侧有就传上去，遇到 p/q 直接返回 | [代码](../src/s8_tree/Hot236_lowestCommonAncestor.java) |
| 437 | [路径总和 III](Hot437-路径总和III.md) | 双重 DFS / 前缀和+回溯 | 暴力：每个节点当起点往下搜；优化：前缀和查账，和数组"和为K的子数组"一个套路，记得回溯 | [代码](../src/s8_tree/Hot437_pathSum.java) |
| 543 | [二叉树的直径](Hot543-二叉树的直径.md) | DFS（记录最大深度和） | 每个节点当拐点，直径 = 左深度 + 右深度，全局变量跟踪最大值 | [代码](../src/s8_tree/Hot543_diameterOfBinaryTree.java) |

## sort（排序）

| 题目 | 方法 | 一句话总结 | 代码 |
| :--- | :--- | :--- | :--- |
| [快速排序](排序-快速排序.md) | 随机 pivot + 双指针分区 | 随机选基准换到最左，右指针找小的、左指针找大的，交换后基准归位，递归两半 | [代码](../src/sort/QuickSort.java) |
| [归并排序](排序-归并排序.md) | 分治 + 双指针合并 | 递归拆到单个元素，合并时两队比头小的先走，一队走完另一队全搬，最后回写原数组 | [代码](../src/sort/MergeSort.java) |
| [冒泡排序](排序-冒泡排序.md) | 相邻比较交换 | 每轮相邻两两比，大的往后冒到末尾，一整轮无交换则提前退出（swapFlag） | [代码](../src/sort/BubbleSort.java) |

## 专题知识（方法论 / 数据结构 / 语法）

| 文档 | 主题 | 一句话总结 |
| :--- | :--- | :--- |
| [递归相关的题目](Tip4-递归相关的题目.md) | 递归方法论 | 递归 = 拆问题 + 拼结果；三板斧（终止/拆解/拼接），四大模式（树遍历/链表/分治/记忆化） |
| [二叉树的 DFS 与 BFS](Tip2-二叉树的DFS与BFS.md) | 遍历策略 | DFS 一条路走到底（递归/栈），BFS 一层层扩散（队列 + size 锁层），附选型口诀 |
| [Queue、Deque、Stack](Tip5-Queue、Deque、Stack.md) | 线性容器 | 三者本质与方法速查；Deque 是超集，推荐用 ArrayDeque 代替老旧的 Stack |
| [取中间值](Tip3-取中间值.md) | mid 写法 | `left + (right-left)/2` 防溢出首选；左中 vs 右中、二分防死循环、快排随机 pivot |
| [Arrays.sort 原理详解](Tip1-Arrays.sort原理详解.md) | 排序源码 / 语法 | 基本类型双轴快排、对象 TimSort、小数组插入排序；附区间排序 Comparator 防溢出写法 |
