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
     * 解法一：栈 + 哨兵
     * 栈 + 哨兵（存下标）
     * 栈内存放下标：栈底恒为「最后一个未匹配的右括号」（初始哨兵 -1），其上为未匹配的 '(' 下标
     * 遇到 ')' 时弹栈：
     * - 弹出后栈非空：i - 栈顶 = 当前连续有效长度，打擂台更新答案
     * - 弹出后栈为空：说明遇到多余右括号，把 i 压栈成为新哨兵（新隔板），后续长度从它之后起算
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
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


    /**
     * 解法二：动态规划
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public int longestValidParentheses_2(String s) {
        int n = s.length();
        int[] dp = new int[n];  // 默认全 0，以 '(' 结尾的位置自然为 0，dp[i] 表示：必须以位置 i 这个字符结尾的最长有效括号长度。
        int maxLen = 0;

        for (int i = 1; i < n; i++) {          // i=0 无法成对，dp[0] 恒为 0
            if (s.charAt(i) == ')') {          // 只有右括号可能作为有效段结尾
                if (s.charAt(i - 1) == '(') {
                    // 情况1：...()，接上 i-2 结尾的有效段
                    dp[i] = 2 + (i >= 2 ? dp[i - 2] : 0);
                } else {
                    // 情况2：...))
                    int left = i - dp[i - 1] - 1;  // 找可能与当前 ) 配对的 (
                    if (left >= 0 && s.charAt(left) == '(') {
                        dp[i] = dp[i - 1] + 2 + (left >= 1 ? dp[left - 1] : 0);
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        }
        return maxLen;
    }

    /**
     * 解法三：双向扫描（空间最优）
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public int longestValidParentheses_3(String s) {
        int n = s.length();
        int maxLen = 0;

        // 第一趟：从左到右扫
        int left = 0, right = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') left++;
            else right++;

            if (left == right) {
                maxLen = Math.max(maxLen, 2 * left);   // 左右括手数相等：这一段完整配对
            } else if (right > left) {
                left = right = 0;                      // 多余右括号当隔板，计数归零
            }
        }

        left = right = 0;  // 第二趟前重置计数器

        // 第二趟：从右到左扫（兼容左括号一直多于右括号的情形）
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') left++;
            else right++;

            if (left == right) {
                maxLen = Math.max(maxLen, 2 * left);
            } else if (left > right) {
                left = right = 0;                      // 多余左括号当隔板，计数归零
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        Hot32_longestValidParentheses longestValidParentheses = new Hot32_longestValidParentheses();
        System.out.println(longestValidParentheses.longestValidParentheses_1("()(()"));  // 最长有效子串 "()"，输出 2
        System.out.println(longestValidParentheses.longestValidParentheses_2("()(()"));  // 最长有效子串 "()"，输出 2
        System.out.println(longestValidParentheses.longestValidParentheses_3("()(()"));  // 最长有效子串 "()"，输出 2
    }
}
