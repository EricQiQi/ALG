package s12_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 394. 字符串解码
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * 编码规则为 k[encoded_string]，表示方括号内部的 encoded_string 正好重复 k 次。
 * k 保证为正整数，且输入中除了重复次数没有其他数字。
 * <p>
 * 示例：
 * "3[a]2[bc]"     → "aaabcbc"
 * "3[a2[c]]"      → "accaccacc"
 * "2[abc]3[cd]ef" → "abcabccdcdcdef"
 */
public class Hot394_decodeString {

    /**
     * 方法1：双栈
     * 核心思路：括号嵌套 = 现场保存与恢复。
     * 遇 '[' 就把「当前已拼好的前缀 + 待重复的次数」压栈存现场，
     * 清空工作区去处理括号内部的新内容；
     * 遇 ']' 就弹栈恢复现场，把本层解析好的 currStr 重复 times 次，
     * 接在弹出的前缀后面。
     * 栈天然对应括号的嵌套层次——最内层最先闭合，正是后进先出。
     * 时间复杂度：O(n)，n 为解码后字符串的长度（每个字符最终都要被拼出来一次）
     * 空间复杂度：O(n)，栈内保存的各层前缀总长与嵌套层数
     */
    public String decodeString_1(String s) {
        // 数字栈：保存「待重复的次数」，遇 '[' 压入，遇 ']' 弹出
        Deque<Integer> countStack = new ArrayDeque<>();
        // 字符串栈：保存「进入本层括号之前已经拼好的前缀」
        Deque<String> stringStack = new ArrayDeque<>();

        int num = 0;            // 正在累积的数字（可能多位，如 "10["）
        String currStr = "";    // 当前层已解析出的字符串

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                // 数字可能有多位，逐位累积："10" → 1 → 10
                num = num * 10 + ch - '0';
            } else if (ch == '[') {
                // 进入新的一层：把次数和前缀存进栈，再清空工作区
                countStack.push(num);
                stringStack.push(currStr);
                currStr = "";
                num = 0;
            } else if (ch == ']') {
                // 结束一层：取回进括号前的前缀，本层内容重复 times 次接在它后面
                // 顺序不可颠倒：先前缀、再重复内容
                StringBuilder temp = new StringBuilder(stringStack.pop());
                int times = countStack.pop();
                for (int i = 0; i < times; i++) {
                    temp.append(currStr);
                }
                currStr = temp.toString();
            } else {
                // 普通字母，直接拼到当前层
                currStr += ch;
            }
        }

        // 最外层没有括号包裹时，结果就留在 currStr 里
        return currStr;
    }

    int i = 0;

    public String decodeString_2(String s) {
        StringBuilder curr = new StringBuilder();
        int count = 0;
        while (i < s.length()) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                count = count * 10 + ch - '0';
                i++;
            } else if (ch == '[') {
                i++;
                String inner = decodeString_2(s);
                for (int i = 0; i < count; i++) curr.append(inner);
                count = 0;
            } else if (ch == ']') {
                i++;
                break;
            } else {
                curr.append(ch);
                i++;
            }
        }
        return curr.toString();
    }

    public static void main(String[] args) {
        Hot394_decodeString hot394 = new Hot394_decodeString();
        // 预期输出：aaa + bccccd × 10
        System.out.println(hot394.decodeString_1("3[a]10[b4[c]d]"));
        System.out.println(hot394.decodeString_2("3[a]10[b4[c]d]"));
    }
}
