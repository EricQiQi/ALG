package s12_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 739. 每日温度
 * 给定每日温度数组 temperatures，返回数组 answer，
 * 其中 answer[i] 表示第 i 天之后要等几天才能遇到更高的温度；
 * 如果之后都不会升高，则 answer[i] = 0。
 *
 * 示例：[73,74,75,71,69,72,76,73] → [1,1,4,2,1,1,0,0]
 *
 * 方法：单调栈（栈内温度自底向上递减）
 * 核心思路：遍历到第 i 天时，栈里存的都是「还没等到更高温度的日子」。
 *          只要当前温度比栈顶那天高，就说明栈顶那天等到了答案，
 *          弹出并结算 res[栈顶] = i - 栈顶；循环直到栈顶不再更低为止。
 *          遍历结束后仍留在栈里的日子，后面再也没有更高温度，
 *          res 保持数组默认值 0，正好就是题目要的答案。
 * 时间复杂度：O(n)，每个下标最多进栈、出栈各一次
 * 空间复杂度：O(n)，最坏情况（温度严格递减）栈里存下全部 n 个下标
 */
public class Hot739_dailyTemperatures {

    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];   // Java 数组默认全 0，恰好对应"之后没有更高温度"

        // 单调递减栈，存【下标】而不是温度值——因为答案要算天数差
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // 当前温度比栈顶那天高 → 栈顶那天等到答案了，弹出结算
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int preIndex = stack.pop();
                res[preIndex] = i - preIndex;   // 等待的天数 = 下标之差
            }
            // 当前这天入栈，等待后面更高的温度来结算
            stack.push(i);
        }

        // 栈中剩余下标对应的 res 保持 0（后面再也没升温）
        return res;
    }

    public static void main(String[] args) {
        Hot739_dailyTemperatures hot739 = new Hot739_dailyTemperatures();
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        int[] res = hot739.dailyTemperatures(temperatures);
        // 预期输出：1 1 4 2 1 1 0 0
        for (int i : res) {
            System.out.print(i + " ");
        }
    }
}
