package s15_dp;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 32. 最长有效括号
 * 求只含 '(' 和 ')' 的字符串中，最长的有效（格式正确且连续）括号子串的长度。
 * 核心思路：栈（存下标）+ 哨兵。栈底始终保留「最后一个未匹配的右括号位置」作为隔板，
 * 每成功弹出一对，当前下标与栈顶之差就是一段连续有效长度。
 */
public class Hot32_longestValidParentheses {

    /**
     * 栈 + 哨兵（存下标）
     * 栈内存放下标：栈底恒为「最后一个未匹配的右括号」（初始哨兵 -1），其上为未匹配的 '(' 下标
     * 遇到 ')' 时弹栈：
     *   - 弹出后栈非空：i - 栈顶 = 当前连续有效长度，打擂台更新答案
     *   - 弹出后栈为空：说明遇到多余右括号，把 i 压栈成为新哨兵（新隔板），后续长度从它之后起算
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     *
     * @param s 只含括号的字符串
     * @return 最长有效括号子串的长度
     */
    public int longestValidParentheses_1(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);  // 哨兵：虚拟的「最后一个未匹配右括号」，让首个有效段长度 = i - (-1)

        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);  // 未匹配的 '(' 下标入栈等待配对
            } else {
                stack.pop();    // 配对：弹出一个 '('（或哨兵，表示该右括号多余）

                if (stack.isEmpty()) {
                    stack.push(i);  // 多余右括号：它成为新隔板，有效段不能跨过它
                } else {
                    // 栈顶是配对成功的「起点前一个位置」，i - 栈顶即连续有效长度
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        Hot32_longestValidParentheses longestValidParentheses = new Hot32_longestValidParentheses();
        System.out.println(longestValidParentheses.longestValidParentheses_1("()(()"));  // 最长有效子串 "()"，输出 2
    }
}
