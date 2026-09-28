package s12_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 84. 柱状图中最大的矩形
 * 给定 n 个非负整数表示柱状图中各柱子的高度，每根柱子宽度为 1，
 * 求能勾勒出来的矩形的最大面积。
 *
 * 示例：[2,1,5,6,2,3] → 10（高度 5、6 两根柱子，5 × 2）
 *
 * 核心思路：枚举每根柱子当矩形的高，它能向两侧扩展的极限就是
 *          「左边第一根比它矮的柱子」和「右边第一根比它矮的柱子」：
 *              面积 = heights[i] × (右边界 - 左边界 - 1)
 *
 *          用单调递增栈（存下标），并且【在出栈时结算】：
 *          一个下标被弹出的瞬间，它的两个边界同时确定——
 *            ・右边界 = 当前 i（正是它更矮才触发了弹出）
 *            ・左边界 = 弹出后的新栈顶（栈内递增，下面那个就是左侧第一个更矮的）
 *          一次弹栈拿到两个边界，所以整体 O(n)。
 *
 * 哨兵的作用：消除两个特判——
 *          ・左哨兵：栈被弹空时左边界应为 -1
 *          ・右哨兵：遍历结束时栈里剩余的柱子，右边界是 n
 *          方法一在数组首尾各补一个真实的 0；方法二用表达式模拟，不额外开数组。
 */
public class Hot84_largestRectangleArea {

    /**
     * 方法1：单调栈 + 哨兵（真哨兵：首尾各补一个高度 0）
     *
     * 左哨兵 newHeights[0] = 0：因为所有高度都 >= 0，弹出条件
     *   newHeights[i] < newHeights[栈顶] 在栈顶为下标 0 时变成 "< 0"，永不成立，
     *   所以下标 0 永远钉在栈底不会被弹出——这正是 while 里 stack.peek()
     *   不需要判空的原因（它要直接被读出来当左边界）。
     * 右哨兵 newHeights[n+1] = 0：遍历到它时栈里剩余的柱子会被全部弹出结算，
     *   不需要在循环后再补一段收尾逻辑。
     *
     * 时间复杂度：O(n)，每个下标进栈、出栈各至多一次
     * 空间复杂度：O(n)，栈 + 长度 n+2 的副本数组
     */
    public int largestRectangleArea_1(int[] heights) {
        int n = heights.length;
        // 造一个首尾各带一个 0 的副本：[0, 原数组..., 0]
        int[] newHeights = new int[n + 2];
        newHeights[0] = 0;          // 左哨兵：钉在栈底当左边界
        newHeights[n + 1] = 0;      // 右哨兵：负责把剩余柱子全部结算掉
        System.arraycopy(heights, 0, newHeights, 1, n);

        int maxArea = 0;

        // 单调递增栈，存【下标】而不是高度——因为宽度要靠下标相减算出来
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < newHeights.length; i++) {
            // 当前柱子比栈顶矮 → 栈顶那根找到了右边界，弹出结算
            while (!stack.isEmpty() && newHeights[i] < newHeights[stack.peek()]) {
                int h = newHeights[stack.pop()];   // 先弹出：这根柱子的高度就是矩形高
                int leftBoundary = stack.peek();   // 再看新栈顶：左侧第一根更矮的
                int width = i - leftBoundary - 1;  // 左右边界都是开区间，所以要减 1

                maxArea = Math.max(maxArea, h * width);
            }

            stack.push(i);   // 当前柱子入栈，等着被右边更矮的柱子结算
        }

        return maxArea;
    }

    /**
     * 方法2：单调栈 + 虚拟哨兵（不复制数组，用表达式模拟两个哨兵）
     *
     * 右哨兵：循环多走一格 i <= n，并用 currentHeight = 0 模拟末尾那根高度 0 的柱子。
     *   注意它只是个临时变量，作用是在 while 里触发剩余柱子全部结算；
     *   结算完就没用了，所以【不需要入栈】——最后一轮用 if (i < n) 跳过 push，
     *   避免把越界下标 n 压进栈留下隐患。
     * 左哨兵：栈被弹空时没有实体可当左边界，直接用 -1 兜底
     *   （-1 恰好让宽度公式 i - (-1) - 1 = i 覆盖从下标 0 到 i-1 的全部柱子）。
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)，只有栈，比方法一省掉一个 n+2 的数组
     */
    public int largestRectangleArea_2(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();   // 单调递增栈，存下标
        int maxArea = 0;

        for (int i = 0; i <= n; i++) {   // 多走一格，i == n 时充当右哨兵
            int currentHeight = i == n ? 0 : heights[i];   // 虚拟的末尾 0 高度

            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int h = heights[stack.pop()];                           // 先弹出取高
                int leftBoundary = stack.isEmpty() ? -1 : stack.peek(); // 弹空则左边界 -1
                int width = i - leftBoundary - 1;
                maxArea = Math.max(maxArea, width * h);
            }

            if (i < n) stack.push(i);   // 右哨兵不入栈：它的使命只是触发结算
        }

        return maxArea;
    }


    public static void main(String[] args) {
        Hot84_largestRectangleArea hot84 = new Hot84_largestRectangleArea();
        int[] heights = {2, 1, 5, 6, 2, 3};
        int res1 = hot84.largestRectangleArea_1(heights);
        int res2 = hot84.largestRectangleArea_2(heights);
        // 预期输出：两行都是 10（高度 5 × 宽度 2）
        System.out.println(res1);
        System.out.println(res2);
    }
}
