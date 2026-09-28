package s12_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 20. 有效的括号
 */
public class Hot20_isValid {

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public boolean isValid(String s) {
        Deque<Character> deque = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                deque.push(')');
            } else if (c == '[') {
                deque.push(']');
            } else if (c == '{') {
                deque.push('}');
            } else if (!deque.isEmpty() && deque.peek() == c) {
                deque.pop();
            } else {
                return false;
            }
        }
        return deque.isEmpty();
    }

    public static void main(String[] args) {
        Hot20_isValid hot20 = new Hot20_isValid();
        System.out.println(hot20.isValid("()"));
    }
}
