package s12_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 155. 最小栈
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * push(x) —— 将元素 x 推入栈中。
 * pop() —— 删除栈顶的元素。
 * top() —— 获取栈顶元素。
 * getMin() —— 检索栈中的最小元素。
 *
 * 方法1：辅助栈（同步栈）
 * 核心思路：再开一个栈 minstack，和数据栈同步升降。
 *          minstack 的栈顶永远是「当前数据栈里的最小值」，
 *          即 minstack[i] = min(数据栈底到第 i 个元素)。
 *          由于两个栈高度始终一致，pop 时一起弹，最小值自动回退到上一状态。
 * 时间复杂度：O(1)（四个操作都是常数时间）
 * 空间复杂度：O(n)（辅助栈和数据栈等长）
 *
 */
class Hot155_MinStack_1 {
    /** 数据栈：正常存原始元素 */
    Deque<Integer> stack;
    /** 辅助栈：栈顶 = 当前数据栈的最小值，与 stack 同步 push/pop */
    Deque<Integer> minstack;

    public Hot155_MinStack_1() {
        stack = new ArrayDeque<>();
        minstack = new ArrayDeque<>();
        // 垫一个哨兵：空栈时的"最小值"设为最大值，
        // 这样 push 第一个元素时 min(minstack.peek(), x) 一定取到 x，无需判空
        minstack.push(Integer.MAX_VALUE);
    }

    /** 入栈：数据栈压 x，辅助栈压「x 与旧最小值的较小者」 */
    public void push(int x) {
        stack.push(x);
        minstack.push(Math.min(minstack.peek(), x));
    }

    /** 出栈：两栈必须同时弹，保持高度一致，最小值才能自动回退 */
    public void pop() {
        stack.pop();
        minstack.pop();
    }

    /** 栈顶元素：直接看数据栈 */
    public int top() {
        return stack.peek();
    }

    /** 最小值：直接看辅助栈栈顶 */
    public int min() {
        return minstack.peek();
    }

    public static void main(String[] args) {
        Hot155_MinStack_1 minStack = new Hot155_MinStack_1();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.min());   // 返回 -3.
        minStack.pop();
        System.out.println(minStack.top());      // 返回 0.
        System.out.println(minStack.min());   // 返回 -2.
    }
}
